package server.service;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import server.util.JsonStringToObject;
import server.DatabaseOperation.DiscardedItemsListDatabaseOperator;
import server.model.DiscardedFoodItem;
import server.util.JsonConverter;

public class DiscardedItemListHandler {
    
    private DiscardedItemsListDatabaseOperator discardedItemsListDatabaseOperator;
    
    public DiscardedItemListHandler() {
        this.discardedItemsListDatabaseOperator = new DiscardedItemsListDatabaseOperator();
        System.out.println("discardedItemsListDatabaseOperator =========== " + discardedItemsListDatabaseOperator);
    }

    private String getDiscardedList(int month, int year) {
        try {
            this.discardedItemsListDatabaseOperator.executeDiscardedFoodItemsUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<DiscardedFoodItem> discardedItemLists = this.discardedItemsListDatabaseOperator.getAllDiscardedItems();
       
        String jsonResponse = JsonConverter.convertObjectToJson(discardedItemLists);
        System.out.println(jsonResponse);
        return jsonResponse;
    }

    public String getLastMonthDiscardedList() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        int lastMonth = cal.get(Calendar.MONTH) + 1; 
        int year = cal.get(Calendar.YEAR);
        return getDiscardedList(lastMonth, year);
    }

    public String insertChefDiscardsFoodItem(String data) {
        try {
            DiscardedFoodItem discardedFoodItem = JsonStringToObject.fromJsonToObject(data, DiscardedFoodItem.class);
            String response = this.discardedItemsListDatabaseOperator.insertDiscardedItemsIntoChefDiscardList(discardedFoodItem.getDiscardID());
            String jsonResponse = JsonConverter.convertStatusAndMessageToJson("info", response);
            NotificationService notificationService = new NotificationService();
            notificationService.addNotification("Chef Added " + discardedFoodItem.getItemName() + " in Discard Table Please give Feedback");
            return jsonResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return JsonConverter.convertStatusAndMessageToJson("error", "An error occurred: " + e.getMessage());
        }
    }

    public String deleteDiscardFoodItem(String data) {
        String jsonResponse = null;
        try {
            DiscardedFoodItem discardedFoodItem = JsonStringToObject.fromJsonToObject(data, DiscardedFoodItem.class);
            String response = this.discardedItemsListDatabaseOperator.deleteDiscardedItemFromMenu(discardedFoodItem.getDiscardID());
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("Success", response);
            NotificationService notificationService = new NotificationService();
            notificationService.addNotification("Chef deleted " + discardedFoodItem.getItemName() + " from Menu");
        } catch (Exception e) {
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("Error", "An error occurred while deleting the item.");
            e.printStackTrace();
        }
        return jsonResponse;
    }
    
    
    
    
    public String getChefDiscardedList() {
        String jsonResponse = null;
        
        try {
            List<DiscardedFoodItem> discardedItemLists = this.discardedItemsListDatabaseOperator.getAllChefDiscardedItems();
            
            if (discardedItemLists == null || discardedItemLists.isEmpty()) {
                jsonResponse = JsonConverter.convertStatusAndMessageToJson("info", "No discarded items found");
            } else {
                jsonResponse = JsonConverter.convertObjectToJson(discardedItemLists);
            }
        } catch (Exception e) {
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", "Failed to retrieve data");
        }
        
        System.out.println(jsonResponse);
        return jsonResponse;
    }

    
}
