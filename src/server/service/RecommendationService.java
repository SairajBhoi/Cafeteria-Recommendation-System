package server.service;
import java.util.List;
import java.util.Locale.Category;

import org.json.simple.JSONArray;

import server.util.JsonConverter;
import server.model.FoodCategory;
import server.resources.RecommendationEngine;
import server.util.JsonStringToObject;

public class RecommendationService {
	
	
	
public String getFoodItemForNextDay(String data){
	
	FoodCategory category =JsonStringToObject.fromJsonToObject(data,FoodCategory.class);
	
	RecommendationEngine recommendationEngine = new RecommendationEngine();
	List <String> result =recommendationEngine.getFoodItemForNextDay(category.getCategoryName(), category.getNumberOfItems());
	String jsonResponse= JsonConverter.convertObjectToJson(result);
	
return jsonResponse;
}



public String getFoodItemByTaste(){
	
	RecommendationEngine recommendationEngine = new RecommendationEngine();
	JSONArray result =recommendationEngine.getCategoryRatingsOrderedByTaste();
	
return result.toString();
}






}