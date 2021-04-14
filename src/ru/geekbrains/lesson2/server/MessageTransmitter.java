package ru.geekbrains.lesson2.server;

import ru.geekbrains.lesson2.server.service.AuthService;
import ru.geekbrains.lesson2.server.service.ChatUsersRepository;
import ru.geekbrains.lesson2.server.service.RegistrationService;

public class MessageTransmitter {
    private final RegistrationService registrationService;
    private final AuthService authService;
    private final Router router;
    private final Sender sender;

    public MessageTransmitter(ChatUsersRepository repository) {
        registrationService = new RegistrationService(repository);
        authService = new AuthService(repository);
        this.sender = new ServerSender();
        this.router = new ServerRouter(authService, sender);
    }

    public AuthService getAuthService() {
        return authService;
    }

    public RegistrationService getRegistrationService() {
        return registrationService;
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
}
