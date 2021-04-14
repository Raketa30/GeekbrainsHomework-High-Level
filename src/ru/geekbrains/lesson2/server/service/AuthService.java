package ru.geekbrains.lesson2.server.service;

import ru.geekbrains.lesson2.server.ClientHandler;
import ru.geekbrains.lesson2.server.MessageTransmitter;
import ru.geekbrains.lesson2.server.entity.User;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AuthService {
    private final ChatUsersDatabase chatUsersDatabase = new ChatUsersDatabase();
    private final Set<ClientHandler> loggedUser = new HashSet<>();

    public synchronized Optional<User> findByLoginAndPassword(String login, String password) {
        return chatUsersDatabase.getUserSet()
                .stream()
                .filter(user -> user.getLogin().equals(login)
                        && user.getPassword().equals(password))
                .findFirst();

    }

    public synchronized void subscribe(ClientHandler handler) {
        loggedUser.add(handler);
    }

    public synchronized void unscribe(ClientHandler handler) {
        loggedUser.remove(handler);
    }

    public synchronized boolean isLoggedIn(User user) {
        return loggedUser.stream().anyMatch(client -> client.getUser().equals(user));
    }

    public synchronized boolean checkLoggedUserByNickname(String nickname) {
        return loggedUser.stream()
                .anyMatch(client -> client.getUser().getNickname().equals(nickname));
    }

    public Set<ClientHandler> getLoggedUser() {
        return loggedUser;
    }

    public boolean authentication(ClientHandler clientHandler, String authMessage) {
        MessageTransmitter transmitter = clientHandler.getMessageTransmitter();

        if (checkCredentials(authMessage)) {
            String[] loginPassword = authMessage.split("\\s+");

            Optional<User> currentUser = findByLoginAndPassword(loginPassword[1], loginPassword[2]);

            if (currentUser.isPresent()) {
                if (!isLoggedIn(currentUser.get())) {
                    System.out.printf("User %s, logged in\n", currentUser.get().getNickname());

                    transmitter.broadcast(String.format("User %s, logged in", currentUser.get().getNickname()));
                    clientHandler.setUser(currentUser.get());

                    subscribe(clientHandler);

                    transmitter.sendStatusMessage(clientHandler, "Connected to main chat");
                    return true;

                } else {
                    transmitter.sendStatusMessage(clientHandler, "Current user logged on!");
                }

            } else {
                transmitter.sendStatusMessage(clientHandler, "Wrong login or password");
            }

        } else {
            transmitter.sendStatusMessage(clientHandler, "Incorrect authentication request");
        }

        return false;
    }

    private boolean checkCredentials(String credentials) {
        String reg = "-auth\\s+([a-zA-Z0-9]{2,10})\\s+([a-zA-Z0-9]{2,10})";
        return credentials.matches(reg);
    }

}
