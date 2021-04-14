package ru.geekbrains.lesson2.server;

import ru.geekbrains.lesson2.server.service.AuthService;

public class ServerRouter implements Router {
    private AuthService authService;
    private Sender sender;

    public ServerRouter(AuthService authService, Sender sender) {
        this.authService = authService;
        this.sender = sender;
    }

    @Override
    public void broadcast(ClientHandler clientHandler, String message) {
        for (ClientHandler client : authService.getLoggedUser()) {
            if (!client.equals(clientHandler)) {
                String nickname = clientHandler.getUser().getNickname();
                sender.sendMessage(client, nickname, message);
            }
        }
    }

    @Override
    public void broadcast(String message) {
        for (ClientHandler client : authService.getLoggedUser()) {
            sender.sendStatusMessage(client, message);
        }
    }

    @Override
    public void unicast(ClientHandler clientHandler, String nickname, String message) {
        for (ClientHandler client : authService.getLoggedUser()) {
            if (client.getUser().getNickname().equals(nickname)) {
                String senderNickname = clientHandler.getUser().getNickname();
                sender.sendPersonalMessage(client, senderNickname, message);
                break;
            }
        }
    }
}
