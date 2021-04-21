package ru.geekbrains.chat_app.server;

import ru.geekbrains.chat_app.server.entity.User;
import ru.geekbrains.chat_app.server.exceptions.ChatServerException;
import ru.geekbrains.chat_app.server.service.ChatUserService;
import ru.geekbrains.chat_app.server.transmitter.MessageTransmitter;

import java.io.IOException;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class MessageProcessor {
    private final MessageTransmitter transmitter;
    private final ClientHandler clientHandler;
    private final ChatUserService chatUserService;
    private User processorUser;

    public MessageProcessor(MessageTransmitter transmitter, ClientHandler clientHandler) {
        this.transmitter = transmitter;
        this.clientHandler = clientHandler;
        chatUserService = transmitter.getChatUserService();
        authTimer();
        authentication();
    }

    private void authTimer() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(processorUser == null) {
                    clientHandler.shutdown();
                }
            }
        }, 120000);
    }

    public void processMessage(String data) {
        if (data.startsWith("/w ")) {
            sendPrivateMessage(data);

        } else if (data.equals("-quit")) {
            chatUserService.logOut(clientHandler);
            clientHandler.shutdown();

        } else if (data.startsWith("/change")) {
            changeUserNickname(data);

        } else {
            transmitter.broadcast(clientHandler, data);
        }
    }

    private void authentication() {
        Optional<User> optionalUser = Optional.empty();
        try {
            while (optionalUser.isEmpty()) {
                String request = clientHandler.readData();

                if (request != null) {
                    if (checkCredentials(request)) {
                        if (request.startsWith("-auth")) {
                            optionalUser = processAuthMessage(request);

                        } else if (request.startsWith("-register")) {
                            processRegisterMessage(request);
                        }

                    } else {
                        transmitter.sendStatusMessage(clientHandler, "Incorrect authentication request");
                    }
                }
            }

            optionalUser.ifPresent(user -> processorUser = user);

        } catch (IOException e) {
            throw new ChatServerException("Something went wrong in auth process", e);
        }
    }

    private boolean timer(long authStart) {
        long current = System.currentTimeMillis();
        return current - authStart > 120000;
    }

    private void processRegisterMessage(String request) {
        Optional<User> user = takeNewUserCredentials(request);

        if (user.isPresent()) {
            String login = user.get().getLogin();

            if (!chatUserService.findByLogin(login)) {
                chatUserService.registerNewUser(user.get());
                transmitter.sendStatusMessage(clientHandler, "Successfully registered, please auth");

            } else {
                transmitter.sendStatusMessage(clientHandler, "Current user already exist");
            }
        } else {
            transmitter.sendStatusMessage(clientHandler, "Try again");
        }
    }

    private Optional<User> processAuthMessage(String request) {
        String[] loginPassword = request.split("\\s+");
        Optional<User> currentUser = chatUserService.findByLoginAndPassword(loginPassword[1], loginPassword[2]);

        if (currentUser.isPresent()) {
            if (!chatUserService.isLoggedIn(clientHandler)) {
                System.out.printf("User %s, logged in\n", currentUser.get().getNickname());

                transmitter.broadcast(String.format("User %s, logged in", currentUser.get().getNickname()));
                transmitter.sendStatusMessage(clientHandler, "-?logged " + currentUser.get().getNickname());

                chatUserService.logIn(clientHandler, currentUser.get());
                return currentUser;

            } else {
                transmitter.sendStatusMessage(clientHandler, "Current user logged on!");
            }

        } else {
            transmitter.sendStatusMessage(clientHandler, "Wrong login or password");
        }
        return Optional.empty();
    }

    private void changeUserNickname(String request) {
        String oldNickname = processorUser.getNickname();
        String currentNickname = takeUserNicknameFromMessage(request);

        processorUser.setNickname(currentNickname);
        chatUserService.changeUserNickname(processorUser);
        transmitter.broadcast(oldNickname + " changed nickname to " + currentNickname);

    }

    private void sendPrivateMessage(String message) {
        Optional<User> user = findLoggedUser(message);
        if (user.isPresent()) {
            String nickname = takeUserNicknameFromMessage(message);
            String formedMessage = formPersonalMessage(message);
            transmitter.unicast(clientHandler, nickname, formedMessage);
        } else {
            transmitter.sendStatusMessage(clientHandler, "Current user not logged on");
        }
    }

    private Optional<User> findLoggedUser(String message) {
        String[] arr = message.split("\\s+");
        return chatUserService.checkLoggedUserByNickname(arr[1]);
    }

    private String takeUserNicknameFromMessage(String message) {
        String[] mess = message.split("\\s+");
        return mess[1];
    }

    private String formPersonalMessage(String personalMessageRequest) {
        String[] mess = personalMessageRequest.split("\\s+");
        StringBuilder builder = new StringBuilder();

        for (int i = 2; i < mess.length; i++) {
            builder.append(mess[i]).append(" ");
        }
        return builder.toString();
    }

    private boolean checkCredentials(String authOrRegRequest) {
        String auth = "-auth\\s+([a-zA-Z0-9]{2,10})\\s+([a-zA-Z0-9]{2,10})";
        String register = "-register\\s+([a-zA-Z0-9]{2,10})\\s+([a-zA-Z0-9]{2,10})\\s+([a-zA-Z0-9]{2,10})";

        return authOrRegRequest.matches(auth) || authOrRegRequest.matches(register);
    }

    private Optional<User> takeNewUserCredentials(String regRequest) {
        String[] credentials = regRequest.split("\\s+");

        if (credentials.length > 4) {
            return Optional.empty();
        }

        User usr = new User(credentials[1], credentials[2], credentials[3]);
        return Optional.of(usr);
    }


}
