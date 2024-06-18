package server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RecommendationEngine {

    private Connection connection;

    public RecommendationEngine() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public void insertRecommendations() {
        try (Statement statement = connection.createStatement()) {

            String sql = "INSERT INTO Recommendation (itemID, categoryID, avgQualityRating, avgTasteRating, avgFreshnessRating, " +
                         "avgValueForMoneyRating, percentPositive, percentNeutral, percentNegative, totalVotes) " +
                         "SELECT f.itemID, c.categoryID, " +
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

            int rowsInserted = statement.executeUpdate(sql);
            System.out.println("Rows inserted: " + rowsInserted);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void retrieveTopFoodByRating(String ratingType) {
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

            System.out.println("Top Food Items by " + ratingType + " rating:");
            while (resultSet.next()) {
                int itemID = resultSet.getInt("itemID");
                String foodName = resultSet.getString("nameOfFood");
                String categoryName = resultSet.getString("categoryName");
                double rating = resultSet.getDouble("avg" + ratingType.substring(0, 1).toUpperCase() + ratingType.substring(1) + "Rating");
                System.out.println("Category: " + categoryName + ", Food Item: " + foodName + ", " + ratingType + " rating: " + rating);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void retrieveBestFoodInEachCategory() {
        try (Statement statement = connection.createStatement()) {

            String sql = "SELECT r.itemID, f.nameOfFood, c.categoryName " +
                         "FROM Recommendation r " +
                         "JOIN FoodMenuItem f ON r.itemID = f.itemID " +
                         "JOIN Category c ON r.categoryID = c.categoryID " +
                         "WHERE r.categoryID IN (SELECT DISTINCT categoryID FROM Category) " +
                         "ORDER BY r.avgQualityRating DESC, r.avgTasteRating DESC, r.avgFreshnessRating DESC, r.avgValueForMoneyRating DESC";

            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("Best Food Items in Each Category:");
            while (resultSet.next()) {
                int itemID = resultSet.getInt("itemID");
                String foodName = resultSet.getString("nameOfFood");
                String categoryName = resultSet.getString("categoryName");
                System.out.println("Category: " + categoryName + ", Food Item: " + foodName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
}
