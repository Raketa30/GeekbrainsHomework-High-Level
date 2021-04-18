package ru.geekbrains.lesson2.client.gui.api;

@FunctionalInterface
public interface Receiver {
    void receive(String data);
}
