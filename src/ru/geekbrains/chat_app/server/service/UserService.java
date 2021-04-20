package ru.geekbrains.chat_app.server.service;

import ru.geekbrains.chat_app.server.ClientHandler;
import ru.geekbrains.chat_app.server.entity.User;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserService {
    private final ChatUserRepo repository = new ChatUserRepo();
    private final Set<ClientHandler> loggedUser = new HashSet<>();

    public UserService() {

    }

    public void changeUserNickname(User user) {
        if (isLoggedIn(user)) {
            repository.updateUserNickname(user);
        }
    }

    public void subscribe(ClientHandler handler) {
        synchronized (loggedUser) {
            loggedUser.add(handler);
        }
    }

    public void unscribe(ClientHandler handler) {
        synchronized (loggedUser) {
            loggedUser.remove(handler);
        }
    }

    public boolean isLoggedIn(User user) {
        synchronized (loggedUser) {
            return loggedUser.stream().anyMatch(client -> client.getUser().equals(user));
        }
    }

    public boolean checkLoggedUserByNickname(String nickname) {
        synchronized (loggedUser) {
            return loggedUser.stream()
                    .anyMatch(client -> client.getUser().getNickname().equals(nickname));
        }
    }

    public Set<ClientHandler> getLoggedUser() {
        synchronized (loggedUser) {
            return loggedUser;
        }
    }

    public void registerNewUser(User user) {
        repository.registerUser(user);
    }

    public boolean registration(String inputMessage) {
        Optional<User> user = takeNewUserCredentials(inputMessage);

        if (user.isPresent() && !repository.findUserByLogin(user.get().getLogin())) {
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

    public Optional<User> findByLoginAndPassword(String login, String password) {
        return repository.findByLoginAndPassword(login, password);
    }
}
