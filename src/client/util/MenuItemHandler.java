package client.util;

import client.model.MenuItem;

public class MenuItemHandler {

    public MenuItem createMenuItem(boolean isCategoryRequired) {
        String itemName = InputHandler.getStringInput("Enter the Food Item name: ");
        float itemPrice = InputHandler.getFloatInput("Enter the Food Item price for " + itemName + ": ");
        boolean isItemAvailable = InputHandler.getBooleanInput(itemName + " is Available: Enter 'no' for not available, 'yes' for available");

        String categoryName = isCategoryRequired ? getCategoryNameInput() : null;
        String foodType = getFoodTypeInput();
        String spiceLevel = getSpiceLevelInput();
        String cuisineType = getCuisineTypeInput();
        boolean isSweet = InputHandler.getBooleanInput("Does " + itemName + " have a sweet taste?");

        MenuItem menuItem = new MenuItem();
        menuItem.setItemName(itemName);
        menuItem.setItemPrice(itemPrice);
        menuItem.setItemAvailable(isItemAvailable);
        menuItem.setItemCategory(categoryName);
        menuItem.setCuisineType(cuisineType);
        menuItem.setFoodType(foodType);
        menuItem.setSpiceLevel(spiceLevel);
        menuItem.setSweet(isSweet);

        return menuItem;
    }

    public MenuItem createMenuItemWithAvailabilityStatus() {
        String itemName = InputHandler.getStringInput("Enter the Food Item name: ");
        boolean isItemAvailable = InputHandler.getBooleanInput(itemName + " is Available: Enter 'no' for not available, 'yes' for available");

        MenuItem menuItem = new MenuItem();
        menuItem.setItemName(itemName);
        menuItem.setItemAvailable(isItemAvailable);

        return menuItem;
    }

    public MenuItem gatherDeletionDetails() {
        String itemName = InputHandler.getStringInput("Enter item name: ");
        boolean deleteFromAllCategory = InputHandler.getBooleanInput(itemName + " delete the item from all categories? Enter 'no' to delete from specific category, 'yes' to delete from all categories");

        char category = deleteFromAllCategory ? 'a' : getCategoryInput();
        String categoryName = getCategoryName(category);

        MenuItem menuItem = new MenuItem(itemName, categoryName);
        return menuItem;
    }

    private char getCategoryInput() {
        char category;
        do {
            category = Character.toLowerCase(InputHandler.getCharInput("Enter Food Category: \nb - breakfast\nl - lunch\ns - snacks\nd - dinner\n"));
        } while (!isValidCategory(category));
        return category;
    }

    private String getFoodTypeInput() {
        Integer foodTypeInput;
        do {
            foodTypeInput = InputHandler.getIntegerInput("Enter dietary preference (1 - Vegetarian, 2 - Non-Vegetarian, 3 - Eggetarian): ");
        } while (!isValidFoodType(foodTypeInput));

        return mapFoodType(foodTypeInput);
    }

    private String getSpiceLevelInput() {
        Integer spiceInput;
        do {
            spiceInput = InputHandler.getIntegerInput("Enter preferred spice level (1 - Low, 2 - Medium, 3 - High): ");
        } while (!isValidSpiceLevel(spiceInput));

        return mapSpiceLevel(spiceInput);
    }

    private String getCuisineTypeInput() {
        Integer cuisineInput;
        do {
            cuisineInput = InputHandler.getIntegerInput("Enter preferred cuisine (1 - North Indian, 2 - South Indian, 3 - Other): ");
        } while (!isValidCuisine(cuisineInput));

        return mapCuisine(cuisineInput);
    }

    private String getCategoryNameInput() {
        char category = getCategoryInput();
        return getCategoryName(category);
    }

    private boolean isValidCategory(char category) {
        return category == 'b' || category == 'l' || category == 's' || category == 'd' || category == 'a';
    }

    private boolean isValidFoodType(Integer foodTypeInput) {
        return foodTypeInput != null && (foodTypeInput == 1 || foodTypeInput == 2 || foodTypeInput == 3);
    }

    private boolean isValidSpiceLevel(Integer spiceInput) {
        return spiceInput != null && (spiceInput == 1 || spiceInput == 2 || spiceInput == 3);
    }

    private boolean isValidCuisine(Integer cuisineInput) {
        return cuisineInput != null && (cuisineInput == 1 || cuisineInput == 2 || cuisineInput == 3);
    }

    private String getCategoryName(char category) {
        switch (category) {
            case 'a': return "all";
            case 'b': return "breakfast";
            case 'l': return "lunch";
            case 's': return "snacks";
            case 'd': return "dinner";
            default: return "unknown";
        }
    }

    private String mapFoodType(int dietaryInput) {
        switch (dietaryInput) {
            case 1: return "Vegetarian";
            case 2: return "Non-Vegetarian";
            case 3: return "Eggetarian";
            default: return "unknown";
        }
    }

    private String mapSpiceLevel(int spiceInput) {
        switch (spiceInput) {
            case 1: return "Low";
            case 2: return "Medium";
            case 3: return "High";
            default: return "unknown";
        }
    }

    private String mapCuisine(int cuisineInput) {
        switch (cuisineInput) {
            case 1: return "North Indian";
            case 2: return "South Indian";
            case 3: return "Other";
            default: return "unknown";
        }
    }
}
