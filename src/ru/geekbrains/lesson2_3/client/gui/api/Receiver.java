package ru.geekbrains.lesson2_3.client.gui.api;

@FunctionalInterface
public interface Receiver {
    void receive(String data);
}
