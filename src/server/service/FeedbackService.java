package server.service;

import java.util.List;
import java.util.Map;

import server.DatabaseOperation.EmployeeFeedbackDatabaseOperator;
import server.model.Feedback;
import server.resources.FoodFeedbackSentimentAnalysis;
import server.util.JsonConverter;
import server.util.JsonStringToObject;

public class FeedbackService {
	private Feedback feedback;
	
	public String addFeedback(String data) {
	    String jsonResponse;
	    try {
	       
	        Feedback feedback = JsonStringToObject.fromJsonToObject(data, Feedback.class);
	        
	     
	        String feedbackMessage = feedback.getFeedbackMessage();
	        
	        System.out.println(feedbackMessage);
	        FoodFeedbackSentimentAnalysis sentimentAnalysis = new FoodFeedbackSentimentAnalysis("D:\\Learn_and_Code\\final-assignment-june\\Cafeteria-Recommendation-System\\src\\server\\resources\\SentimentKeyWords.txt");
	        String sentiment = sentimentAnalysis.analyzeSentiment(feedbackMessage);
	   
	        feedback.setFeedbackMessageSentiment(sentiment);
	       System.out.println(feedback.getFeedbackMessageSentiment());
	
	        EmployeeFeedbackDatabaseOperator empFeedback = new EmployeeFeedbackDatabaseOperator();
	        String message = empFeedback.addFeedback(feedback);
	        
	    
	        jsonResponse = JsonConverter.convertStatusAndMessageToJson("success", message);
	    } catch (Exception e) {
	    
	        jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", e.getMessage());
	    }
	    return jsonResponse;
	}
	 
	 
	 public String viewFeedback(String data) {
		    try {
		    	String itemName=JsonStringToObject.getValueFromData("itemName", data);
		    	EmployeeFeedbackDatabaseOperator employeeFeedback = new EmployeeFeedbackDatabaseOperator();
		        List<Map<String, Object>> feedbackList = employeeFeedback.viewFeedback(itemName);
		        return JsonConverter.convertObjectToJson(feedbackList);
		    } catch (Exception e) {
		        e.printStackTrace(); 
		        return JsonConverter.convertStatusAndMessageToJson("error", e.getMessage());
		    }
		}
	 
}
