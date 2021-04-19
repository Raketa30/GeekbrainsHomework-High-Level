package ru.geekbrains.lesson2_3.server;

import ru.geekbrains.lesson2_3.server.service.UserService;

public class ServerRouter implements Router {
    private final UserService userService;
    private final Sender sender;

    public ServerRouter(Sender sender, UserService userService) {
        this.sender = sender;
        this.userService = userService;
    }

    @Override
    public void broadcast(ClientHandler clientHandler, String message) {
        synchronized (userService) {
            for (ClientHandler client : userService.getLoggedUser()) {
                if (!client.equals(clientHandler)) {
                    String nickname = clientHandler.getUser().getNickname();
                    sender.sendMessage(client, nickname, message);
                }
            }
        }
    }

    @Override
    public void broadcast(String message) {
        synchronized (userService) {
            for (ClientHandler client : userService.getLoggedUser()) {
                sender.sendStatusMessage(client, message);
            }
        }
    }

    @Override
    public void unicast(ClientHandler clientHandler, String nickname, String message) {
        synchronized (userService) {
            for (ClientHandler client : userService.getLoggedUser()) {
                if (client.getUser().getNickname().equals(nickname)) {
                    String senderNickname = clientHandler.getUser().getNickname();
                    sender.sendPersonalMessage(client, senderNickname, message);
                    break;
                }
            }
        }
    }
}
