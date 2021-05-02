package ru.geekbrains.lesson3.client.gui;

import ru.geekbrains.lesson3.client.gui.api.ClientHistoryProcessor;
import ru.geekbrains.lesson3.client.gui.api.HistoryProcessor;
import ru.geekbrains.lesson3.client.gui.api.Receiver;
import ru.geekbrains.lesson3.client.gui.api.Sender;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClientMessageAction extends KeyAdapter implements ActionListener {
    private final JTextArea chatArea;
    private final JTextField textField;
    private final Sender<String> sender;
    private final HistoryProcessor historyProcessor;
    private boolean logged = false;
    private String username;

    public ClientMessageAction(JTextArea chatArea, JTextField textField, Sender<String> sender) {
        this.chatArea = chatArea;
        this.textField = textField;
        this.sender = sender;
        this.historyProcessor = new ClientHistoryProcessor(chatArea);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '\n' && !textField.getText().equals("")) {
            prepareAndSendMessage();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!textField.getText().equals("")) {
            prepareAndSendMessage();
        }
    }

    private void prepareAndSendMessage() {
        String message = textField.getText();
        if (logged) {
            String messageToChat = transformMessage(message);
            chatArea.append(messageToChat + "\n");
            historyProcessor.addToHistory(username, messageToChat);
        }

        sender.send(message);
        textField.setText("");
    }


    private String transformMessage(String message) {
        return "[YOU]: " + message;
    }

    public Receiver<String> getReceiver() {
        return (message) -> {
            if (!message.isBlank()) {
                if (message.startsWith("-?logged")) {
                    logged = true;
                    username = message.split(" ")[1];
                    chatArea.append( "For change nickname enter: /change new_nickname");

                    if (username != null) {
                        historyProcessor.takeFromHistory(username);
                    }

                } else if (message.equals("Auth timeout")) {
                    chatArea.append(message);
                    logged = false;

                } else {
                    chatArea.append(message);
                    chatArea.append("\n");
                    if(logged) {
                        historyProcessor.addToHistory(username, message);
                    }
                }
            }
        };
    }

}
