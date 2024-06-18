package server;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class RecommendationEngine {

    private Connection connection;

    public RecommendationEngine() {
    	
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    
    public String insertRecommendations() {
        String insertSql = "INSERT INTO Recommendation (itemID, categoryID, avgQualityRating, avgTasteRating, avgFreshnessRating, " +
                           "avgValueForMoneyRating, percentPositive, percentNeutral, percentNegative, totalVotes, date) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String selectSql = "SELECT f.itemID, c.categoryID, " +
                           "       AVG(uf.qualityRating) AS avgQualityRating, " +
                           "       AVG(uf.tasteRating) AS avgTasteRating, " +
                           "       AVG(uf.freshnessRating) AS avgFreshnessRating, " +
                           "       AVG(uf.valueForMoneyRating) AS avgValueForMoneyRating, " +
                           "       SUM(CASE WHEN uf.sentiment = 'positive' THEN 1 ELSE 0 END) / COUNT(*) * 100 AS percentPositive, " +
                           "       SUM(CASE WHEN uf.sentiment = 'neutral' THEN 1 ELSE 0 END) / COUNT(*) * 100 AS percentNeutral, " +
                           "       SUM(CASE WHEN uf.sentiment = 'negative' THEN 1 ELSE 0 END) / COUNT(*) * 100 AS percentNegative, " +
                           "       COUNT(*) AS totalVotes " +
                           "FROM UserFeedbackOnFoodItem uf " +
                           "JOIN FoodItemCategory fic ON uf.itemID = fic.itemID " +
                           "JOIN FoodMenuItem f ON uf.itemID = f.itemID " +
                           "JOIN Category c ON fic.categoryID = c.categoryID " +
                           "GROUP BY f.itemID, c.categoryID";

        try (Statement selectStatement = connection.createStatement();
             PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {

           
            ResultSet resultSet = selectStatement.executeQuery(selectSql);

            // Process the result set and insert into Recommendation table
            while (resultSet.next()) {
                int itemID = resultSet.getInt("itemID");
                int categoryID = resultSet.getInt("categoryID");
                double avgQualityRating = resultSet.getDouble("avgQualityRating");
                double avgTasteRating = resultSet.getDouble("avgTasteRating");
                double avgFreshnessRating = resultSet.getDouble("avgFreshnessRating");
                double avgValueForMoneyRating = resultSet.getDouble("avgValueForMoneyRating");
                double percentPositive = resultSet.getDouble("percentPositive");
                double percentNeutral = resultSet.getDouble("percentNeutral");
                double percentNegative = resultSet.getDouble("percentNegative");
                int totalVotes = resultSet.getInt("totalVotes");

                // Set parameters for the INSERT statement
                insertStatement.setInt(1, itemID);
                insertStatement.setInt(2, categoryID);
                insertStatement.setDouble(3, avgQualityRating);
                insertStatement.setDouble(4, avgTasteRating);
                insertStatement.setDouble(5, avgFreshnessRating);
                insertStatement.setDouble(6, avgValueForMoneyRating);
                insertStatement.setDouble(7, percentPositive);
                insertStatement.setDouble(8, percentNeutral);
                insertStatement.setDouble(9, percentNegative);
                insertStatement.setInt(10, totalVotes);

                // Set current date as the date column
                Date currentDate = new Date(System.currentTimeMillis());
                insertStatement.setDate(11, currentDate);

                // Execute the INSERT statement
                insertStatement.executeUpdate();
            }

            System.out.println("Rows inserted successfully.");
            return JsonConverter.convertStatusAndMessageToJson("status", "insertion done");

        } catch (SQLException e) {
            e.printStackTrace();
            return JsonConverter.convertStatusAndMessageToJson("error", "failed to insert");
        }
    }
    
    
    
//    public void insertRecommendations() {
//        try (Statement statement = connection.createStatement()) {
//
//            String sql = "INSERT INTO Recommendation (itemID, categoryID, avgQualityRating, avgTasteRating, avgFreshnessRating, " +
//                         "avgValueForMoneyRating, percentPositive, percentNeutral, percentNegative, totalVotes) " +
//                         "SELECT f.itemID, c.categoryID, " +
//                         "       AVG(uf.qualityRating) AS avgQualityRating, " +
//                         "       AVG(uf.tasteRating) AS avgTasteRating, " +
//                         "       AVG(uf.freshnessRating) AS avgFreshnessRating, " +
//                         "       AVG(uf.valueForMoneyRating) AS avgValueForMoneyRating, " +
//                         "       SUM(CASE WHEN uf.sentiment = 'positive' THEN 1 ELSE 0 END) / COUNT(*) * 100 AS percentPositive, " +
//                         "       SUM(CASE WHEN uf.sentiment = 'neutral' THEN 1 ELSE 0 END) / COUNT(*) * 100 AS percentNeutral, " +
//                         "       SUM(CASE WHEN uf.sentiment = 'negative' THEN 1 ELSE 0 END) / COUNT(*) * 100 AS percentNegative, " +
//                         "       COUNT(*) AS totalVotes " +
//                         "FROM UserFeedbackOnFoodItem uf " +
//                         "JOIN FoodItemCategory fic ON uf.itemID = fic.itemID " +
//                         "JOIN FoodMenuItem f ON uf.itemID = f.itemID " +
//                         "JOIN Category c ON fic.categoryID = c.categoryID " +
//                         "GROUP BY f.itemID, c.categoryID";
//
//            int rowsInserted = statement.executeUpdate(sql);
//            System.out.println("Rows inserted: " + rowsInserted);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    
    
    public String finalReport() {
        String sql = "SELECT c.categoryName, f.nameOfFood AS itemName, cmr.numberOfVotes " +
                     "FROM ChefMenuRollout cmr " +
                     "JOIN FoodMenuItem f ON cmr.itemID = f.itemID " +
                     "JOIN Category c ON cmr.categoryID = c.categoryID " +
                     "WHERE cmr.rolloutDate = ? " +
                     "ORDER BY c.categoryName";

        JSONArray jsonArray = new JSONArray();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Set current date as parameter in the PreparedStatement
            pstmt.setDate(1, Date.valueOf(LocalDate.now()));

            // Execute query and process result set
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    String categoryName = resultSet.getString("categoryName");
                    String itemName = resultSet.getString("itemName");
                    int numberOfVotes = resultSet.getInt("numberOfVotes");

                    // Create JSON object for each row
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("categoryName", categoryName);
                    jsonObject.put("itemName", itemName);
                    jsonObject.put("numberOfVotes", numberOfVotes);

                  
                    jsonArray.add(jsonObject);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Return error JSON object if something goes wrong
            return "{\"error\": \"Failed to fetch recommendations\"}";
        }

        // Return JSON array as string
        return jsonArray.toString();
    }


    
    
    
    public JSONArray retrieveTopFoodByRating(String ratingType) {
        JSONArray jsonArray = new JSONArray();

        try (Statement statement = connection.createStatement()) {

            String sql = "SELECT r.itemID, f.nameOfFood, c.categoryName, " +
                         "       r.avgQualityRating, r.avgTasteRating, r.avgFreshnessRating, r.avgValueForMoneyRating " +
                         "FROM Recommendation r " +
                         "JOIN FoodMenuItem f ON r.itemID = f.itemID " +
                         "JOIN Category c ON r.categoryID = c.categoryID " +
                         "ORDER BY CASE '" + ratingType + "' " +
                         "         WHEN 'quality' THEN r.avgQualityRating " +
                         "         WHEN 'taste' THEN r.avgTasteRating " +
                         "         WHEN 'freshness' THEN r.avgFreshnessRating " +
                         "         WHEN 'valueForMoney' THEN r.avgValueForMoneyRating " +
                         "         ELSE 0 END DESC " +
                         "LIMIT 5";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                JSONObject jsonObject = new JSONObject();
                int itemID = resultSet.getInt("itemID");
                String foodName = resultSet.getString("nameOfFood");
                String categoryName = resultSet.getString("categoryName");
                double rating = resultSet.getDouble("avg" + ratingType.substring(0, 1).toUpperCase() + ratingType.substring(1) + "Rating");
                
                jsonObject.put("itemID", itemID);
                jsonObject.put("foodName", foodName);
                jsonObject.put("categoryName", categoryName);
                jsonObject.put("rating", rating);

                jsonArray.add(jsonObject);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    
    
    
    
    public JSONArray getCategoryRatingsOrderedByTaste() {
    	System.out.println("inside the function");
        JSONArray jsonArray = new JSONArray();

        try {
            String sql =  "SELECT " +
                    "    cmr.categoryID, " +
                    "    c.categoryName, " +
                    "    cmr.itemID, " +
                    "    f.nameOfFood AS itemName, " +
                    "    AVG(uf.tasteRating) AS avgTasteRating, " +
                    "    AVG(uf.qualityRating) AS avgQualityRating, " +
                    "    AVG(uf.freshnessRating) AS avgFreshnessRating, " +
                    "    AVG(uf.valueForMoneyRating) AS avgValueForMoneyRating, " +
                    "    AVG(CASE WHEN uf.sentiment = 'positive' THEN 1 ELSE 0 END) * 100 AS percentPositive, " +
                    "    AVG(CASE WHEN uf.sentiment = 'neutral' THEN 1 ELSE 0 END) * 100 AS percentNeutral, " +
                    "    AVG(CASE WHEN uf.sentiment = 'negative' THEN 1 ELSE 0 END) * 100 AS percentNegative, " +
                    "    COUNT(*) AS totalVotes " +
                    "FROM " +
                    "    ChefMenuRollout cmr " +
                    "JOIN " +
                    "    FoodMenuItem f ON cmr.itemID = f.itemID " +
                    "JOIN " +
                    "    Category c ON cmr.categoryID = c.categoryID " +
                    "JOIN " +
                    "    UserFeedbackOnFoodItem uf ON cmr.itemID = uf.itemID " + // Assuming this relation exists
                    "GROUP BY " +
                    "    cmr.categoryID, cmr.itemID " + 
                    "ORDER BY " +
                    "    avgTasteRating DESC";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                JSONObject jsonObject = new JSONObject();
                int categoryID = resultSet.getInt("categoryID");
                String categoryName = resultSet.getString("categoryName");
                int itemID = resultSet.getInt("itemID");
                String itemName = resultSet.getString("itemName");
                double avgTasteRating = resultSet.getDouble("avgTasteRating");
                double avgQualityRating = resultSet.getDouble("avgQualityRating");
                double avgFreshnessRating = resultSet.getDouble("avgFreshnessRating");
                double avgValueForMoneyRating = resultSet.getDouble("avgValueForMoneyRating");
                double percentPositive = resultSet.getDouble("percentPositive");
                double percentNeutral = resultSet.getDouble("percentNeutral");
                double percentNegative = resultSet.getDouble("percentNegative");
                int totalVotes = resultSet.getInt("totalVotes");

                jsonObject.put("categoryID", categoryID);
                jsonObject.put("categoryName", categoryName);
                jsonObject.put("itemID", itemID);
                jsonObject.put("itemName", itemName);
                jsonObject.put("avgTasteRating", avgTasteRating);
                jsonObject.put("avgQualityRating", avgQualityRating);
                jsonObject.put("avgFreshnessRating", avgFreshnessRating);
                jsonObject.put("avgValueForMoneyRating", avgValueForMoneyRating);
                jsonObject.put("percentPositive", percentPositive);
                jsonObject.put("percentNeutral", percentNeutral);
                jsonObject.put("percentNegative", percentNegative);
                jsonObject.put("totalVotes", totalVotes);

                jsonArray.add(jsonObject);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    
    
    public JSONArray retrieveBestFoodInEachCategory() {
        JSONArray jsonArray = new JSONArray();

        try (Statement statement = connection.createStatement()) {

            String sql = "SELECT r.itemID, f.nameOfFood, c.categoryName " +
                         "FROM Recommendation r " +
                         "JOIN FoodMenuItem f ON r.itemID = f.itemID " +
                         "JOIN Category c ON r.categoryID = c.categoryID " +
                         "WHERE r.categoryID IN (SELECT DISTINCT categoryID FROM Category) " +
                         "ORDER BY r.avgQualityRating DESC, r.avgTasteRating DESC, r.avgFreshnessRating DESC, r.avgValueForMoneyRating DESC";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                JSONObject jsonObject = new JSONObject();
                int itemID = resultSet.getInt("itemID");
                String foodName = resultSet.getString("nameOfFood");
                String categoryName = resultSet.getString("categoryName");

                jsonObject.put("itemID", itemID);
                jsonObject.put("foodName", foodName);
                jsonObject.put("categoryName", categoryName);

                jsonArray.add(jsonObject);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

   
}
