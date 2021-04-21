package ru.geekbrains.lesson3.server.transmitter;

import ru.geekbrains.lesson3.server.ClientHandler;

public interface Sender<T> {
    void sendMessage(ClientHandler clientHandler, String nickname, T data);

    void sendPersonalMessage(ClientHandler clientHandler, String nickname, T data);

    void sendStatusMessage(ClientHandler clientHandler, T data);
}
