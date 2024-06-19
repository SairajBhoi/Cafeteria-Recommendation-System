package server.service;

import java.util.List;
import java.util.Map;

import server.FoodFeedbackSentimentAnalysis;
import server.DatabaseOperation.EmployeeFeedback;
import server.model.Feedback;
import server.util.JsonConverter;
import server.util.JsonStringToObject;

public class FeedbackService {
	private Feedback feedback;
	
	 public String addFeedback(String  data) {
			String jsonResponse;
			try {
				feedback = JsonStringToObject.fromJsonToObject(data, Feedback.class);
				EmployeeFeedback empFeedback = new EmployeeFeedback();
				String message =empFeedback.addFeedback(feedback);
				String empFeedbackSentiment=feedback.getFeedbackMessage();
				FoodFeedbackSentimentAnalysis foodFeedbackSentimentAnalysis= new FoodFeedbackSentimentAnalysis("SentimentKeyWords.txt");
				foodFeedbackSentimentAnalysis.analyzeSentiment(empFeedbackSentiment);
				
				jsonResponse=JsonConverter.convertStatusAndMessageToJson("success",message);
	
			} catch (Exception e) {
				jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", e.getMessage());
			}
			return jsonResponse;
		    }

	 
	 
	 public String viewFeedback(String data) {
		    try {
		    	String itemName=JsonStringToObject.getValueFromData("itemName", data);
		    	EmployeeFeedback employeeFeedback = new EmployeeFeedback();
		        List<Map<String, Object>> feedbackList = employeeFeedback.viewFeedback(itemName);
		        return JsonConverter.convertObjectToJson(feedbackList);
		    } catch (Exception e) {
		        e.printStackTrace(); 
		        return JsonConverter.convertStatusAndMessageToJson("error", e.getMessage());
		    }
		}
	 
}
