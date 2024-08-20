package server.databaseoperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import server.DatabaseConnection;
import server.model.Vote;

public class UserVoteDatabaseOperator {

    private Connection connection;

    public UserVoteDatabaseOperator() {
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        this.connection = dbInstance.getConnection();
    }

    public JSONArray getChefRolloutListForCurrentDate() {
        JSONArray jsonArray = new JSONArray();

        try {
            LocalDate currentDate = LocalDate.now();
            String formattedCurrentDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            String sql = "SELECT r.rolloutID, f.nameOfFood, c.categoryName, r.categoryID " +
                         "FROM ChefMenuRollout r " +
                         "JOIN FoodMenuItem f ON r.itemID = f.itemID " +
                         "JOIN Category c ON r.categoryID = c.categoryID " +
                         "WHERE r.rolloutDate = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, formattedCurrentDate);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                int rolloutID = resultSet.getInt("rolloutID");
                String itemName = resultSet.getString("nameOfFood");
                String categoryName = resultSet.getString("categoryName");
                int categoryID = resultSet.getInt("categoryID");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("rolloutID", rolloutID);
                jsonObject.put("itemName", itemName);
                jsonObject.put("categoryName", categoryName);
                jsonObject.put("categoryID", categoryID);

                jsonArray.add(jsonObject);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public String processVote(Vote vote) {
        try {
            // First, check if the rolloutID exists for the current date
            if (!isValidRolloutID(vote.getRolloutID())) {
                return "error: invalid rolloutID for the given date";
            }

            // Check if the user has already voted
            if (!hasUserVoted(vote.getUserID(), vote.getRolloutID())) {
                // Insert the vote into the UserVote table
                String sql = "INSERT INTO UserVote (userID, rolloutID, voteDecision, voteDate) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, vote.getUserID());
                    pstmt.setInt(2, vote.getRolloutID());
                    pstmt.setInt(3, vote.isVoteDecision() ? 1 : 0);
                    pstmt.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
                    pstmt.executeUpdate();
                }

                // If the vote is positive, update the number of votes in ChefMenuRollout
                if (vote.isVoteDecision()) {
                    String updateSql = "UPDATE ChefMenuRollout SET numberOfVotes = numberOfVotes + 1 WHERE rolloutID = ?";
                    try (PreparedStatement updatePstmt = connection.prepareStatement(updateSql)) {
                        updatePstmt.setInt(1, vote.getRolloutID());
                        updatePstmt.executeUpdate();
                    }
                }
            } else {
                return "error: already given feedback";
            }
        } catch (SQLException e) {
            return "error: " + e.getMessage();
        }
        return "success: voted successfully";
    }

    // Method to check if the rolloutID is valid for the current date
    private boolean isValidRolloutID(int rolloutID) throws SQLException {
        String query = "SELECT COUNT(*) FROM ChefMenuRollout WHERE rolloutID = ? AND rolloutDate = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, rolloutID);
            pstmt.setDate(2, java.sql.Date.valueOf(LocalDate.now())); // Check for today's date
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }


    public boolean hasUserVoted(String userID, int rolloutID) {
        String sql = "SELECT COUNT(*) FROM UserVote WHERE userID = ? AND rolloutID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            pstmt.setInt(2, rolloutID);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
