package ru.geekbrains.lesson2.server.service;

import ru.geekbrains.lesson2.server.entity.User;

public class Test {
    public static void main(String[] args) {
        ChatUsersRepository repository = new ChatUsersRepository();
        System.out.println(repository.findAll());
        User user = new User("noviy", "n1", "pn1");
        repository.registerUser(user);
        System.out.println(repository.findAll());
        user.setNickname("vasya");
        repository.updateUserNickname(user);
        System.out.println(repository.findAll());


    }
}
