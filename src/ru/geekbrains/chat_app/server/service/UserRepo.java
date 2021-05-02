package ru.geekbrains.chat_app.server.service;

import ru.geekbrains.chat_app.server.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo {
    List<User> findAll();

    boolean findUserByLogin(String login);

    Optional<User> findUserByLoginAndPassword(String login, String password);

    void updateUser(User user);

    void registerUser(User user);
}
