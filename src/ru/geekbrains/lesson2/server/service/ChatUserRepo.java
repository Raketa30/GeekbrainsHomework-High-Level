package ru.geekbrains.lesson2.server.service;

import ru.geekbrains.lesson2.server.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChatUserRepo {
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                User user = new User(resultSet.getString("nickname"),
                        resultSet.getString("login"),
                        resultSet.getString("password"));
                user.setId(resultSet.getInt("user_id"));
                users.add(user);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return users;
    }

    public boolean findUserByLogin(String login) {
        return findAll().stream()
                .anyMatch(user -> user.getLogin().equals(login));
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        return findAll()
                .stream()
                .filter(user -> user.getLogin().equals(login)
                        && user.getPassword().equals(password))
                .findFirst();
    }

    public void updateUserNickname(User user) {
        try {
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("UPDATE users SET nickname = '%s' WHERE login = '%s'", user.getNickname(), user.getLogin());
            statement.execute(query);
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void registerUser(User user) {
        try {
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            if (!findAll().contains(user)) {
                String query = String.format("INSERT INTO users(nickname, login, password) VALUES('%s', '%s', '%s');", user.getNickname(), user.getLogin(), user.getPassword());
                statement.execute(query);
            }
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
