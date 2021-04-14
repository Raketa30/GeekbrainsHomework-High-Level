package ru.geekbrains.lesson2.server.service;

public class RegistrationService {
    private final ChatUsersRepository repository;

    public RegistrationService(ChatUsersRepository repository) {
        this.repository = repository;
    }

    public boolean findUserByLogin(String login) {
        return repository.findAll().stream()
                .anyMatch(user -> user.getLogin().equals(login));
    }


}
