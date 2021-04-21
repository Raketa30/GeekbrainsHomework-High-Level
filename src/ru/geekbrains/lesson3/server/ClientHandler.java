package ru.geekbrains.lesson3.server;

import ru.geekbrains.lesson3.server.exceptions.ChatServerException;
import ru.geekbrains.lesson3.server.transmitter.MessageTransmitter;
import ru.geekbrains.lesson3.server.transmitter.SocketReceiver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final DataOutputStream out;
    private final DataInputStream in;
    private final SocketReceiver receiver;
    private final Socket socket;

    public ClientHandler(Socket socket, MessageTransmitter messageTransmitter) {
        this.socket = socket;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            receiver = new SocketReceiver(messageTransmitter, this);

        } catch (IOException e) {
            throw new ChatServerException("ClientHandler closed", e);
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

    public void shutdown() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
