package ru.geekbrains.lesson2_3.server;

import java.io.IOException;

public class SocketReceiver implements Receiver {
    private final MessageProcessor messageProcessor;
    private final ClientHandler clientHandler;

    public SocketReceiver(MessageTransmitter transmitter, ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        this.messageProcessor = new MessageProcessor(transmitter, clientHandler);

    }

    @Override
    public void receiveMessage() {
        while (true) {
            try {
                String data = clientHandler.readData();
                messageProcessor.processMessage(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
