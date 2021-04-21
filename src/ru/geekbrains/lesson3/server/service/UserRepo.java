package ru.geekbrains.lesson3.server.service;

import ru.geekbrains.lesson3.server.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo {
    List<User> findAll();

    boolean findUserByLogin(String login);

    Optional<User> findUserByLoginAndPassword(String login, String password);

    void updateUser(User user);

    void registerUser(User user);
}
