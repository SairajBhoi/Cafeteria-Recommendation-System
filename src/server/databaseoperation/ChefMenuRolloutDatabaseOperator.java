package server.databaseoperation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import server.DatabaseConnection;
import server.model.ChefMenuItemScore;
import server.model.ChefMenuRollout;

public class ChefMenuRolloutDatabaseOperator {

    private Connection connection;
    private MenuDatabaseOperator menuDatabaseOperator;

    public ChefMenuRolloutDatabaseOperator() {
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        this.connection = dbInstance.getConnection();
        this.menuDatabaseOperator = new MenuDatabaseOperator(); 
    }

    public boolean insertChefMenuRollout(ChefMenuRollout rollout) throws SQLException {
        try {
            int itemId = menuDatabaseOperator.getItemID(rollout.getItemName());
            int categoryId = menuDatabaseOperator.getCategoryID(rollout.getCategoryName());

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
                    e.printStackTrace();
                }
                rollouts.add(rollout);
            }
        }
        return rollouts;
    }

    public List<ChefMenuItemScore> getChefMenuItemsByRolloutDate(Date rolloutDate) throws SQLException {
        List<ChefMenuItemScore> menuItems = new ArrayList<>();
        String query = "SELECT r.rolloutID, m.nameOfFood, m.foodPrice, m.foodAvailable, " +
                       "m.CuisineType, m.FoodType, m.SpiceLevel, m.IsSweet, c.categoryName " +
                       "FROM ChefMenuRollout r " +
                       "JOIN FoodMenuItem m ON r.itemID = m.itemID " +
                       "JOIN Category c ON r.categoryID = c.categoryID " +
                       "WHERE r.rolloutDate = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, rolloutDate);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChefMenuItemScore menuItem = new ChefMenuItemScore();
                    menuItem.setRolloutID(rs.getInt("rolloutID"));
                    menuItem.setItemName(rs.getString("nameOfFood"));
                    menuItem.setItemPrice(rs.getFloat("foodPrice"));
                    menuItem.setItemAvailable(rs.getBoolean("foodAvailable"));
                    menuItem.setCuisineType(rs.getString("CuisineType"));
                    menuItem.setFoodType(rs.getString("FoodType"));
                    menuItem.setSpiceLevel(rs.getString("SpiceLevel"));
                    menuItem.setSweet(rs.getBoolean("IsSweet"));
                    menuItem.setItemCategory(rs.getString("categoryName"));
                    menuItems.add(menuItem);
                }
            }
        }
        return menuItems;
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
        String insertQuery = "INSERT INTO UserVote (userID, rolloutID, voteDecision, voteDate) VALUES (?, ?, ?, CURDATE())";
        String countQuery = "SELECT COUNT(*) AS voteCount FROM UserVote WHERE rolloutID = ?";

        try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
            insertStmt.setString(1, userID);
            insertStmt.setInt(2, rolloutID);
            insertStmt.setString(3, voteDecision);
            boolean inserted = insertStmt.executeUpdate() > 0;

            if (inserted) {
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
