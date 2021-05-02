package ru.geekbrains.chat_app.client.gui.api;

@FunctionalInterface
public interface Receiver<T> {
    void receive(T data);
}
