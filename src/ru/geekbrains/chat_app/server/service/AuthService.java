package ru.geekbrains.chat_app.server.service;

import ru.geekbrains.chat_app.server.ClientHandler;
import ru.geekbrains.chat_app.server.entity.User;

import java.util.Map;
import java.util.Optional;

public interface AuthService {
    void logIn(ClientHandler clientHandler, User user);

    void logOut(ClientHandler clientHandler);

    boolean isLoggedIn(ClientHandler clientHandler);

    Map<ClientHandler, User> getLoggedUsers();

    Optional<User> findUserByNickname(String userNickname);
}
