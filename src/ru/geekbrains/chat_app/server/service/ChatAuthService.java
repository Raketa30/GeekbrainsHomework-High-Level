package ru.geekbrains.chat_app.server.service;

import ru.geekbrains.chat_app.server.ClientHandler;
import ru.geekbrains.chat_app.server.entity.User;

import java.util.*;

public class ChatAuthService implements AuthService {
    private final Map<ClientHandler, User> loggedUser = new HashMap<>();

    @Override
    public void logIn(ClientHandler clientHandler, User user) {
        loggedUser.put(clientHandler, user);
    }

    @Override
    public void logOut(ClientHandler clientHandler) {
        loggedUser.remove(clientHandler);
    }

    @Override
    public boolean isLoggedIn(ClientHandler clientHandler) {
        return loggedUser.containsKey(clientHandler);
    }

    @Override
    public Map<ClientHandler, User> getLoggedUsers() {
        return Collections.unmodifiableMap(loggedUser);
    }

    @Override
    public Optional<User> findUserByNickname(String userNickname) {

        for(Map.Entry<ClientHandler, User> entry : loggedUser.entrySet()) {
            if(entry.getValue().getNickname().equals(userNickname)) {
                return Optional.of(entry.getValue());
            }
        }
        return Optional.empty();
    }
}
