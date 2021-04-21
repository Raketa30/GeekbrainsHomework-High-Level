package ru.geekbrains.lesson3.client.gui.api;

@FunctionalInterface
public interface Sender<T> {
    void send(T data);
}
