package server.databaseoperation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import server.DatabaseConnection;
import server.model.Feedback;

public class EmployeeFeedbackDatabaseOperator {
    private Connection connection;

    public EmployeeFeedbackDatabaseOperator() {
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        this.connection = dbInstance.getConnection();
    }

    public String addFeedback(Feedback feedback) throws SQLException, Exception {
        UserDatabaseOperator user = new UserDatabaseOperator();
        if (!user.isUserExists(feedback.getEmployeeId())) {
            throw new Exception("User ID does not exist: " + feedback.getEmployeeId());
        }

        MenuDatabaseOperator menu = new MenuDatabaseOperator();
        int itemId = menu.getItemID(feedback.getItemName());

        String sqlQuery = "INSERT INTO UserFeedbackOnFoodItem (userUserID, itemID, feedbackGivenDate, " +
                           "qualityRating, tasteRating, freshnessRating, valueForMoneyRating, feedbackMessage, sentiment) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement addFeedbackStmt = connection.prepareStatement(sqlQuery)) {
            addFeedbackStmt.setString(1, feedback.getEmployeeId());
            addFeedbackStmt.setInt(2, itemId);
            addFeedbackStmt.setDate(3, new Date(Calendar.getInstance().getTime().getTime()));
            addFeedbackStmt.setInt(4, feedback.getQualityRating());
            addFeedbackStmt.setInt(5, feedback.getTasteRating());
            addFeedbackStmt.setInt(6, feedback.getFreshnessRating());
            addFeedbackStmt.setInt(7, feedback.getValueForMoneyRating());
            addFeedbackStmt.setString(8, feedback.getFeedbackMessage());
            addFeedbackStmt.setString(9, feedback.getFeedbackMessageSentiment());

            int rowsInserted = addFeedbackStmt.executeUpdate();

            if (rowsInserted > 0) {
                return "Feedback added successfully.";
            } else {
                throw new Exception("Failed to add feedback.");
            }
        } catch (SQLException e) {
            throw new SQLException("Error during adding feedback: " + e.getMessage(), e);
        }
    }

    public List<Map<String, Object>> viewFeedback(String itemName) throws SQLException, Exception {
        List<Map<String, Object>> feedbackList = new ArrayList<>();
        MenuDatabaseOperator menu = new MenuDatabaseOperator();
        
        int itemId;
        try {
            itemId = menu.getItemID(itemName);
        } catch (Exception e) {
            throw new Exception("Failed to retrieve item ID: " + e.getMessage(), e);
        }

        String query = "SELECT feedbackID, userUserID, itemID, feedbackGivenDate, qualityRating, tasteRating, " +
                       "freshnessRating, valueForMoneyRating, feedbackMessage, sentiment " +
                       "FROM UserFeedbackOnFoodItem " +
                       "WHERE itemID = ? " +
                       "ORDER BY feedbackGivenDate DESC " +
                       "LIMIT 5";

        try (PreparedStatement viewFeedbackStmt = connection.prepareStatement(query)) {
            viewFeedbackStmt.setInt(1, itemId);

            try (ResultSet rs = viewFeedbackStmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> feedback = new LinkedHashMap<>();
                    String userName = getUserName(rs.getString("userUserID"));
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
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to view feedback: " + e.getMessage(), e);
        }

        return feedbackList;
    }

    private String getUserName(String userId) throws SQLException, Exception {
        UserDatabaseOperator userDAO = new UserDatabaseOperator();
        return userDAO.getUserName(userId);
    }
}
