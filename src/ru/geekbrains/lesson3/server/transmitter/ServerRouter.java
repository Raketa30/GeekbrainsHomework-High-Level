package ru.geekbrains.lesson3.server.transmitter;

import ru.geekbrains.lesson3.server.ClientHandler;
import ru.geekbrains.lesson3.server.entity.User;
import ru.geekbrains.lesson3.server.service.ChatUserService;

import java.util.Map;

public class ServerRouter implements Router<String> {
    private final Map<ClientHandler, User> loggedHandlers;
    private final Sender<String> sender;

    public ServerRouter(Sender<String> sender, ChatUserService chatUserService) {
        this.sender = sender;
        this.loggedHandlers = chatUserService.findLoggedUsers();
    }

    @Override
    public void broadcast(ClientHandler handlerSender, String message) {
        for (ClientHandler handlerReceiver : loggedHandlers.keySet()) {
            if (!handlerReceiver.equals(handlerSender)) {
                String nickname = loggedHandlers.get(handlerSender).getNickname();
                this.sender.sendMessage(handlerReceiver, nickname, message);
            }
        }
    }

    @Override
    public void broadcast(String message) {
        for (ClientHandler client : loggedHandlers.keySet()) {
            sender.sendStatusMessage(client, message);
        }
    }

    @Override
    public void unicast(ClientHandler clientHandler, String nickname, String message) {
        for (ClientHandler client : loggedHandlers.keySet()) {

            if (loggedHandlers.get(client).getNickname().equals(nickname)) {
                String senderNickname = loggedHandlers.get(clientHandler).getNickname();
                sender.sendPersonalMessage(client, senderNickname, message);
                break;
            }
        }
    }
}
