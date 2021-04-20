package ru.geekbrains.chat_app.server.transmitter;

import ru.geekbrains.chat_app.server.ClientHandler;

public interface Sender<T> {
    void sendMessage(ClientHandler clientHandler, String nickname, T data);

    void sendPersonalMessage(ClientHandler clientHandler, String nickname, T data);

    void sendStatusMessage(ClientHandler clientHandler, T data);
}
