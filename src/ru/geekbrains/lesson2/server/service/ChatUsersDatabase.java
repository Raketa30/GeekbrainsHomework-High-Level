package ru.geekbrains.lesson2.server.service;

import ru.geekbrains.lesson2.server.entity.User;

import java.util.HashSet;
import java.util.Set;

public class ChatUsersDatabase {
    private final Set<User> userSet = new HashSet<>();

    public ChatUsersDatabase() {
        userSet.add(new User("user1", "u1", "p1"));
        userSet.add(new User("user2", "u2", "p2"));
        userSet.add(new User("user3", "u3", "p3"));
        userSet.add(new User("user4", "u4", "p4"));
    }

    public Set<User> getUserSet() {
        return userSet;
    }
}
