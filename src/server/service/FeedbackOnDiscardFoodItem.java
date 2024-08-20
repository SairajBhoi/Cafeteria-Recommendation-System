package server.service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.databaseoperation.UserDiscardFeedbackDatabaseOper;
import server.model.UserDiscardedFeedback;
import server.util.JsonConverter;
import server.util.JsonStringToObject;

public class FeedbackOnDiscardFoodItem {

    private static final Logger logger = Logger.getLogger(FeedbackOnDiscardFoodItem.class.getName());
    private UserDiscardFeedbackDatabaseOper userDiscardFeedbackDatabaseOper;

    public FeedbackOnDiscardFoodItem() {
        this.userDiscardFeedbackDatabaseOper = new UserDiscardFeedbackDatabaseOper();
        logger.info("UserDiscardFeedbackDatabaseOper initialized.");
    }

    public String viewFeedback(String data) {
        UserDiscardedFeedback userDiscardedFeedback = JsonStringToObject.fromJsonToObject(data, UserDiscardedFeedback.class);
        List<UserDiscardedFeedback> userDiscardedFeedbackList = null;
        String jsonResponse = null;

        try {
            userDiscardedFeedbackList = userDiscardFeedbackDatabaseOper.viewAllFeedbackForDiscardID(userDiscardedFeedback.getDiscardID());

            if (userDiscardedFeedbackList == null || userDiscardedFeedbackList.isEmpty()) {
                jsonResponse = JsonConverter.convertStatusAndMessageToJson("info", "No feedback found for the given discard ID.");
            } else {
                jsonResponse = JsonConverter.convertObjectToJson(userDiscardedFeedbackList);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException occurred during viewFeedback: {0}", e.getMessage());
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", "Failed to retrieve feedback.");
        }

        return jsonResponse;
    }

    public String addFeedback(String data) {
        UserDiscardedFeedback userDiscardedFeedback = JsonStringToObject.fromJsonToObject(data, UserDiscardedFeedback.class);
        String jsonResponse = null;
        String response = null;

        try {
            response = userDiscardFeedbackDatabaseOper.insertUserDiscardedFeedback(userDiscardedFeedback);
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("Success", response);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException occurred during addFeedback: {0}", e.getMessage());
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", "An error occurred while adding feedback.");
        }

        return jsonResponse;
    }
}