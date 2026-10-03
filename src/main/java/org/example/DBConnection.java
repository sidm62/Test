package org.example;

import java.sql.Connection;

public class DBConnection {
    private static final String URL = "jdbc:mariadb://localhost:3306/temp";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = java.sql.DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (java.sql.SQLException e) {
               throw new RuntimeException("Tietokantayhteyden muodostaminen epäonnistui: " + e.getMessage(), e);
            }
        }
        return connection;
    }

}
