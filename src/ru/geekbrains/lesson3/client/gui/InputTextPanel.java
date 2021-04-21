package ru.geekbrains.lesson3.client.gui;

import ru.geekbrains.lesson3.client.gui.api.Sender;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;


public class InputTextPanel extends JPanel {
    private final ClientMessageAction messageAction;
    public InputTextPanel(JTextArea chatArea, Sender<String> sender) {
        JTextField textField = new JTextField();
        JButton button = new JButton("Enter");

        messageAction = new ClientMessageAction(chatArea, textField, sender);

        DefaultCaret caret = (DefaultCaret) chatArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        chatArea.append("Connected to chat server..\n");
        chatArea.append("Please enter auth message: -auth login password\n");
        chatArea.append("For registration enter: -register nickname login password\n");

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(490, 40));

        button.setPreferredSize(new Dimension(50, 40));
        button.addActionListener(messageAction);
        textField.addKeyListener(messageAction);

        add(textField, BorderLayout.CENTER);
        add(button, BorderLayout.EAST);
    }

    public ClientMessageAction getMessageAction() {
        return messageAction;
    }
}
