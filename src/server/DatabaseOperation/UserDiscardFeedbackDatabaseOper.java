package server.DatabaseOperation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.DatabaseConnection;
import server.model.UserDiscardedFeedback;


public class UserDiscardFeedbackDatabaseOper {

    private Connection connection;

    public UserDiscardFeedbackDatabaseOper() {
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        this.connection = dbInstance.getConnection();
    }

    // Method to retrieve all feedback for a given discardID
    public List<UserDiscardedFeedback> viewAllFeedbackForDiscardID(int discardID) throws SQLException {
        String sql = "SELECT userID, discardID, question1Answer, question2Answer, question3Answer, feedbackDate "
                   + "FROM UserDiscardedFeedback "
                   + "WHERE discardID = ?";

        List<UserDiscardedFeedback> feedbackList = new ArrayList<>();
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, discardID);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    UserDiscardedFeedback feedback = new UserDiscardedFeedback();
                    feedback.setUserID(rs.getString("userID"));
                    feedback.setDiscardID(rs.getInt("discardID"));
                    feedback.setQuestion1Answer(rs.getString("question1Answer"));
                    feedback.setQuestion2Answer(rs.getString("question2Answer"));
                    feedback.setQuestion3Answer(rs.getString("question3Answer"));
                    feedback.setFeedbackDate(rs.getDate("feedbackDate"));
                    
                    feedbackList.add(feedback);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving feedback from UserDiscardedFeedback: " + e.getMessage(), e);
        }

        return feedbackList;
    }

    // Method to check if feedback already exists
    private boolean feedbackExists(String userID, int discardID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM UserDiscardedFeedback WHERE userID = ? AND discardID = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            pstmt.setInt(2, discardID);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Returns true if feedback exists
                }
            }
        }
        return false; // Returns false if no feedback found
    }

    public String insertUserDiscardedFeedback(UserDiscardedFeedback userFeedback) throws SQLException {
        String userID = userFeedback.getUserID();
        int discardID = userFeedback.getDiscardID();
        String question1Answer = userFeedback.getQuestion1Answer();
        String question2Answer = userFeedback.getQuestion2Answer();
        String question3Answer = userFeedback.getQuestion3Answer();
        Date feedbackDate = new Date(System.currentTimeMillis()); 

        // Check if feedback already exists
        if (feedbackExists(userID, discardID)) {
            return "Feedback for userID " + userID + " and discardID " + discardID + " already exists.";
        }

        String sql = "INSERT INTO UserDiscardedFeedback (userID, discardID, question1Answer, question2Answer, question3Answer, feedbackDate) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            pstmt.setInt(2, discardID);
            pstmt.setString(3, question1Answer);
            pstmt.setString(4, question2Answer);
            pstmt.setString(5, question3Answer);
            pstmt.setDate(6, feedbackDate); 

            int rowsAffected = pstmt.executeUpdate();
            return "Inserted " + rowsAffected + " row(s) into UserDiscardedFeedback.";
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error inserting feedback into UserDiscardedFeedback: " + e.getMessage(), e);
        }
    }

}
