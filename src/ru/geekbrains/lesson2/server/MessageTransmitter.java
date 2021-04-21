package ru.geekbrains.lesson2.server;

import ru.geekbrains.lesson2.server.service.UserService;

public class MessageTransmitter {
    private final UserService userService;
    private final Router router;
    private final Sender sender;

    public MessageTransmitter(UserService userService) {
        this.userService = userService;
        this.sender = new ServerSender();
        this.router = new ServerRouter(sender, userService);
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

    public UserService getUserService() {
        return userService;
    }
}
