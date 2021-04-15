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
    private final DataOutputStream out;
    private final DataInputStream in;
    private final SocketReceiver receiver;
    private User user;

    public ClientHandler(Socket socket, MessageTransmitter messageTransmitter) {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            receiver = new SocketReceiver(messageTransmitter, this);
            getAuthTimer(socket);

        } catch (IOException e) {
            throw new ChatServerException("ClientHandler closed");
        }
    }

    @Override
    public void run() {
        receiver.receiveMessage();
    }

    public String readData() throws IOException {
        return in.readUTF();
    }

    public void sendData(String data) throws IOException {
        out.writeUTF(data);
    }

    private void getAuthTimer(Socket socket) {
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

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
