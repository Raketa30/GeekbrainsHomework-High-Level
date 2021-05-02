package ru.geekbrains.lesson3.server.service;

import ru.geekbrains.lesson3.server.entity.User;

public class ChatRegService implements RegService{
    private final UserRepo userRepo;

    public ChatRegService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void register(User user) {
        userRepo.registerUser(user);
    }
}
