package ru.geekbrains.lesson2.server;

import ru.geekbrains.lesson2.server.service.AuthService;

public class MessageTransmitter {
    private final AuthService authService = new AuthService();
    private final Router router;
    private final Sender sender;

    public MessageTransmitter() {
        this.sender = new ServerSender();
        this.router = new ServerRouter(authService, sender);
    }

    public AuthService getAuthService() {
        return authService;
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

    public void sendStatusMessage(ClientHandler clientHandler, String current_user_not_logged_on) {
        sender.sendStatusMessage(clientHandler, current_user_not_logged_on);
    }
}
