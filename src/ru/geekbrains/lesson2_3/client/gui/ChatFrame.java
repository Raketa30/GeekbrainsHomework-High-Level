package ru.geekbrains.lesson2_3.client.gui;

import ru.geekbrains.lesson2_3.client.gui.api.Sender;

import javax.swing.*;
import java.awt.*;

public class ChatFrame extends JFrame {
    private final InputTextPanel textPanel;

    public ChatFrame(Sender sender) {
        setTitle("Chat 1.0.0.0.1b");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setResizable(false);
        setLayout(new BorderLayout());

        JTextArea chatArea = new JTextArea();
        textPanel = new InputTextPanel(chatArea, sender);

        chatArea.setEditable(false);

        add(chatArea, BorderLayout.CENTER);
        add(textPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public InputTextPanel getTextPanel() {
        return textPanel;
    }
}
