package ru.geekbrains.lesson3.server.transmitter;

import ru.geekbrains.lesson3.server.ClientHandler;

public interface Router<T> {
    void broadcast(T data);

    void broadcast(ClientHandler user, T data);

    void unicast(ClientHandler user, String nickname, T data);
}
