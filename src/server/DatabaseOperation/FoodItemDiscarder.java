package server.DatabaseOperation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class FoodItemDiscarder {
	
	
	
	
//	public Boolean checkConditionForDiscarding {
//       
//
//         String sqlQuery="SELECT \r\n"
//         		+ "    itemID,\r\n"
//         		+ "    nameOfFood,\r\n"
//         		+ "    avgQualityRating,\r\n"
//         		+ "    avgTasteRating,\r\n"
//         		+ "    avgFreshnessRating,\r\n"
//         		+ "    avgValueForMoneyRating,\r\n"
//         		+ "    (avgQualityRating + avgTasteRating + avgFreshnessRating + avgValueForMoneyRating) / 4 AS overAllRating\r\n"
//         		+ "FROM (\r\n"
//         		+ "    SELECT \r\n"
//         		+ "        FoodMenuItem.itemID,\r\n"
//         		+ "        FoodMenuItem.nameOfFood,\r\n"
//         		+ "        AVG(UserFeedbackOnFoodItem.qualityRating) AS avgQualityRating,\r\n"
//         		+ "        AVG(UserFeedbackOnFoodItem.tasteRating) AS avgTasteRating,\r\n"
//         		+ "        AVG(UserFeedbackOnFoodItem.freshnessRating) AS avgFreshnessRating,\r\n"
//         		+ "        AVG(UserFeedbackOnFoodItem.valueForMoneyRating) AS avgValueForMoneyRating\r\n"
//         		+ "    FROM \r\n"
//         		+ "        UserFeedbackOnFoodItem\r\n"
//         		+ "    JOIN \r\n"
//         		+ "        FoodMenuItem ON UserFeedbackOnFoodItem.itemID = FoodMenuItem.itemID\r\n"
//         		+ "    WHERE \r\n"
//         		+ "        UserFeedbackOnFoodItem.feedbackGivenDate BETWEEN '2024-01-01' AND '2024-06-30'\r\n"
//         		+ "    GROUP BY \r\n"
//         		+ "        FoodMenuItem.itemID, FoodMenuItem.nameOfFood\r\n"
//         		+ ") AS subquery;\r\n"
//         		+ "";
//        
//
//         return true;
//    }
    

}
