package ru.geekbrains.lesson3.client.gui.api;

public interface HistoryProcessor {
    void addToHistory(String username, String line);

    void takeFromHistory(String username);
}
