package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeFeedback {
    private Connection connection;

    public EmployeeFeedback() {
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        this.connection = dbInstance.getConnection();
    }

    public String addFeedback(Feedback feedback) throws Exception {
        String status = null;

        String sqlQuery = "INSERT INTO UserFeedbackOnFoodItem (userUserId, itemID, feedbackGivenDate, qualityRating, tasteRating, freshnessRating, valueForMoneyRating, feedbackMessage, sentiment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement addFeedbackStmt = connection.prepareStatement(sqlQuery)) {
            Menu menu = new Menu();
            int itemId = menu.getItemID(feedback.getItemName());

            addFeedbackStmt.setString(1, feedback.getEmployeeName());
            addFeedbackStmt.setInt(2, itemId);
            addFeedbackStmt.setDate(3, new java.sql.Date(Calendar.getInstance().getTime().getTime())); // Current date
            addFeedbackStmt.setInt(4, feedback.getQualityRating());
            addFeedbackStmt.setInt(5, feedback.getTasteRating());
            addFeedbackStmt.setInt(6, feedback.getFreshnessRating());
            addFeedbackStmt.setInt(7, feedback.getValueForMoneyRating());
            addFeedbackStmt.setString(8, feedback.getFeedbackMessage());
            addFeedbackStmt.setString(9, feedback.getFeedbackMessageSentiment());

            int rowsInserted = addFeedbackStmt.executeUpdate();

            if (rowsInserted > 0) {
                status = "Feedback added successfully.";
            } else {
                throw new Exception("Failed to add feedback.");
            }

        } catch (SQLException e) {
            throw new Exception("Error during adding feedback: " + e.getMessage());
        }

        return status;
    }

    public List<Map<String, Object>> viewFeedback(String itemName) throws Exception {
        List<Map<String, Object>> feedbackList = new ArrayList<>();
        Menu menu = new Menu();
        int itemId = menu.getItemID(itemName);

        String query = "SELECT feedbackID, userUserId, itemID, feedbackGivenDate, qualityRating, tasteRating, " +
                       "freshnessRating, valueForMoneyRating, feedbackMessage, sentiment " +
                       "FROM UserFeedbackOnFoodItem " +
                       "WHERE itemID = ? " +
                       "ORDER BY feedbackGivenDate DESC " +
                       "LIMIT 5";

        try (PreparedStatement viewFeedbackStmt = connection.prepareStatement(query)) {
            viewFeedbackStmt.setInt(1, itemId);
            ResultSet rs = viewFeedbackStmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> feedback = new HashMap<>();

                // You can fetch the user name using DAO or directly from the User table
                // Here I'm assuming it's fetched from another method, adjust as per your DAO structure
                String userName = getUserName(rs.getString("userUserId"));

                feedback.put("feedbackID", rs.getInt("feedbackID"));
                feedback.put("userName", userName);
                feedback.put("itemID", rs.getInt("itemID"));
                feedback.put("feedbackGivenDate", rs.getDate("feedbackGivenDate"));
                feedback.put("qualityRating", rs.getInt("qualityRating"));
                feedback.put("tasteRating", rs.getInt("tasteRating"));
                feedback.put("freshnessRating", rs.getInt("freshnessRating"));
                feedback.put("valueForMoneyRating", rs.getInt("valueForMoneyRating"));
                feedback.put("feedbackMessage", rs.getString("feedbackMessage"));
                feedback.put("sentiment", rs.getString("sentiment"));

                feedbackList.add(feedback);
            }
        } catch (SQLException e) {
            throw new Exception("Failed to view feedback.\n" + e.getMessage());
        }

        return feedbackList;
    }

    private String getUserName(String userId) throws Exception {
        // Implement your logic to fetch user name from User table based on userId
        // Example placeholder
      UserDAO userDAO = new UserDAO();
        return userDAO.getUserName(userId);
      
    }
}
