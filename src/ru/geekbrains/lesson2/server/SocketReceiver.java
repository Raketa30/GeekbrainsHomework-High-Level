package ru.geekbrains.lesson2.server;

import ru.geekbrains.lesson2.server.exceptions.ChatServerException;

import java.io.DataInputStream;
import java.io.IOException;

public class SocketReceiver implements Receiver {
    private final DataInputStream in;
    private final MessageTransmitter messageTransmitter;
    private final ClientHandler clientHandler;

    public SocketReceiver(DataInputStream in, MessageTransmitter messageTransmitter, ClientHandler clientHandler) {
        this.in = in;
        this.messageTransmitter = messageTransmitter;
        this.clientHandler = clientHandler;
    }

    @Override
    public void receiveMessage() {
        while (true) {
            try {
                String message = in.readUTF();
                if (message.startsWith("/w ")) {
                    if (checkUser(message)) {
                        String nickname = takeUserNicknameFromMessage(message);
                        String formedMessage = formPersonalMessage(message);

                        messageTransmitter.unicast(clientHandler, nickname, formedMessage);

                    } else {
                        messageTransmitter.sendStatusMessage(clientHandler, "Current user not logged on");
                    }

                } else if (message.equals("-quit")) {
                    messageTransmitter.getAuthService().unscribe(clientHandler);
                    break;

                } else if (message.startsWith("/change")) {
                    String oldNickname = clientHandler.getUser().getNickname();
                    String currentNickname = takeUserNicknameFromMessage(message);

                    clientHandler.getUser().setNickname(currentNickname);
                    messageTransmitter.getAuthService().changeUserNickname(clientHandler.getUser());
                    messageTransmitter.broadcast(oldNickname + " changed nickname to " + currentNickname);
                }

                else {
                    System.out.println(message);
                    messageTransmitter.broadcast(clientHandler, message);
                }

            } catch (IOException e) {
                messageTransmitter.getAuthService().unscribe(clientHandler);
                throw new ChatServerException("Something went wrong during receiving the message.", e);
            }
        }

    }

    private boolean checkUser(String message) {
        String[] arr = message.split("\\s+");
        return messageTransmitter.getAuthService().checkLoggedUserByNickname(arr[1]);
    }

    private String takeUserNicknameFromMessage(String message) {
        String[] mess = message.split("\\s+");
        return mess[1];
    }

    private String formPersonalMessage(String message) {
        String[] mess = message.split("\\s+");
        StringBuilder builder = new StringBuilder();

        for (int i = 2; i < mess.length; i++) {
            builder.append(mess[i]).append(" ");
        }
        return builder.toString();
    }


}
