package server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChefMenuRolloutDAO {

    private Connection connection;

    public ChefMenuRolloutDAO(Connection connection) {
    	  DatabaseConnection dbInstance = DatabaseConnection.getInstance();
          this.connection = dbInstance.getConnection();
    }

   
    public boolean insertChefMenuRollout(ChefMenuRollout rollout) throws SQLException {
    	Menu menu= new Menu();
    	try {
			menu.getItemID(rollout.getItemName());
		} catch (Exception e) {
			e.printStackTrace();
		}
        String query = "INSERT INTO ChefMenuRollout (rolloutDate, itemID, categoryID, votingEndTime, numberOfVotes) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, rollout.getRolloutDate());
            stmt.setInt(2, rollout.getItemID());
            stmt.setInt(3, rollout.getCategoryID());
            stmt.setTimestamp(4, rollout.getVotingEndTime());
            stmt.setInt(5, rollout.getNumberOfVotes());
            return stmt.executeUpdate() > 0;
        }
    }

   
    public List<ChefMenuRollout> getAllChefMenuRollouts() throws SQLException {
        String query = "SELECT * FROM ChefMenuRollout";
        List<ChefMenuRollout> rollouts = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ChefMenuRollout rollout = new ChefMenuRollout();
                rollout.setRolloutID(rs.getInt("rolloutID"));
                rollout.setRolloutDate(rs.getDate("rolloutDate"));
                rollout.setItemID(rs.getInt("itemID"));
                rollout.setCategoryID(rs.getInt("categoryID"));
                rollout.setVotingEndTime(rs.getTimestamp("votingEndTime"));
                rollout.setNumberOfVotes(rs.getInt("numberOfVotes"));
                rollouts.add(rollout);
            }
        }
        return rollouts;
    }

   
    public List<ChefMenuRollout> getTodayRollouts() throws SQLException {
        String query = "SELECT * FROM ChefMenuRollout WHERE DATE(rolloutDate) = CURDATE()";
        List<ChefMenuRollout> rollouts = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ChefMenuRollout rollout = new ChefMenuRollout();
                rollout.setRolloutID(rs.getInt("rolloutID"));
                rollout.setRolloutDate(rs.getDate("rolloutDate"));
                rollout.setItemID(rs.getInt("itemID"));
                rollout.setCategoryID(rs.getInt("categoryID"));
                rollout.setVotingEndTime(rs.getTimestamp("votingEndTime"));
                rollout.setNumberOfVotes(rs.getInt("numberOfVotes"));
                rollouts.add(rollout);
            }
        }
        return rollouts;
    }

    
    public boolean updateVoteCount(int rolloutID, int newVoteCount) throws SQLException {
        String query = "UPDATE ChefMenuRollout SET numberOfVotes = ? WHERE rolloutID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, newVoteCount);
            stmt.setInt(2, rolloutID);
            return stmt.executeUpdate() > 0;
        }
    }

   
    public boolean castVote(String userID, int rolloutID, String voteDecision) throws SQLException {
        String query = "INSERT INTO UserVote (userID, rolloutID, voteDecision, voteDate) VALUES (?, ?, ?, CURDATE())";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userID);
            stmt.setInt(2, rolloutID);
            stmt.setString(3, voteDecision);
            boolean inserted = stmt.executeUpdate() > 0;

            if (inserted) {
                String countQuery = "SELECT COUNT(*) AS voteCount FROM UserVote WHERE rolloutID = ?";
                try (PreparedStatement countStmt = connection.prepareStatement(countQuery)) {
                    countStmt.setInt(1, rolloutID);
                    ResultSet rs = countStmt.executeQuery();
                    if (rs.next()) {
                        int voteCount = rs.getInt("voteCount");
                        return updateVoteCount(rolloutID, voteCount);
                    }
                }
            }
        }
        return false;
    }
}
