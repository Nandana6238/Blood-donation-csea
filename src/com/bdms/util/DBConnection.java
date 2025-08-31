package com.bdms.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    // Change these to your MySQL credentials
    private static final String URL = "jdbc:mysql://localhost:3306/bdms?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";          // your MySQL username
    private static final String PASSWORD = "Nisha@2005"; // your MySQL password

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver
            return DriverManager.getConnection(URL, USER, PASSWORD); // Connect and return connection
        } catch (Exception e) {
            e.printStackTrace(); // Print error if connection fails
            return null;
        }
    }
}
