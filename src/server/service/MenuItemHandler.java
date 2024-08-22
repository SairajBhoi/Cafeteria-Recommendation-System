package server.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.databaseoperation.MenuDatabaseOperator;
import server.model.MenuItem;
import server.util.JsonConverter;
import server.util.JsonStringToObject;

public class MenuItemHandler {
    private static final Logger logger = Logger.getLogger(MenuItemHandler.class.getName());
    private final MenuDatabaseOperator menu;

    public MenuItemHandler() throws SQLException {
        this.menu = new MenuDatabaseOperator();
    }

    public String addMenuItem(String data) {
        String jsonResponse;
        try {
            MenuItem menuItem = JsonStringToObject.fromJsonToObject(data, MenuItem.class);
            if (!menu.isItemInCategory(menuItem.getItemName(), menuItem.getItemCategory())) {
                boolean added = menu.addMenuItem(menuItem);
                if (added) {
                    jsonResponse = JsonConverter.convertStatusAndMessageToJson("success", "Added item to menu.");
                    sendNotification("Added " + menuItem.getItemName() + " to Main Menu");
                } else {
                    jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", "Error adding menu item");
                }
            } else {
                jsonResponse = JsonConverter.convertStatusAndMessageToJson("info", "Item already present in menu");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred during addMenuItem: {0}", e.getMessage());
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", e.getMessage());
        }
        return jsonResponse;
    }

    public String updateMenuItem(String data) {
        String jsonResponse;
        try {
            MenuItem menuItem = JsonStringToObject.fromJsonToObject(data, MenuItem.class);
            boolean status = menu.updateMenuItem(menuItem);
            if (status) {
                jsonResponse = JsonConverter.convertStatusAndMessageToJson("success", "Updated menu item.");
            } else {
                jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", "Error updating menu item.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred during updateMenuItem: {0}", e.getMessage());
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", "Error updating menu item. " + e.getMessage());
        }
        return jsonResponse;
    }

    public String deleteMenuItem(String data) {
        String jsonResponse;
        try {
            MenuItem menuItem = JsonStringToObject.fromJsonToObject(data, MenuItem.class);
            boolean deleted = menu.deleteMenuItem(menuItem.getItemName(), menuItem.getItemCategory());
            if (deleted) {
                jsonResponse = JsonConverter.convertStatusAndMessageToJson("success", "Deleted menu item.");
                sendNotification("Deleted " + menuItem.getItemName() + " Food Item from Main Menu");
            } else {
                jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", "Error deleting menu item.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred during deleteMenuItem: {0}", e.getMessage());
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", e.getMessage());
        }
        return jsonResponse;
    }

    public String updateFoodAvailableStatus(String data) {
        String jsonResponse;
        try {
            String itemName = JsonStringToObject.getValueFromData("itemName", data);
            boolean availabilityStatus = JsonStringToObject.getValueFromData("itemAvailable", data).equalsIgnoreCase("true");
            boolean status = menu.updateAvailability(itemName, availabilityStatus);
            sendNotification("Updated availability status of " + itemName);
            jsonResponse = status 
                ? JsonConverter.convertStatusAndMessageToJson("success", "Updated availability status in menu.")
                : JsonConverter.convertStatusAndMessageToJson("error", "Error updating availability status.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred during updateFoodAvailableStatus: {0}", e.getMessage());
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", e.getMessage());
        }
        return jsonResponse;
    }

    public String viewAllMenuItems() {
        String jsonResponse;
        try {
            List<Map<String, Object>> menuList = menu.viewMenuItems();
            if (menuList == null || menuList.isEmpty()) {
                jsonResponse = JsonConverter.convertStatusAndMessageToJson("info", "No menu items found");
            } else {
                jsonResponse = JsonConverter.convertObjectToJson(menuList);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred during viewAllMenuItems: {0}", e.getMessage());
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", e.getMessage());
        }
        return jsonResponse;
    }

    private void sendNotification(String message) {
        try {
            NotificationService notificationService = new NotificationService();
            notificationService.addNotification(message);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to send notification: {0}", e.getMessage());
        }
    }
}
