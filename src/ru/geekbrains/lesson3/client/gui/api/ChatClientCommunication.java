package ru.geekbrains.lesson3.client.gui.api;

import ru.geekbrains.lesson3.client.exceptions.ChatClientException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatClientCommunication {
    private final DataOutputStream out;
    private final DataInputStream in;

    public ChatClientCommunication(String address, int port) {
        try {
            Socket socket = new Socket(address, port);

            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

        } catch (IOException e) {
            throw new ChatClientException("Something with connection to server");
        }
    }

    public void send(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            throw new ChatClientException("Problem to send message", e);
        }
    }

    public String receive() {
        try {
            return in.readUTF();

        } catch (IOException e) {
            throw new ChatClientException("Problem to receive message", e);
        }
    }
}
