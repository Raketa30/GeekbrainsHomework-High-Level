package ru.geekbrains.chat_app.server.service;

import ru.geekbrains.chat_app.server.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChatUserRepo implements UserRepo{

    @Override
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
                users.add(user);
            }
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return users;
    }
    @Override
    public boolean findUserByLogin(String login) {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE login = ?");
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?");
            statement.setString(1, login);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User(
                        resultSet.getString("nickname"),
                        resultSet.getString("login"),
                        resultSet.getString("password")
                );

                return Optional.of(user);
            }
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }
    @Override
    public void updateUser(User user) {
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("UPDATE users SET nickname = '%s' WHERE login = '%s'", user.getNickname(), user.getLogin());
            statement.execute(query);
            connection.commit();
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void registerUser(User user) {
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            if (!findAll().contains(user)) {
                String query = String.format("INSERT INTO users(nickname, login, password) VALUES('%s', '%s', '%s');", user.getNickname(), user.getLogin(), user.getPassword());
                statement.execute(query);
                connection.commit();
                connection.close();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
