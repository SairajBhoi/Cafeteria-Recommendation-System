package server.DatabaseOperation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import server.DatabaseConnection;
import server.model.ChefMenuRollout;

public class ChefMenuRolloutDatabaseOperator {

    private Connection connection;

    public ChefMenuRolloutDatabaseOperator() {
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        this.connection = dbInstance.getConnection();
    }

    public boolean insertChefMenuRollout(ChefMenuRollout rollout) throws SQLException {
        MenuDatabaseOperator menu = new MenuDatabaseOperator();
        try {
            int itemId = menu.getItemID(rollout.getItemName());
            int categoryId = menu.getCategoryID(rollout.getCategoryName());

            rollout.setItemID(itemId);
            rollout.setCategoryID(categoryId);
        } catch (Exception e) {
            
            e.printStackTrace();
            return false;
        }

        String query = "INSERT INTO ChefMenuRollout (rolloutDate, itemID, categoryID, numberOfVotes) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, rollout.getRolloutDate());
            stmt.setInt(2, rollout.getItemID());
            stmt.setInt(3, rollout.getCategoryID());
            stmt.setInt(4, rollout.getNumberOfVotes());

            int affectedRows = stmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting ChefMenuRollout: " + e.getMessage());
            throw e; 
        }
    }

    public List<ChefMenuRollout> getAllChefMenuRollouts() throws SQLException {
        String query = "SELECT * FROM ChefMenuRollout";
        List<ChefMenuRollout> rollouts = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                ChefMenuRollout rollout = new ChefMenuRollout();
                rollout.setRolloutID(rs.getInt("rolloutID"));
                rollout.setRolloutDate(rs.getDate("rolloutDate"));
                rollout.setItemID(rs.getInt("itemID"));
                rollout.setCategoryID(rs.getInt("categoryID"));
                rollout.setNumberOfVotes(rs.getInt("numberOfVotes"));
                rollouts.add(rollout);
            }
        }
        return rollouts;
    }

    public List<ChefMenuRollout> getTodayRollouts() throws SQLException {
        String query = "SELECT * FROM ChefMenuRollout WHERE DATE(rolloutDate) = CURDATE()";
        MenuDatabaseOperator menuDatabaseOperator = new MenuDatabaseOperator();
        List<ChefMenuRollout> rollouts = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                ChefMenuRollout rollout = new ChefMenuRollout();
                rollout.setRolloutID(rs.getInt("rolloutID"));
                rollout.setRolloutDate(rs.getDate("rolloutDate"));
                rollout.setItemID(rs.getInt("itemID"));
                rollout.setCategoryID(rs.getInt("categoryID"));
                rollout.setNumberOfVotes(rs.getInt("numberOfVotes"));
                try {
                    rollout.setCategoryName(menuDatabaseOperator.getCategoryName(rollout.getCategoryID()));
                    rollout.setItemName(menuDatabaseOperator.getItemName(rollout.getItemID()));
                } catch (Exception e) {
                    // Handle specific exceptions or rethrow as appropriate
                    e.printStackTrace();
                }
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
        } catch (SQLException e) {
            System.err.println("Error updating vote count: " + e.getMessage());
            throw e; 
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
                    try (ResultSet rs = countStmt.executeQuery()) {
                        if (rs.next()) {
                            int voteCount = rs.getInt("voteCount");
                            return updateVoteCount(rolloutID, voteCount);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error casting vote: " + e.getMessage());
            throw e; 
        }
        return false;
    }
}