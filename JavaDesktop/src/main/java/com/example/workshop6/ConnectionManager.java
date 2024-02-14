package com.example.workshop6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static String url = "jdbc:mysql://localhost:3306/travelexperts";
    //private static String driverName = "com.mysql.cj.jdbc.Driver";
    private static String username = "Fred";
    private static String password = "password";
    private static Connection con;
    private static String urlstring;

    public static Connection getConnection() {
        //try {
            //Class.forName(driverName);
            try {
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                System.out.println("Failed to create the database connection.");
            }
       // } catch (ClassNotFoundException ex) {
           // System.out.println("Driver not found.");
        //}
        return con;
    }
}
