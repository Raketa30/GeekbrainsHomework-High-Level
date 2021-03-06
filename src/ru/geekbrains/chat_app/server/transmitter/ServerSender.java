package ru.geekbrains.chat_app.server.transmitter;

import ru.geekbrains.chat_app.server.ClientHandler;
import ru.geekbrains.chat_app.server.exceptions.ChatServerException;

import java.io.IOException;

public class ServerSender implements Sender<String> {
    @Override
    public void sendMessage(ClientHandler clientHandler, String nickname, String message) {
        try {
            clientHandler.sendData(String.format("[%s]:> %s", nickname, message));
        } catch (IOException e) {
            throw new ChatServerException("Something went wrong during sending the message.", e);
        }
    }

    @Override
    public void sendStatusMessage(ClientHandler clientHandler, String message) {
        try {
            clientHandler.sendData(message);
        } catch (IOException e) {
            throw new ChatServerException("Something went wrong during sending the status message.", e);
        }
    }

    @Override
    public void sendPersonalMessage(ClientHandler clientHandler, String nickname, String message) {
        try {
            clientHandler.sendData(String.format("[Private]%s:> %s", nickname, message));
        } catch (IOException e) {
            throw new ChatServerException("Something went wrong during sending the private message.", e);
        }
    }
}
