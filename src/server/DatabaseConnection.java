package server;

import java.sql.*;

public class DatabaseConnection {

//    private static final String DB_URL = System.getenv("DB_URL");   
//    private static final String USERNAME = System.getenv("DB_USERNAME");
//    private static final String PASSWORD = System.getenv("DB_PASSWORD");
//    private static final String DATABASE_NAME = System.getenv("DB_NAME");
    
    private static final String DB_URL = "jdbc:mysql://database-1.cx24yqcse9i2.ap-south-1.rds.amazonaws.com:3306/";//System.getenv("DB_URL");
    private static final String USERNAME = "admin";//System.getenv("DB_USERNAME");
    private static final String PASSWORD = "SairajBhoiITT"; //System.getenv("DB_PASSWORD");
    private static final String DATABASE_NAME = "cafeteria";//System.getenv("DB_NAME");


   
    private static DatabaseConnection instance;
    private Connection connection;

   
    private DatabaseConnection() {
        try {
            this.connection = DriverManager.getConnection(DB_URL + DATABASE_NAME, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  
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

   
    public Connection getConnection() {
        return connection;
    }

   
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



