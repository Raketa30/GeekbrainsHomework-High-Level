package ru.geekbrains.lesson2.server.service;

public class RegistrationService {
    private ChatUsersRepository repository;

    public RegistrationService(ChatUsersRepository repository) {
        this.repository = repository;
    }


}
