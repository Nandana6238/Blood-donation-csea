
package com.bdms.util;

public class TestDBConnection {
    public static void main(String[] args) {
        try {
            var conn = DBConnection.getConnection();
            System.out.println("✅ Connected to MySQL successfully!");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
