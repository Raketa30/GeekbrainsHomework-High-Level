package ru.geekbrains.lesson2.server;

import ru.geekbrains.lesson2.server.entity.User;
import ru.geekbrains.lesson2.server.exceptions.ChatServerException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

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
            String inputMessage = in.readUTF();
//
//            if (inputMessage.startsWith("-register")) {
//                if (messageTransmitter.getRegistrationService().registration(inputMessage)) {
//                    messageTransmitter.sendStatusMessage(this, "Successfully registered, please auth");
//                }
//            } else {
//
//            }

            isLoggedIn = messageTransmitter.getAuthService()
                    .authentication(this, inputMessage);

        }
        out.writeUTF("logged");
    }

    private void getAuthTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (user == null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 120000);
    }

}
