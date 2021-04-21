package ru.geekbrains.lesson3.server.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:sqlite:/Users/duckpool/dev/GeekBrainsJavaCourses/GeekbrainsHomework-High-Level/chatdb"
        );
    }
}
