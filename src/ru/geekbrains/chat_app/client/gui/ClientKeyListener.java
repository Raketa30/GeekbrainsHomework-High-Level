package ru.geekbrains.chat_app.client.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClientKeyListener extends KeyAdapter implements ActionListener {
    private final JTextArea chatArea;

    public ClientKeyListener(JTextArea chatArea) {
        this.chatArea = chatArea;
    }


    @Override
    public void keyTyped(KeyEvent e) {
        super.keyTyped(e);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
