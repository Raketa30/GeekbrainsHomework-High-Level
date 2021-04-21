package ru.geekbrains.lesson3.server.transmitter;

import ru.geekbrains.lesson3.server.ClientHandler;
import ru.geekbrains.lesson3.server.MessageProcessor;
import ru.geekbrains.lesson3.server.exceptions.ChatServerException;

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
                messageProcessor.processMessage(clientHandler.readData());
            } catch (IOException e) {
                clientHandler.shutdown();
                throw new ChatServerException("Something wrong during receive message", e);
            }
        }
    }
}
