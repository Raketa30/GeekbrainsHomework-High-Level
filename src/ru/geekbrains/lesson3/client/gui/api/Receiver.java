package ru.geekbrains.lesson3.client.gui.api;

@FunctionalInterface
public interface Receiver<T> {
    void receive(T data);
}
