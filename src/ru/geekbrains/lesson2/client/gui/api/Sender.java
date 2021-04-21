package ru.geekbrains.lesson2.client.gui.api;

@FunctionalInterface
public interface Sender {
    void send(String message);
}
