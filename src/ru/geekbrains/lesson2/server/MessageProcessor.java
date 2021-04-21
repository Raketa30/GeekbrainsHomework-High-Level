package ru.geekbrains.lesson2.server;

import ru.geekbrains.lesson2.server.entity.User;
import ru.geekbrains.lesson2.server.exceptions.ChatServerException;
import ru.geekbrains.lesson2.server.service.UserService;

import java.io.IOException;
import java.util.Optional;

public class MessageProcessor {
    private final MessageTransmitter transmitter;
    private final ClientHandler clientHandler;
    private final UserService service;

    public MessageProcessor(MessageTransmitter transmitter, ClientHandler clientHandler) {
        this.transmitter = transmitter;
        this.clientHandler = clientHandler;
        service = transmitter.getUserService();
        authentication();
    }

    public void processMessage(String data) {
        if (data.startsWith("/w ")) {
            sendPrivateMessage(data);

        } else if (data.equals("-quit")) {
            transmitter.getUserService().unscribe(clientHandler);

        } else if (data.startsWith("/change")) {
            changeUserNickname(data);

        } else {
            System.out.println(data);
            transmitter.broadcast(clientHandler, data);
        }
    }

    private void authentication() {
        try {
            boolean access = false;
            while (!access) {
                String request = clientHandler.readData();

                if (checkCredentials(request)) {
                    if (request.startsWith("-auth")) {
                        access = processAuthentication(request);

                    } else if (request.startsWith("-register")) {
                        processRegistration(request);
                    }
                } else {
                    transmitter.sendStatusMessage(clientHandler, "Incorrect authentication request");
                }

            }
        } catch (IOException e) {
            throw new ChatServerException("Something went wrong in auth process", e);
        }
    }

    private void processRegistration(String request) {
        if (service.registration(request)) {
            transmitter.sendStatusMessage(clientHandler, "Successfully registered, please auth");
            return;
        }

        transmitter.sendStatusMessage(clientHandler, "Try again");
    }

    private boolean processAuthentication(String request) {
        String[] loginPassword = request.split("\\s+");
        Optional<User> currentUser = service.findByLoginAndPassword(loginPassword[1], loginPassword[2]);

        if (currentUser.isPresent()) {
            if (!service.isLoggedIn(currentUser.get())) {
                System.out.printf("User %s, logged in\n", currentUser.get().getNickname());

                transmitter.broadcast(String.format("User %s, logged in", currentUser.get().getNickname()));
                clientHandler.setUser(currentUser.get());

                service.subscribe(clientHandler);

                transmitter.sendStatusMessage(clientHandler, "Connected to main chat");
                transmitter.sendStatusMessage(clientHandler, "For change nickname enter: /change new_nickname");
                return true;

            } else {
                transmitter.sendStatusMessage(clientHandler, "Current user logged on!");
            }

        } else {
            transmitter.sendStatusMessage(clientHandler, "Wrong login or password");
        }
        return false;
    }

    private void changeUserNickname(String message) {
        String oldNickname = clientHandler.getUser().getNickname();
        String currentNickname = takeUserNicknameFromMessage(message);

        clientHandler.getUser().setNickname(currentNickname);
        service.changeUserNickname(clientHandler.getUser());
        transmitter.broadcast(oldNickname + " changed nickname to " + currentNickname);
    }

    private void sendPrivateMessage(String message) {
        if (checkUser(message)) {
            String nickname = takeUserNicknameFromMessage(message);
            String formedMessage = formPersonalMessage(message);

            transmitter.unicast(clientHandler, nickname, formedMessage);

        } else {
            transmitter.sendStatusMessage(clientHandler, "Current user not logged on");
        }
    }

    private boolean checkUser(String message) {
        String[] arr = message.split("\\s+");
        return service.checkLoggedUserByNickname(arr[1]);
    }

    private String takeUserNicknameFromMessage(String message) {
        String[] mess = message.split("\\s+");
        return mess[1];
    }

    private String formPersonalMessage(String message) {
        String[] mess = message.split("\\s+");
        StringBuilder builder = new StringBuilder();

        for (int i = 2; i < mess.length; i++) {
            builder.append(mess[i]).append(" ");
        }
        return builder.toString();
    }

    private boolean checkCredentials(String credentials) {
        String auth = "-auth\\s+([a-zA-Z0-9]{2,10})\\s+([a-zA-Z0-9]{2,10})";
        String register = "-register\\s+([a-zA-Z0-9]{2,10})\\s+([a-zA-Z0-9]{2,10})\\s+([a-zA-Z0-9]{2,10})";

        return credentials.matches(auth) || credentials.matches(register);
    }


}
