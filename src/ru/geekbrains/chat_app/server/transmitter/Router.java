package ru.geekbrains.chat_app.server.transmitter;

import ru.geekbrains.chat_app.server.ClientHandler;

public interface Router<T> {
    void broadcast(T data);

    void broadcast(ClientHandler user, T data);

    void unicast(ClientHandler user, String nickname, T data);
}
