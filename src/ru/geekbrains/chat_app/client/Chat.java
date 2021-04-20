package ru.geekbrains.chat_app.client;

import ru.geekbrains.chat_app.client.gui.ChatFrame;
import ru.geekbrains.chat_app.client.gui.api.ChatClientCommunication;
import ru.geekbrains.chat_app.client.gui.api.Receiver;

public class Chat {
    private final ChatFrame frame;
    private final ChatClientCommunication communication;

    public Chat(String host, int port) {
        communication = new ChatClientCommunication(host, port);
        frame = new ChatFrame(communication::send);

        new Thread(() -> {
            Receiver<String> receiver = frame.getTextPanel().getReceiver();
            while (true) {
                String msg = communication.receive();
                if (!msg.isBlank()) {
                    receiver.receive(msg);
                }
            }
        }).start();
    }


}
