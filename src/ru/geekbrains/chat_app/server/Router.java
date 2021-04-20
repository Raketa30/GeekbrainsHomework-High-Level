package ru.geekbrains.chat_app.server;

public interface Router<T> {
    void broadcast(T data);

    void broadcast(ClientHandler clientHandler, T data);

    void unicast(ClientHandler clientHandler, String nickname, T data);
}
