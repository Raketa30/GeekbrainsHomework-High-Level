package ru.geekbrains.chat_app.server.service;

import ru.geekbrains.chat_app.server.ClientHandler;
import ru.geekbrains.chat_app.server.entity.User;

import java.util.Map;
import java.util.Optional;

public class ChatUserService {
    private final UserRepo userRepo;
    private final AuthService authService;
    private final RegService regService;

    public ChatUserService() {
        userRepo = new ChatUserRepo();
        authService = new ChatAuthService();
        regService = new ChatRegService(userRepo);
    }

    public void changeUserNickname(User user) {
        userRepo.updateUser(user);
    }

    public void logIn(ClientHandler clientHandler, User user) {
        authService.logIn(clientHandler, user);
    }

    public void logOut(ClientHandler clientHandler) {
        authService.logOut(clientHandler);
    }

    public boolean isLoggedIn(ClientHandler clientHandler) {
        return authService.isLoggedIn(clientHandler);
    }

    public void registerNewUser(User user) {
        regService.register(user);
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        return userRepo.findUserByLoginAndPassword(login, password);
    }

    public boolean findByLogin(String login) {
        return userRepo.findUserByLogin(login);
    }

    public Map<ClientHandler, User> findLoggedUsers() {
        return authService.getLoggedUsers();
    }

    public Optional<User> checkLoggedUserByNickname(String userNickname) {
       return authService.findUserByNickname(userNickname);
    }
}
