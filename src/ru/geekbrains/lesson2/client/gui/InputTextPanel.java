package ru.geekbrains.lesson2.client.gui;

import ru.geekbrains.lesson2.client.gui.api.Receiver;
import ru.geekbrains.lesson2.client.gui.api.Sender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputTextPanel extends JPanel {
    private final JTextArea chatArea;
    private boolean logged = false;

    public InputTextPanel(JTextArea chatArea, Sender sender) {
        this.chatArea = chatArea;
        JTextField textField = new JTextField();
        JButton button = new JButton("Enter");
        chatArea.setAutoscrolls(true);

        chatArea.append("Connected to chat server..\n");
        chatArea.append("Please enter auth message: -auth login password\n");
        chatArea.append("For registration enter: -register nickname login password\n");

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(490, 40));

        button.setPreferredSize(new Dimension(50, 40));

        button.addActionListener(e -> {
            if (!textField.getText().equals("")) {
                String message = textField.getText();
                if (logged) {
                    chatArea.append("[YOU]: " + message + "\n");
                }
                sender.send(message);
                textField.setText("");
            }
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n' && !textField.getText().equals("")) {
                    String message = textField.getText();
                    if (logged) {
                        chatArea.append("[YOU]: " + message + "\n");
                    }
                    sender.send(message);
                    textField.setText("");
                }
            }
        });

        add(textField, BorderLayout.CENTER);
        add(button, BorderLayout.EAST);
    }

    public Receiver getReceiver() {
        return (message) -> {
            if (!message.isBlank()) {
                if (message.equals("logged")) {
                    logged = true;

                } else if (message.equals("Auth timeout")) {
                    chatArea.append(message);
                    logged = false;

                } else {
                    chatArea.append(message);
                    chatArea.append("\n");
                }
            }
        };
    }
}
