package server.resources;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import server.util.JsonConverter;
import server.DatabaseConnection;

public class RecommendationEngine {

    private Connection connection;

    public RecommendationEngine() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

  
        public List<String> getFoodItemForNextDay(String categoryName, int NumberOfItems) {
            List<String> foodItemNames = new ArrayList<>();

            String query = "SELECT " +
                    "    m.nameOfFood " +
                    "FROM " +
                    "    UserFeedbackOnFoodItem f " +
                    "JOIN " +
                    "    FoodItemCategory fic ON f.itemID = fic.itemID " +
                    "JOIN " +
                    "    Category c ON fic.categoryID = c.categoryID " +
                    "JOIN " +
                    "    FoodMenuItem m ON f.itemID = m.itemID " +
                    "WHERE " +
                    "    c.categoryName = ? " +
                    "GROUP BY " +
                    "    f.itemID, m.nameOfFood, m.foodPrice, m.foodAvailable, c.categoryName " +
                    "ORDER BY " +
                    "    AVG(f.qualityRating) DESC " +
                    "LIMIT ?";

            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                
                pstmt.setString(1, categoryName);
                pstmt.setInt(2, NumberOfItems);

                
                try (ResultSet rs = pstmt.executeQuery()) {
                 
                    while (rs.next()) {
                        String nameOfFood = rs.getString("nameOfFood");
                        foodItemNames.add(nameOfFood);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(); 
                throw new RuntimeException("Error fetching food item names: " + e.getMessage());
            }

            return foodItemNames;
        }
 


        
        
 
        public JSONArray getFoodItemRatingsForDate(LocalDate rolloutDate) {
            JSONArray jsonArray = new JSONArray();

            String query = "SELECT " +
                    "    r.rolloutDate, " +
                    "    c.categoryName, " + 
                    "    f.nameOfFood, " +
                    "    ar.avgQualityRating, " +
                    "    ar.avgTasteRating, " +
                    "    ar.avgFreshnessRating, " +
                    "    ar.avgValueForMoneyRating, " +
                    "    ar.avgOverallRating " +
                    "FROM ChefMenuRollout r " +
                    "JOIN ( " +
                    "    SELECT " +
                    "        itemID, " +
                    "        AVG(qualityRating) AS avgQualityRating, " +
                    "        AVG(tasteRating) AS avgTasteRating, " +
                    "        AVG(freshnessRating) AS avgFreshnessRating, " +
                    "        AVG(valueForMoneyRating) AS avgValueForMoneyRating, " +
                    "        (AVG(qualityRating) + AVG(tasteRating) + AVG(freshnessRating) + AVG(valueForMoneyRating)) / 4 AS avgOverallRating " +
                    "    FROM UserFeedbackOnFoodItem " +
                    "    GROUP BY itemID " +
                    ") ar ON r.itemID = ar.itemID " +
                    "JOIN FoodMenuItem f ON r.itemID = f.itemID " +
                    "JOIN Category c ON r.categoryID = c.categoryID " +
                    "WHERE r.rolloutDate = ? " +
                    "ORDER BY ar.avgOverallRating DESC";

            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setDate(1, Date.valueOf(rolloutDate));

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("nameOfFood", rs.getString("nameOfFood"));
                        jsonObject.put("categoryName", rs.getString("categoryName"));
                        jsonObject.put("rolloutDate", rs.getDate("rolloutDate").toString());                 
                        jsonObject.put("avgQualityRating", rs.getDouble("avgQualityRating"));
                        jsonObject.put("avgTasteRating", rs.getDouble("avgTasteRating"));
                        jsonObject.put("avgFreshnessRating", rs.getDouble("avgFreshnessRating"));
                        jsonObject.put("avgValueForMoneyRating", rs.getDouble("avgValueForMoneyRating"));
                        jsonObject.put("avgOverallRating", rs.getDouble("avgOverallRating"));

                        jsonArray.add(jsonObject);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Error fetching food item ratings for date: " + e.getMessage());
            }

            return jsonArray;
        }

    
        
        
        
    
    

    
    public String finalReport() {
        String sql = "SELECT c.categoryName, f.nameOfFood AS itemName, cmr.numberOfVotes " +
                     "FROM ChefMenuRollout cmr " +
                     "JOIN FoodMenuItem f ON cmr.itemID = f.itemID " +
                     "JOIN Category c ON cmr.categoryID = c.categoryID " +
                     "WHERE cmr.rolloutDate = ? " +
                     "ORDER BY c.categoryName";

        JSONArray jsonArray = new JSONArray();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            pstmt.setDate(1, Date.valueOf(LocalDate.now()));

          
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    String categoryName = resultSet.getString("categoryName");
                    String itemName = resultSet.getString("itemName");
                    int numberOfVotes = resultSet.getInt("numberOfVotes");

                   
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
        JSONArray jsonArray = new JSONArray();
        try{
                   LocalDate currentDate = LocalDate.now();

            String sql = "SELECT " +
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
                         "    UserFeedbackOnFoodItem uf ON cmr.itemID = uf.itemID " +
                         "WHERE " +
                         "    cmr.rolloutDate = ? " + 
                         "GROUP BY " +
                         "    cmr.categoryID, cmr.itemID " +
                         "ORDER BY " +
                         "    avgTasteRating DESC";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setObject(1, currentDate); 

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
