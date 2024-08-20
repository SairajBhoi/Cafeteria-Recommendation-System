package server.databaseoperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import server.DatabaseConnection;

import server.model.UserPreference;

public class UserProfileDatabaseOperator {
    private Connection connection;
  
    public UserProfileDatabaseOperator() {
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        this.connection = dbInstance.getConnection();
    }

    public UserPreference getUserPreference(String userID) throws Exception {
        String query = "SELECT * FROM UserPreference WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    UserPreference userPreference = new UserPreference();
                    userPreference.setUserID(resultSet.getString("userID"));
                    userPreference.setDietaryPreference(resultSet.getString("dietaryPreference"));
                    userPreference.setPreferredSpiceLevel(resultSet.getString("preferredSpiceLevel"));
                    userPreference.setPreferredCuisine(resultSet.getString("preferredCuisine"));
                    userPreference.setHasSweetTooth(resultSet.getBoolean("hasSweetTooth"));
                    return userPreference;
                } else {
                    throw new Exception("Profile not set");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error retrieving user profile");
        }
    }
    
    
    public String updateOrAddUserPreference(UserPreference userPreference) throws Exception {
        boolean userExists = checkUserExists(userPreference.getUserID());

        if (userExists) {
            return updateUserPreference(userPreference);
        } else {
           {
            return addUserPreference(userPreference);
           
        	}
        }
    }
    
    
    
    private boolean checkUserExists(String userID) throws SQLException {
        String query = "SELECT COUNT(*) FROM UserPreference WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

    private String addUserPreference(UserPreference userPreference) throws Exception {
        String query = "INSERT INTO UserPreference (userID, dietaryPreference, preferredSpiceLevel, preferredCuisine, hasSweetTooth) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userPreference.getUserID());
            statement.setString(2, userPreference.getDietaryPreference());
            statement.setString(3, userPreference.getPreferredSpiceLevel());
            statement.setString(4, userPreference.getPreferredCuisine());
            statement.setBoolean(5, userPreference.isHasSweetTooth());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error adding user profile");
        }
        return "Successfully added the profile";
    }

    private String updateUserPreference(UserPreference userPreference) throws Exception  {
        String query = "UPDATE UserPreference SET dietaryPreference = ?, preferredSpiceLevel = ?, preferredCuisine = ?, hasSweetTooth = ? WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userPreference.getDietaryPreference());
            statement.setString(2, userPreference.getPreferredSpiceLevel());
            statement.setString(3, userPreference.getPreferredCuisine());
            statement.setBoolean(4, userPreference.isHasSweetTooth());
            statement.setString(5, userPreference.getUserID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error updating user profile");
        }
        return "Successfully updated the profile";
    }

    public void deleteUserPreference(String userID) throws Exception {
        String query = "DELETE FROM UserPreference WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error deleting user profile");
        }
    }
}
