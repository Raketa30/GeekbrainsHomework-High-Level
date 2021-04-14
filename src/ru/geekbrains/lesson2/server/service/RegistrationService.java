package ru.geekbrains.lesson2.server.service;

import ru.geekbrains.lesson2.server.entity.User;

import java.util.Optional;

public class RegistrationService {
    private final ChatUsersRepository repository;

    public RegistrationService(ChatUsersRepository repository) {
        this.repository = repository;
    }

    public synchronized boolean findUserByLogin(String login) {
        return repository.findAll().stream()
                .anyMatch(user -> user.getLogin().equals(login));
    }

    public synchronized void registerNewUser(User user) {
        repository.registerUser(user);
    }

    public boolean registration(String inputMessage) {
        Optional<User> user = takeNewUserCredentials(inputMessage);

        if (user.isPresent() && !findUserByLogin(user.get().getLogin())) {
            registerNewUser(user.get());
            return true;
        }

        return false;
    }

    private Optional<User> takeNewUserCredentials(String message) {
        String[] credentials = message.split("\\s+");

        if (credentials.length > 4) {
            return Optional.empty();
        }

        User usr = new User(credentials[1], credentials[2], credentials[3]);
        return Optional.of(usr);
    }
}

