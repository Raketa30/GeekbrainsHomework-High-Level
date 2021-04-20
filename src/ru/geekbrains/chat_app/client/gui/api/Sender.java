package ru.geekbrains.chat_app.client.gui.api;

@FunctionalInterface
public interface Sender<T> {
    void send(T data);
}
