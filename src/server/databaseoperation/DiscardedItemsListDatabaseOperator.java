package server.databaseoperation;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import server.DatabaseConnection;
import server.model.DiscardedFoodItem;

public class DiscardedItemsListDatabaseOperator {

    private Connection connection;

    public DiscardedItemsListDatabaseOperator() {
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        this.connection = dbInstance.getConnection();
        System.out.print( "connection ============ "+ this.connection);
    }

    public List<DiscardedFoodItem> retrieveDiscardedItems(int month, int year) {
        List<DiscardedFoodItem> discardedItems = new ArrayList<>();
        String sqlQuery = "SELECT discardID, itemName, averageRating, positivePercentage, negativePercentage, discardedDate " +
                          "FROM DiscardedFoodItem " +
                          "WHERE MONTH(discardedDate) = ? AND YEAR(discardedDate) = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {
            pstmt.setInt(1, month);
            pstmt.setInt(2, year);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int discardID = rs.getInt("discardID");
                String itemName = rs.getString("itemName");
                BigDecimal averageRating = rs.getBigDecimal("averageRating");
                BigDecimal positivePercentage = rs.getBigDecimal("positivePercentage");
                BigDecimal negativePercentage = rs.getBigDecimal("negativePercentage");
                Date discardedDate = rs.getDate("discardedDate");

                DiscardedFoodItem discardedItem = new DiscardedFoodItem();
                discardedItem.setDiscardID(discardID);
                discardedItem.setItemName(itemName);
                discardedItem.setAverageRating(averageRating);
                discardedItem.setPositivePercentage(positivePercentage);
                discardedItem.setNegativePercentage(negativePercentage);
                discardedItem.setDiscardedDate(discardedDate);
                discardedItems.add(discardedItem);
                
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return discardedItems;
    }
    
    
    
    
    
    public List<DiscardedFoodItem> getAllDiscardedItems() {
        List<DiscardedFoodItem> discardedItems = new ArrayList<>();
        String sqlQuery = "SELECT " +
                          "di.discardID, " +
                          "di.itemID, " + // Added itemID here
                          "di.itemName, " +
                          "di.averageRating, " +
                          "di.positivePercentage, " +
                          "di.negativePercentage, " +
                          "di.discardedDate " +
                          "FROM DiscardedFoodItem di";

        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int discardID = rs.getInt("discardID");
                int itemID = rs.getInt("itemID"); // Retrieve itemID
                String itemName = rs.getString("itemName");
                BigDecimal averageRating = rs.getBigDecimal("averageRating");
                BigDecimal positivePercentage = rs.getBigDecimal("positivePercentage");
                BigDecimal negativePercentage = rs.getBigDecimal("negativePercentage");
                Date discardedDate = rs.getDate("discardedDate");

                DiscardedFoodItem discardedItem = new DiscardedFoodItem();
                discardedItem.setDiscardID(discardID);
                discardedItem.setItemID(itemID);
                discardedItem.setItemName(itemName);
                discardedItem.setAverageRating(averageRating);
                discardedItem.setPositivePercentage(positivePercentage);
                discardedItem.setNegativePercentage(negativePercentage);
                discardedItem.setDiscardedDate(discardedDate);

                discardedItems.add(discardedItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return discardedItems;
    }
    
    
    public List<DiscardedFoodItem> getAllChefDiscardedItems() {
        List<DiscardedFoodItem> discardedItems = new ArrayList<>();
        String sqlQuery = "SELECT " +
                          "cdl.discardID, " +
                          "fmi.nameOfFood AS itemName, " +
                          "di.averageRating, " +
                          "di.positivePercentage, " +
                          "di.negativePercentage, " +
                          "di.discardedDate " +
                          "FROM ChefDiscardList cdl " +
                          "JOIN DiscardedFoodItem di ON cdl.itemID = di.itemID " +
                          "JOIN FoodMenuItem fmi ON di.itemID = fmi.itemID";

        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int discardID = rs.getInt("discardID");
                String itemName = rs.getString("itemName");
                BigDecimal averageRating = rs.getBigDecimal("averageRating");
                BigDecimal positivePercentage = rs.getBigDecimal("positivePercentage");
                BigDecimal negativePercentage = rs.getBigDecimal("negativePercentage");
                Date discardedDate = rs.getDate("discardedDate");
                DiscardedFoodItem discardedItem = new DiscardedFoodItem();
                discardedItem.setDiscardID(discardID);
                discardedItem.setItemName(itemName);
                discardedItem.setAverageRating(averageRating);
                discardedItem.setPositivePercentage(positivePercentage);
                discardedItem.setNegativePercentage(negativePercentage);
                discardedItem.setDiscardedDate(discardedDate);
                discardedItems.add(discardedItem);
            }
        } catch (SQLException e) {
            
            e.printStackTrace();
        }

        return discardedItems;
    }
    
    
    
    
    
    
    
    
    public void executeDiscardedFoodItemsUpdate() throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;

        // SQL commands
        String checkEmptySql = "SELECT COUNT(*) FROM DiscardedFoodItem;";
        //String deleteSql = "DELETE FROM DiscardedFoodItem;";
        String insertSql = "INSERT INTO DiscardedFoodItem (itemID, itemName, averageRating, positivePercentage, negativePercentage, discardedDate)\n" +
                           "SELECT\n" +
                           "    fmi.itemID,\n" +
                           "    fmi.nameOfFood AS itemName,\n" +
                           "    AVG((uf.qualityRating + uf.tasteRating + uf.freshnessRating + uf.valueForMoneyRating) / 4) AS averageRating,\n" +
                           "    SUM(CASE WHEN uf.sentiment = 'Positive' THEN 1 ELSE 0 END) / COUNT(uf.feedbackID) * 100 AS positivePercentage,\n" +
                           "    SUM(CASE WHEN uf.sentiment = 'Negative' THEN 1 ELSE 0 END) / COUNT(uf.feedbackID) * 100 AS negativePercentage,\n" +
                           "    CURDATE() AS discardedDate\n" +
                           "FROM\n" +
                           "    FoodMenuItem fmi\n" +
                           "JOIN\n" +
                           "    UserFeedbackOnFoodItem uf ON fmi.itemID = uf.itemID\n" +
                           "WHERE\n" +
                           "    uf.feedbackGivenDate >= CURDATE() - INTERVAL 30 DAY\n" +
                           "GROUP BY\n" +
                           "    fmi.itemID, fmi.nameOfFood\n" +
                           "HAVING\n" +
                           "    AVG((uf.qualityRating + uf.tasteRating + uf.freshnessRating + uf.valueForMoneyRating) / 4) < 2\n" +
                           "    AND COUNT(uf.feedbackID) >= 1\n" +
                           "    AND SUM(CASE WHEN uf.sentiment = 'Negative' THEN 1 ELSE 0 END) / COUNT(uf.feedbackID) * 100 > 50;";

        try {
            statement = connection.createStatement();
            
            // Check if DiscardedFoodItem is empty
            resultSet = statement.executeQuery(checkEmptySql);
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count == 0) {
                // Table is empty, perform delete and insert operations
                //statement.executeUpdate(deleteSql);
               // System.out.println("Existing records deleted from DiscardedFoodItem.");

                statement.executeUpdate(insertSql);
                System.out.println("New records inserted into DiscardedFoodItem.");
            } else {
                System.out.println("DiscardedFoodItem table is not empty. No update performed.");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error executing update on DiscardedFoodItem: " + e.getMessage(), e);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }

    
    
    
    
    
    
    
    
    

    
    
    
    public String insertDiscardedItemsIntoChefDiscardList(int discardID) {
        String insertQuery = "INSERT INTO ChefDiscardList (discardID, itemID, discardMonthYear) " +
                             "SELECT di.discardID, di.itemID, di.discardedDate " +
                             "FROM DiscardedFoodItem di " +
                             "WHERE di.discardID = ?";

        try {
            // Check if discardID exists in ChefDiscardList
            if (isDiscardIDPresent(discardID)) {
                return "Discard food name already exists in ChefDiscardList.";
            }

            // Proceed with the insertion
            try (PreparedStatement insertPstmt = connection.prepareStatement(insertQuery)) {
                insertPstmt.setInt(1, discardID);
                int rowsAffected = insertPstmt.executeUpdate();

                if (rowsAffected > 0) {
                    return "Success: Inserted " + rowsAffected + " rows into ChefDiscardList.";
                } else {
                    return "No rows inserted. Check if the discardID exists in DiscardedFoodItem.";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }


    
    
    private boolean isDiscardIDPresent(int discardID) throws SQLException {
        String checkExistsQuery = "SELECT COUNT(*) FROM ChefDiscardList WHERE discardID = ?";

        try (PreparedStatement checkPstmt = connection.prepareStatement(checkExistsQuery)) {
            checkPstmt.setInt(1, discardID);
            try (ResultSet rs = checkPstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    
    
    public void deleteDiscardedItems(int discardID) {
        String deleteFromChefDiscardList = "DELETE FROM ChefDiscardList WHERE discardID = ?";
        String deleteFromDiscardedFoodItem = "DELETE FROM DiscardedFoodItem WHERE discardID = ?";
        String deleteFromFoodMenuItem = "DELETE FROM FoodMenuItem WHERE itemID NOT IN (SELECT itemID FROM DiscardedFoodItem)";

        try (PreparedStatement pstmt1 = connection.prepareStatement(deleteFromChefDiscardList);
             PreparedStatement pstmt2 = connection.prepareStatement(deleteFromDiscardedFoodItem);
             PreparedStatement pstmt3 = connection.prepareStatement(deleteFromFoodMenuItem)) {

            pstmt1.setInt(1, discardID);
            pstmt1.executeUpdate();

            pstmt2.setInt(1, discardID);
            pstmt2.executeUpdate();


            pstmt3.executeUpdate();

            System.out.println("Records deleted from ChefDiscardList, DiscardedFoodItem, and FoodMenuItem.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public String deleteDiscardedItemFromMenu(int discardID) throws Exception {
        String deleteFeedbackQuery = "DELETE FROM UserDiscardedFeedback WHERE discardID = ?";
        String deleteChefDiscardList = "DELETE FROM ChefDiscardList WHERE discardID = ?";
        String deleteFromDiscardedFoodItem = "DELETE FROM DiscardedFoodItem WHERE discardID = ?";
        String deleteFromFoodItemCategory = "DELETE FROM FoodItemCategory WHERE itemID IN (SELECT itemID FROM DiscardedFoodItem WHERE discardID = ?)";
        String deleteFromFoodMenuItem = "DELETE FROM FoodMenuItem WHERE itemID NOT IN (SELECT itemID FROM DiscardedFoodItem)";

        try {
            // Start transaction
            connection.setAutoCommit(false);

            // Delete from UserDiscardedFeedback
            try (PreparedStatement pstmt = connection.prepareStatement(deleteFeedbackQuery)) {
                pstmt.setInt(1, discardID);
                pstmt.executeUpdate();
            }

            // Delete from ChefDiscardList
            try (PreparedStatement pstmt = connection.prepareStatement(deleteChefDiscardList)) {
                pstmt.setInt(1, discardID);
                pstmt.executeUpdate();
            }

            // Delete from FoodItemCategory
            try (PreparedStatement pstmt = connection.prepareStatement(deleteFromFoodItemCategory)) {
                pstmt.setInt(1, discardID);
                pstmt.executeUpdate();
            }

            // Delete from DiscardedFoodItem
            try (PreparedStatement pstmt = connection.prepareStatement(deleteFromDiscardedFoodItem)) {
                pstmt.setInt(1, discardID);
                pstmt.executeUpdate();
            }

            // Optionally, delete from FoodMenuItem if it is no longer referenced
            try (PreparedStatement pstmt = connection.prepareStatement(deleteFromFoodMenuItem)) {
                pstmt.executeUpdate();
            }

            // Commit transaction
            connection.commit();
            return "Records deleted from UserDiscardedFeedback, ChefDiscardList, FoodItemCategory, DiscardedFoodItem, and FoodMenuItem.";
        } catch (SQLException e) {
            // Rollback transaction if there is an error
            try {
                connection.rollback();
                throw new Exception("Error occurred while deleting records. Transaction rolled back.", e);
            } catch (SQLException rollbackEx) {
                throw new Exception("Error occurred while deleting records, and rollback failed.", rollbackEx);
            }
        } finally {
            // Restore auto-commit mode
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace(); // Log the error for debugging purposes
            }
        }
    }


    
}
