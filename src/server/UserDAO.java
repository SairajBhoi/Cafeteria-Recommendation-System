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

    public User authenticateUser(String userID, String password) throws Exception {
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
                    throw new Exception("Multiple users found with the same userID: " + userID);
                }
            } else {
                throw new Exception("Wrong user ID or password.");
            }
        } catch (SQLException e) {
            throw new Exception("Error during authentication: " + e.getMessage());
        }

        return user;
    }

    public String getUserName(String userId) throws Exception {
        String query = "SELECT username FROM User WHERE userID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String userName = rs.getString("username");

                if (rs.next()) {
                    throw new Exception("Multiple users found with the same userID: " + userId);
                }

                return userName;
            } else {
                throw new Exception("User not found for userID: " + userId);
            }
        } catch (SQLException e) {
            throw new Exception("Database error while fetching username: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
}
