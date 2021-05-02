package ru.geekbrains.lesson3.client.gui.api;

import org.apache.commons.io.input.ReversedLinesFileReader;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ClientHistoryProcessor implements HistoryProcessor {
    private final JTextArea chatArea;

    public ClientHistoryProcessor(JTextArea chatArea) {
        this.chatArea = chatArea;
    }

    @Override
    public void addToHistory(String username, String line) {
        File fileHistory = new File("user_" + username);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileHistory, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void takeFromHistory(String username) {
        File fileHistory = new File("user_" + username);
        chatArea.append("\n____________HISTORY START_____________");
        int caretPosition = chatArea.getCaretPosition();
        try (ReversedLinesFileReader reader = new ReversedLinesFileReader(fileHistory)) {
            try {
                for (int i = 0; i < 100; i++) {
                    chatArea.insert(reader.readLine(), caretPosition);
                    chatArea.insert("\n", caretPosition);
                }
            } catch (NullPointerException e) {
                System.out.println("empty file");
            }

            chatArea.append("\n____________HISTORY END_____________\n");

        } catch (IOException e) {
            System.out.println("file end");
        }

    }
}
