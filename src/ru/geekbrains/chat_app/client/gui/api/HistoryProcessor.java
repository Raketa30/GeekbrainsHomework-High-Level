package ru.geekbrains.chat_app.client.gui.api;

public interface HistoryProcessor {
    void addToHistory(String username, String line);

    void takeFromHistory(String username);
}
