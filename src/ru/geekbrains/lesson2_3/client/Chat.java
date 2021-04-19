package ru.geekbrains.lesson2_3.client;

import ru.geekbrains.lesson2_3.client.gui.ChatFrame;
import ru.geekbrains.lesson2_3.client.gui.api.ChatClientCommunication;
import ru.geekbrains.lesson2_3.client.gui.api.Receiver;

public class Chat {
    private final ChatFrame frame;
    private final ChatClientCommunication communication;

    public Chat(String host, int port) {
        communication = new ChatClientCommunication(host, port);
        frame = new ChatFrame(communication::send);

        new Thread(() -> {
            Receiver receiver = frame.getTextPanel().getReceiver();
            while (true) {
                String msg = communication.receive();
                if (!msg.isBlank()) {
                    receiver.receive(msg);
                }
            }
        }).start();
        //

    }


}
