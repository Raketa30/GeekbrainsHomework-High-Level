package ru.geekbrains.lesson2.server;

import ru.geekbrains.lesson2.server.exceptions.ChatServerException;

import java.io.IOException;

public class ServerSender implements Sender {
    @Override
    public void sendMessage(ClientHandler clientHandler, String nickname, String message) {
        if (clientHandler.getUser() != null) {
            try {
                clientHandler.getOut()
                        .writeUTF(String.format("[%s]:> %s", nickname, message));

            } catch (IOException e) {
                throw new ChatServerException("Something went wrong during sending the message.", e);
            }
        }
    }

    @Override
    public void sendStatusMessage(ClientHandler clientHandler, String message) {
        try {
            clientHandler.getOut().writeUTF(message);
        } catch (IOException e) {
            throw new ChatServerException("Something went wrong during sending the status message.", e);
        }
    }

    @Override
    public void sendPersonalMessage(ClientHandler clientHandler, String nickname, String message) {
        try {
            clientHandler.getOut().writeUTF(String.format("[Private]%s:> %s", nickname, message));
        } catch (IOException e) {
            throw new ChatServerException("Something went wrong during sending the private message.", e);
        }
    }
}
