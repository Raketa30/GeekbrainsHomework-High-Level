package ru.geekbrains.chat_app.server.transmitter;

import ru.geekbrains.chat_app.server.ClientHandler;
import ru.geekbrains.chat_app.server.service.ChatUserService;

public class MessageTransmitter {
    private final ChatUserService chatUserService;
    private final Router<String> router;
    private final Sender<String> sender;

    public MessageTransmitter(ChatUserService chatUserService) {
        this.chatUserService = chatUserService;
        this.sender = new ServerSender();
        this.router = new ServerRouter(sender, chatUserService);
    }

    public void unicast(ClientHandler clientHandler, String nickname, String formedMessage) {
        router.unicast(clientHandler, nickname, formedMessage);
    }

    public void broadcast(ClientHandler clientHandler, String message) {
        router.broadcast(clientHandler, message);
    }

    public void broadcast(String message) {
        router.broadcast(message);
    }

    public void sendStatusMessage(ClientHandler clientHandler, String currentUserNotLoggedOn) {
        sender.sendStatusMessage(clientHandler, currentUserNotLoggedOn);
    }

    public ChatUserService getChatUserService() {
        return chatUserService;
    }
}
