package server.resources.recommendationEngine;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;



import java.util.List;



import org.json.simple.JSONArray;

import server.model.UserPreference;
import server.util.JsonConverter;
import server.DatabaseOperation.ChefMenuRolloutDatabaseOperator;
import server.DatabaseOperation.UserProfileDatabaseOperator;
import server.model.ChefMenuItemScore;
import server.model.FoodCategory;
import server.model.MenuItem;
import server.util.JsonStringToObject;

public class RecommendationService {
	
	
	
public String getFoodItemForNextDay(String data){
		
		FoodCategory category =JsonStringToObject.fromJsonToObject(data,FoodCategory.class);
		
		RecommendationDatabaseOperator recommendationEngine = new RecommendationDatabaseOperator();
		List <String> result =recommendationEngine.getFoodItemForNextDay(category.getCategoryName(), category.getNumberOfItems());
		String jsonResponse= JsonConverter.convertObjectToJson(result);
		
	return jsonResponse;
	}



	public String getFoodItemByTaste(){
		
		RecommendationDatabaseOperator recommendationEngine = new RecommendationDatabaseOperator();
		JSONArray result =recommendationEngine.getCategoryRatingsOrderedByTaste();
		
	return result.toString();
	}


	public String getFoodOrderbyRatingOnChefRollout() {
	    LocalDate currentDate = LocalDate.now();
	    RecommendationDatabaseOperator recommendationEngine = new RecommendationDatabaseOperator();
	    JSONArray jsonArray = recommendationEngine.getFoodItemRatingsForDate(currentDate);
	    String jsonResponse= JsonConverter.convertObjectToJson(jsonArray);
	    
	    System.out.print(jsonResponse);
	    return jsonResponse;
	}





	public String getFoodOrderbyUserPreferenceOnChefRollout(String data) {
		
		String userID=JsonStringToObject.getValueFromData("UserID", data);
        UserProfileDatabaseOperator userProfileDatabaseOperator = new UserProfileDatabaseOperator();
        UserPreference userPreference = null;

        try {
            userPreference = userProfileDatabaseOperator.getUserPreference(userID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (userPreference == null) {
            return JsonConverter.convertObjectToJson(new ArrayList<>());
        }

        ChefMenuRolloutDatabaseOperator chefMenuRolloutDatabaseOperator = new ChefMenuRolloutDatabaseOperator();
        List<MenuItem> rollouts = new ArrayList<>();

        try {
            LocalDate today = LocalDate.now();
            rollouts = chefMenuRolloutDatabaseOperator.getChefMenuItemsByRolloutDate(Date.valueOf(today));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<ChefMenuItemScore> chefMenuItemScore = calculateScores(userPreference, rollouts);
        List<ChefMenuItemScore> sortedChefMenuItemScore = sortChefMenuItemScores(chefMenuItemScore);

        return JsonConverter.convertObjectToJson(sortedChefMenuItemScore);
    }

    private List<ChefMenuItemScore> calculateScores(UserPreference userPreference, List<MenuItem> rollouts) {
        List<ChefMenuItemScore> scoredItems = new ArrayList<>();

        for (MenuItem menuItem : rollouts) {
            double score = calculateScoreForMenuItem(userPreference, menuItem);
            ChefMenuItemScore chefMenuItemScore = new ChefMenuItemScore();
            chefMenuItemScore.setItemName(menuItem.getItemName());
            chefMenuItemScore.setItemPrice(menuItem.getItemPrice());
            chefMenuItemScore.setItemAvailable(menuItem.isItemAvailable());
            chefMenuItemScore.setItemCategory(menuItem.getItemCategory());
            chefMenuItemScore.setCuisineType(menuItem.getCuisineType());
            chefMenuItemScore.setFoodType(menuItem.getFoodType());
            chefMenuItemScore.setSpiceLevel(menuItem.getSpiceLevel());
            chefMenuItemScore.setSweet(menuItem.isSweet());
            chefMenuItemScore.setScore(score);
            scoredItems.add(chefMenuItemScore);
        }

        return scoredItems;
    }

    private double calculateScoreForMenuItem(UserPreference userPreference, MenuItem menuItem) {
        double score = 0.0;

        if (menuItem.getFoodType().equalsIgnoreCase(userPreference.getDietaryPreference())) {
            score += 3.0;
        }

        if (menuItem.getCuisineType().equalsIgnoreCase(userPreference.getPreferredCuisine())) {
            score += 1.5;
        }

        if (menuItem.getSpiceLevel().equalsIgnoreCase(userPreference.getPreferredSpiceLevel())) {
            score += 0.75;
        }

        if (menuItem.isSweet() == userPreference.isHasSweetTooth()) {
            score += 0.33;
        }

        return score;
    }

    private List<ChefMenuItemScore> sortChefMenuItemScores(List<ChefMenuItemScore> chefMenuItemScores) {
        int itemCount = chefMenuItemScores.size();
        ChefMenuItemScore temp;

        // Bubble sort
        for (int currentPass = 0; currentPass < itemCount - 1; currentPass++) {
            for (int currentIndex = 0; currentIndex < itemCount - currentPass - 1; currentIndex++) {
                if (chefMenuItemScores.get(currentIndex).getScore() < chefMenuItemScores.get(currentIndex + 1).getScore()) {
                    temp = chefMenuItemScores.get(currentIndex);
                    chefMenuItemScores.set(currentIndex, chefMenuItemScores.get(currentIndex + 1));
                    chefMenuItemScores.set(currentIndex + 1, temp);
                }
            }
        }

        return chefMenuItemScores;
    }


	
}