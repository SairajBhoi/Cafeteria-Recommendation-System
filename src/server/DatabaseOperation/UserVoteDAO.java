package server.DatabaseOperation;

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
import server.util.JsonConverter;
import server.util.JsonStringToObject;

public class UserVoteDAO {

    private Connection connection;

    public UserVoteDAO() {
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        this.connection = dbInstance.getConnection();
    }

    public String getChefRolloutListForCurrentDateAsJson() {
        JSONArray jsonArray = new JSONArray();

        try {
            // Get current date formatted as 'yyyy-MM-dd'
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
                String nameOfFood = resultSet.getString("nameOfFood");
                String categoryName = resultSet.getString("categoryName");
                int categoryID = resultSet.getInt("categoryID");

                // Create a JSON object for each row
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("rolloutID", rolloutID);
                jsonObject.put("nameOfFood", nameOfFood);
                jsonObject.put("categoryName", categoryName);
                jsonObject.put("categoryID", categoryID);
                // Add the JSON object to the array
                jsonArray.add(jsonObject);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException appropriately (logging, rethrowing, etc.)
        }

        // Convert jsonArray directly to JSON string using JsonConverter
        if (JsonConverter.convertObjectToJson(jsonArray).isEmpty()) {
        	
        	return JsonConverter.convertStatusAndMessageToJson("info","no rollout yet");}
        else {
        	return JsonConverter.convertObjectToJson(jsonArray).toString();}
    }
    
    
    
    public String processVote(Vote vote) {
        try {
        	if(! hasUserVoted( vote.getUserID(), vote.getRolloutID())) {
            String sql = "INSERT INTO UserVote (userID, rolloutID, voteDecision, voteDate) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, vote.getUserID());
            pstmt.setInt(2, vote.getRolloutID());
            pstmt.setBoolean(3, vote.isVoteDecision());
            pstmt.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
            pstmt.executeUpdate();

            String updateSql = "UPDATE ChefMenuRollout SET numberOfVotes = numberOfVotes + 1 WHERE rolloutID = ?";
            PreparedStatement updatePstmt = connection.prepareStatement(updateSql);
            updatePstmt.setInt(1, vote.getRolloutID());
            updatePstmt.executeUpdate();
        } 
        else {
        	return JsonConverter.convertStatusAndMessageToJson("error", "already given feedback");
        }}catch (SQLException e) {
        	return JsonConverter.convertStatusAndMessageToJson("error", e.getMessage());
        }
		return JsonConverter.convertStatusAndMessageToJson("success", "voted successfully");
        
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
    
    public  String addVote(String data){
    	Vote vote =JsonStringToObject.fromJsonToObject(data, Vote.class);
    	
    	 String status =processVote(vote);
    	return status;
    	
    }

}
