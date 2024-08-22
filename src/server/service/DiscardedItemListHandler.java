package server.service;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.util.JsonStringToObject;
import server.databaseoperation.DiscardedItemsListDatabaseOperator;
import server.model.DiscardedFoodItem;
import server.util.JsonConverter;

public class DiscardedItemListHandler {
    
    private static final Logger logger = Logger.getLogger(DiscardedItemListHandler.class.getName());
    private final DiscardedItemsListDatabaseOperator discardedItemsListDatabaseOperator;

    public DiscardedItemListHandler() {
        this.discardedItemsListDatabaseOperator = new DiscardedItemsListDatabaseOperator();
        logger.info("DiscardedItemsListDatabaseOperator initialized.");
    }

    private String getDiscardedList(int month, int year) {
        String jsonResponse;
        try {
            this.discardedItemsListDatabaseOperator.executeDiscardedFoodItemsUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException occurred during executeDiscardedFoodItemsUpdate: {0}", e.getMessage());
        }

        List<DiscardedFoodItem> discardedItemLists = this.discardedItemsListDatabaseOperator.getAllDiscardedItems();
        jsonResponse = JsonConverter.convertObjectToJson(discardedItemLists);
        logger.info("Discarded items JSON response: " + jsonResponse);
        return jsonResponse;
    }

    public String getLastMonthDiscardedList() {
        String jsonResponse;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        int lastMonth = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        jsonResponse = getDiscardedList(lastMonth, year);
        return jsonResponse;
    }

    public String insertChefDiscardsFoodItem(String data) {
        String jsonResponse;
        try {
            DiscardedFoodItem discardedFoodItem = JsonStringToObject.fromJsonToObject(data, DiscardedFoodItem.class);
            String response = this.discardedItemsListDatabaseOperator.insertDiscardedItemsIntoChefDiscardList(discardedFoodItem.getDiscardID());
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("info", response);

            NotificationService notificationService = new NotificationService();
            notificationService.addNotification("Chef added " + discardedFoodItem.getItemName() + " in Discard Table. Please give feedback.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred during insertChefDiscardsFoodItem: {0}", e.getMessage());
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", "An error occurred: " + e.getMessage());
        }
        return jsonResponse;
    }

    public String deleteDiscardFoodItem(String data) {
        String jsonResponse;
        try {
            DiscardedFoodItem discardedFoodItem = JsonStringToObject.fromJsonToObject(data, DiscardedFoodItem.class);
            String response = this.discardedItemsListDatabaseOperator.deleteDiscardedItemFromMenu(discardedFoodItem.getDiscardID());
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("Success", response);

            NotificationService notificationService = new NotificationService();
            notificationService.addNotification("Chef deleted " + discardedFoodItem.getItemName() + " from Menu.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred during deleteDiscardFoodItem: {0}", e.getMessage());
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("Error", "An error occurred while deleting the item.");
        }
        return jsonResponse;
    }
    
    public String getChefDiscardedList() {
        String jsonResponse;
        try {
            List<DiscardedFoodItem> discardedItemLists = this.discardedItemsListDatabaseOperator.getAllChefDiscardedItems();
            if (discardedItemLists == null || discardedItemLists.isEmpty()) {
                jsonResponse = JsonConverter.convertStatusAndMessageToJson("info", "No discarded items found.");
            } else {
                jsonResponse = JsonConverter.convertObjectToJson(discardedItemLists);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred during getChefDiscardedList: {0}", e.getMessage());
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", "Failed to retrieve data.");
        }
        logger.info(String.format("Discarded items JSON response: %s", jsonResponse));
        return jsonResponse;
    }
}
