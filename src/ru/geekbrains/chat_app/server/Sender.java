package ru.geekbrains.chat_app.server;

public interface Sender<T> {
    void sendMessage(ClientHandler clientHandler, String nickname, T data);

    void sendPersonalMessage(ClientHandler clientHandler, String nickname, T data);

    void sendStatusMessage(ClientHandler clientHandler, T data);
}
