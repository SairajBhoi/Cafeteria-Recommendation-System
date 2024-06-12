package server;

import java.sql.*;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:mysql://database-1.cx24yqcse9i2.ap-south-1.rds.amazonaws.com:3306/";
    private static final String USERNAME = "admin";//System.getenv("DB_USERNAME");
    private static final String PASSWORD = "SairajBhoiITT"; //System.getenv("DB_PASSWORD");
    private static final String DATABASE_NAME = "cafeteria";//System.getenv("DB_NAME");

    // Singleton instance
    private static DatabaseConnection instance;
    private Connection connection;

    // Private constructor to prevent instantiation
    private DatabaseConnection() {
        try {
            this.connection = DriverManager.getConnection(DB_URL + DATABASE_NAME, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Public method to provide access to the instance
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    // Method to get the connection
    public Connection getConnection() {
        return connection;
    }

    // Method to close the connection
    public static void closeConnection() {
        if (instance != null && instance.connection != null) {
            try {
                instance.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                instance.connection = null;
                instance = null;
            }
        }
    }
}



package server;

package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/org";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    // Singleton instance
    private static Connection connection = null;

    // Private constructor to prevent instantiation
    private DatabaseConnection() {}

    // Static block for initial connection creation
    static {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace(); // In a real application, use a logging framework
        }
    }

    // Public method to get the connection
    public static synchronized Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In a real application, use a logging framework
        }
        return connection;
    }

    // Method to close the connection
    public static synchronized void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace(); // In a real application, use a logging framework
            } finally {
                connection = null;
            }
        }
    }
}
