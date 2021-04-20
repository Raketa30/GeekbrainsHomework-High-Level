package ru.geekbrains.chat_app.client.gui;

import ru.geekbrains.chat_app.client.gui.api.Receiver;
import ru.geekbrains.chat_app.client.gui.api.Sender;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;

public class InputTextPanel extends JPanel {
    private final JTextArea chatArea;
    private boolean logged = false;
    private String username;

    public InputTextPanel(JTextArea chatArea, Sender<String> sender) {
        this.chatArea = chatArea;
        JTextField textField = new JTextField();
        JButton button = new JButton("Enter");

        DefaultCaret caret = (DefaultCaret)chatArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

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
                    String messageToChat = transformMessage(message);
                    chatArea.append(messageToChat + "\n");
                    addToHistory(messageToChat);
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
                        String messageToChat = transformMessage(message);
                        chatArea.append(messageToChat + "\n");
                        scrollDown();
                        addToHistory(messageToChat);
                    }
                    sender.send(message);
                    textField.setText("");
                }
            }
        });

        add(textField, BorderLayout.CENTER);
        add(button, BorderLayout.EAST);
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
                    fillHistory();
                } else if (message.equals("Auth timeout")) {
                    chatArea.append(message);
                    logged = false;
                } else {
                    chatArea.append(message);
                    chatArea.append("\n");
                    addToHistory(message);
                }
            }
        };
    }

    private void fillHistory() {
        if (logged && username != null) {
            File fileHistory = new File("user_" + username);
            try (BufferedReader reader = new BufferedReader(new FileReader(fileHistory))) {
                for (int i = 0; i < 100; i++) {
                    if (!reader.ready()) {
                        break;
                    } else {
                        chatArea.append(reader.readLine() + "\n");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addToHistory(String line) {
        if (logged) {
            File fileHistory = new File("user_" + username);
            try (BufferedWriter reader = new BufferedWriter(new FileWriter(fileHistory, true))) {
                reader.write(line);
                reader.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void scrollDown(){
        DefaultCaret caret = (DefaultCaret)chatArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }
}
