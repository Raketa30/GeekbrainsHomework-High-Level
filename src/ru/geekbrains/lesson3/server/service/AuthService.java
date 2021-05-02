package ru.geekbrains.lesson3.server.service;

import ru.geekbrains.lesson3.server.ClientHandler;
import ru.geekbrains.lesson3.server.entity.User;

import java.util.Map;
import java.util.Optional;

public interface AuthService {
    void logIn(ClientHandler clientHandler, User user);

    void logOut(ClientHandler clientHandler);

    boolean isLoggedIn(ClientHandler clientHandler);

    Map<ClientHandler, User> getLoggedUsers();

    Optional<User> findUserByNickname(String userNickname);
}
