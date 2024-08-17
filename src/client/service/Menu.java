package client.service;

import java.io.IOException;
import client.Client;
import RequestGateway.MenuRequestGateway;
import client.model.MenuItem;
import client.util.InputHandler;
import client.util.PrintOutToConsole;

public class Menu {
    private MenuItem menuItem;
    private MenuRequestGateway requestGateway;

    public Menu(String role) {
        this.requestGateway = new MenuRequestGateway(role);
    }

    public void addMenuItem() {
        menuItem = new MenuItem();
        fillMenuItemDetails(true);
        sendMenuItemRequest(requestGateway.createAddMenuItemRequest(menuItem));
    }

    public void updateMenuItem() {
        menuItem = new MenuItem();
        fillMenuItemDetails(false);
        sendMenuItemRequest(requestGateway.createUpdateMenuItemRequest(menuItem));
    }

    public void updateAvailabilityStatus() {
        try {
            menuItem = new MenuItem();
            String itemName = InputHandler.getStringInput("Enter the Food Item name: ");
            boolean isItemAvailable = InputHandler.getBooleanInput(itemName + " is Available: Enter 'no' for not available, 'yes' for available");

            menuItem.setItemName(itemName);
            menuItem.setItemAvailable(isItemAvailable);

            String jsonRequest = requestGateway.createUpdateFoodAvailableStatusRequest(menuItem);
            String jsonResponse = Client.requestServer(jsonRequest);
            PrintOutToConsole.printToConsole(jsonResponse);
        } catch (IOException e) {
            System.err.println("Error updating availability status: " + e.getMessage());
        }
    }

    public void deleteMenuItem() {
        try {
            String itemName = InputHandler.getStringInput("Enter item name: ");
            boolean deleteFromAllCategory = InputHandler.getBooleanInput(itemName + " delete the item from all categories? Enter 'no' to delete from specific category, 'yes' to delete from all categories");

            char category = 'a';
            if (!deleteFromAllCategory) {
                do {
                    category = Character.toLowerCase(InputHandler.getCharInput("Enter Food Category: \nb - breakfast\nl - lunch\ns - snacks\nd - dinner\n"));
                } while (!isValidCategory(category));
            }
            String categoryName = getCategoryName(category);

            MenuItem menuItem = new MenuItem(itemName, categoryName);
            String jsonRequest = requestGateway.createDeleteMenuItemRequest(menuItem);
            String jsonResponse = Client.requestServer(jsonRequest);
            PrintOutToConsole.printToConsole(jsonResponse);
        } catch (IOException e) {
            System.err.println("Error deleting menu item: " + e.getMessage());
        }
    }

    public void viewAllMenuItems() {
        try {
            String jsonRequest = requestGateway.createViewAllMenuItemsRequest();
            String jsonResponse = Client.requestServer(jsonRequest);
            System.out.println("Menu Item:");
            PrintOutToConsole.printToConsole(jsonResponse);
        } catch (IOException e) {
            System.err.println("Error viewing all menu items: " + e.getMessage());
        }
    }

    private void fillMenuItemDetails(boolean isCategoryRequired) {
        String itemName = InputHandler.getStringInput("Enter the Food Item name: ");
        float itemPrice = InputHandler.getFloatInput("Enter the Food Item price for " + itemName + ": ");
        boolean isItemAvailable = InputHandler.getBooleanInput(itemName + " is Available: Enter 'no' for not available, 'yes' for available");

        char category = 0;
        if (isCategoryRequired) {
            do {
                category = Character.toLowerCase(InputHandler.getCharInput("Enter Food Category: \nb - breakfast\nl - lunch\ns - snacks\nd - dinner\n"));
            } while (!isValidCategory(category));
        }
        String foodType;
        do {
            int foodTypeInput = InputHandler.getIntegerInput("Enter dietary preference (1 - Vegetarian, 2 - Non-Vegetarian, 3 - Eggetarian): ");
            foodType = mapFoodType(foodTypeInput);
            if (foodType == null) {
                System.out.println("Invalid input. Please enter a valid integer (1, 2, 3).");
            }
        } while (foodType == null);

        String spiceLevel;
        do {
            int spiceInput = InputHandler.getIntegerInput("Enter preferred spice level (1 - Low, 2 - Medium, 3 - High): ");
            spiceLevel = mapSpiceLevel(spiceInput);
            if (spiceLevel == null) {
                System.out.println("Invalid input. Please enter a valid integer (1, 2, 3).");
            }
        } while (spiceLevel == null);

        String cuisineType;
        do {
            int cuisineInput = InputHandler.getIntegerInput("Enter preferred cuisine (1 - North Indian, 2 - South Indian, 3 - Other): ");
            cuisineType = mapCuisine(cuisineInput);
            if (cuisineType == null) {
                System.out.println("Invalid input. Please enter a valid integer (1, 2, 3).");
            }
        } while (cuisineType == null);

        boolean isSweet = InputHandler.getBooleanInput("Does " + itemName + " have a sweet taste?");
        if (isCategoryRequired) {
            String categoryName = getCategoryName(category);
            menuItem.setItemCategory(categoryName);
        }
        menuItem.setItemName(itemName);
        menuItem.setItemPrice(itemPrice);
        menuItem.setItemAvailable(isItemAvailable);
        menuItem.setCuisineType(cuisineType);
        menuItem.setFoodType(foodType);
        menuItem.setSpiceLevel(spiceLevel);
        menuItem.setSweet(isSweet);
    }

    private void sendMenuItemRequest(String jsonRequest) {
        try {
            String jsonResponse = Client.requestServer(jsonRequest);
            PrintOutToConsole.printToConsole(jsonResponse);
        } catch (IOException e) {
            System.err.println("Error sending menu item request: " + e.getMessage());
        }
    }

    private boolean isValidCategory(char category) {
        return category == 'b' || category == 'l' || category == 's' || category == 'd';
    }

    private String getCategoryName(char category) {
        switch (category) {
            case 'a':
                return "all";
            case 'b':
                return "breakfast";
            case 'l':
                return "lunch";
            case 's':
                return "snacks";
            case 'd':
                return "dinner";
            default:
                return "unknown";
        }
    }

    private String mapFoodType(int dietaryInput) {
        switch (dietaryInput) {
            case 1:
                return "Vegetarian";
            case 2:
                return "Non-Vegetarian";
            case 3:
                return "Eggetarian";
            default:
                return null;
        }
    }

    private String mapSpiceLevel(int spiceInput) {
        switch (spiceInput) {
            case 1:
                return "Low";
            case 2:
                return "Medium";
            case 3:
                return "High";
            default:
                return null;
        }
    }

    private String mapCuisine(int cuisineInput) {
        switch (cuisineInput) {
            case 1:
                return "North Indian";
            case 2:
                return "South Indian";
            case 3:
                return "Other";
            default:
                return null;
        }
    }
}
