package ru.geekbrains.lesson2_3.server;

import ru.geekbrains.lesson2_3.server.entity.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class ClientHandler implements Runnable {
    private final SocketReceiver receiver;
    private final Socket socket;
    private User user;

    public ClientHandler(Socket socket, MessageTransmitter messageTransmitter) {
        this.socket = socket;
        receiver = new SocketReceiver(messageTransmitter, this);
        getAuthTimer(socket);
    }

    @Override
    public void run() {
        receiver.receiveMessage();
    }

    public String readData() throws IOException {
        String message;
        try (DataInputStream in = new DataInputStream(socket.getInputStream())) {
            message = in.readUTF();
        }
        return message;
    }

    public void sendData(String data) throws IOException {
        try (DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
            out.writeUTF(data);
        }
    }

    private void getAuthTimer(Socket socket) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (user == null) {
                    try {
                        sendData("Auth timeout");
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
