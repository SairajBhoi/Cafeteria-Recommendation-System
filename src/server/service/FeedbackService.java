package server.service;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.databaseoperation.EmployeeFeedbackDatabaseOperator;
import server.model.Feedback;
import server.resources.FoodFeedbackSentimentAnalysis;
import server.util.JsonConverter;
import server.util.JsonStringToObject;

public class FeedbackService {
    private static final Logger logger = Logger.getLogger(FeedbackService.class.getName());
    private Feedback feedback;

    public String addFeedback(String data) {
        String jsonResponse;
        try {
            feedback = JsonStringToObject.fromJsonToObject(data, Feedback.class);
            String feedbackMessage = feedback.getFeedbackMessage();
            
           
            logger.info(String.format("Received feedback message: %s", feedbackMessage));

            FoodFeedbackSentimentAnalysis sentimentAnalysis = new FoodFeedbackSentimentAnalysis();
            String sentiment = sentimentAnalysis.analyzeSentiment(feedbackMessage);

            feedback.setFeedbackMessageSentiment(sentiment);
            logger.info(String.format("Feedback sentiment: %s", feedback.getFeedbackMessageSentiment()));

            EmployeeFeedbackDatabaseOperator empFeedback = new EmployeeFeedbackDatabaseOperator();
            String message = empFeedback.addFeedback(feedback);

            jsonResponse = JsonConverter.convertStatusAndMessageToJson("success", message);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred during addFeedback: {0}", e.getMessage());
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", e.getMessage());
        }
        return jsonResponse;
    }

    public String viewFeedback(String data) {
        String jsonResponse = null;
        try {
            String itemName = JsonStringToObject.getValueFromData("itemName", data);
            EmployeeFeedbackDatabaseOperator employeeFeedback = new EmployeeFeedbackDatabaseOperator();
            List<Map<String, Object>> feedbackList = employeeFeedback.viewFeedback(itemName);

            if (feedbackList == null || feedbackList.isEmpty()) {
                jsonResponse = JsonConverter.convertStatusAndMessageToJson("info", "No feedback found for the given item name");
            } else {
                jsonResponse = JsonConverter.convertObjectToJson(feedbackList);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred during viewFeedback: {0}", e.getMessage());
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", e.getMessage());
        }

        return jsonResponse;
    }
}
