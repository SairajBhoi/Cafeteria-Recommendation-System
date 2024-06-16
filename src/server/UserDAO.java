package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private Connection connection;

    public UserDAO() {
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        this.connection = dbInstance.getConnection();
    }

    public String authenticateUser(String userID, String password) throws Exception {
        User user = null;
        String query = "SELECT * FROM User WHERE userID = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userID);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getString("userID"));
                user.setUserName(rs.getString("username"));
                user.setUserRole(rs.getString("role"));
                
                if (rs.next()) {
                    System.err.println("Warning: Multiple users found for userID: " + userID);
                    throw new Exception("Multiple users found with the same userID.");
                }
            } else {
                throw new Exception("Wrong user ID or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error during authentication: " + e.getMessage());
        }

        return JsonConverter.convertObjectToJson(user);
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
