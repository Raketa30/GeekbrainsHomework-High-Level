package ru.geekbrains.lesson2.server;

import ru.geekbrains.lesson2.server.entity.User;
import ru.geekbrains.lesson2.server.exceptions.ChatServerException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 1. Разобраться с кодом
 * 2. * Реализовать личные сообщения, если клиент пишет «/w nick3 Привет»,
 * то только клиенту с ником nick3 должно прийти сообщение «Привет»
 */

public class ClientHandler implements Runnable {
    private Socket socket;
    private final MessageTransmitter messageTransmitter;
    private final DataOutputStream out;
    private final DataInputStream in;
    private volatile User user;

    public ClientHandler(Socket socket, MessageTransmitter messageTransmitter) {
        this.socket = socket;
        this.messageTransmitter = messageTransmitter;

        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            getAuthTimer();
            authentication();

        } catch (IOException e) {
            throw new ChatServerException("ClientHandler closed");
        }
    }

    @Override
    public void run() {
        Receiver receiver = new SocketReceiver(in, messageTransmitter, this);
        receiver.receiveMessage();
    }

    public MessageTransmitter getMessageTransmitter() {
        return messageTransmitter;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public synchronized User getUser() {
        return user;
    }

    public synchronized void setUser(User user) {
        this.user = user;
    }

    private void authentication() throws IOException {
        boolean isLoggedIn = false;

        while (!isLoggedIn) {
            String authMessage = in.readUTF();
            isLoggedIn = messageTransmitter.getAuthService()
                    .authentication(this, authMessage);
        }
        out.writeUTF("logged");
    }

    private void getAuthTimer() {
        long start = System.currentTimeMillis();

        new Thread(() -> {
            while (true) {
                try {
                    long current = System.currentTimeMillis();
                    if (current - start >= 120000 && getUser() == null) {
                        out.writeUTF("Auth timeout");
                        socket.close();
                        break;
                    }
                } catch (IOException ignored) {
                }
            }
        }).start();
    }

}
