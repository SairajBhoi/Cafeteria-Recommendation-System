package server.service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.databaseoperation.ChefMenuRolloutDatabaseOperator;
import server.model.ChefMenuRollout;
import server.util.JsonConverter;
import server.util.JsonStringToObject;

public class ChefMenuRolloutHandler {

    private static final Logger logger = Logger.getLogger(ChefMenuRolloutHandler.class.getName());

    public String rolloutMenu(String data) {
        ChefMenuRolloutDatabaseOperator chefMenuRolloutDAO = new ChefMenuRolloutDatabaseOperator();
        ChefMenuRollout chefMenuRollout = JsonStringToObject.fromJsonToObject(data, ChefMenuRollout.class);
        String jsonResponse;

        try {
            boolean status = chefMenuRolloutDAO.insertChefMenuRollout(chefMenuRollout);
            jsonResponse = status 
                ? JsonConverter.convertStatusAndMessageToJson("success", "Added to rollout menu")
                : JsonConverter.convertStatusAndMessageToJson("failure", "Failed to add to rollout menu");

            if (status) {
                NotificationService notificationService = new NotificationService();
                notificationService.addNotification("Chef rolled out menu. Please check and vote.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception occurred while inserting Chef Menu Rollout: {0}", e.getMessage().replace("'", "\\'"));
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", "Database error occurred. Please try again later.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error occurred: {0}", e.getMessage().replace("'", "\\'"));
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", "An unexpected error occurred. Please contact support.");
        }

        return jsonResponse;
    }

    public String viewRolloutResult() {
        ChefMenuRolloutDatabaseOperator chefMenuRolloutDAO = new ChefMenuRolloutDatabaseOperator();
        String jsonResponse;

        try {
            List<ChefMenuRollout> rollouts = chefMenuRolloutDAO.getTodayRollouts();
            jsonResponse = (rollouts == null || rollouts.isEmpty())
                ? JsonConverter.convertStatusAndMessageToJson("info", "No rollouts created for today.")
                : JsonConverter.convertObjectToJson(rollouts);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception occurred while fetching today's rollouts: {0}", e.getMessage().replace("'", "\\'"));
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", "Database error occurred while fetching today's rollouts.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error occurred: {0}", e.getMessage().replace("'", "\\'"));
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", "An unexpected error occurred. Please contact support.");
        }

        return jsonResponse;
    }
}
