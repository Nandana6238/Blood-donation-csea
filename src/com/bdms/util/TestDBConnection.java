package com.bdms.util;

import java.sql.Connection;

public class TestDBConnection {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("Database connected successfully!");
        } else {
            System.out.println("Failed to connect to database.");
        }
    }
}
//javac com/bdms/utilDBConnection.java com/bdms/util/TestDBConnection.java
//java com.bdms.util.TestDBConnection


