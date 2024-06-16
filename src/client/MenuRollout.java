package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuRollout {
    private DailyMenuItem dailyMenuItem;
    private InputHandler inputHandler;
    private JsonConverter jsonConverter;
    private String requestPath;
	private String role;

    public MenuRollout(String role) {
        this.dailyMenuItem = new DailyMenuItem();
        this.inputHandler = new InputHandler();
        this.jsonConverter = new JsonConverter();
        this.requestPath = "/" + role;
    }

       private void collectMenuItems() throws IOException {
        List<String> breakfastItems = collectItemsForMeal("breakfast");
        List<String> lunchItems = collectItemsForMeal("lunch");
        List<String> snackItems = collectItemsForMeal("snack");
        List<String> dinnerItems = collectItemsForMeal("dinner");

        dailyMenuItem.setBreakfastMenu(breakfastItems); 
        dailyMenuItem.setLunchMenu(lunchItems);
        dailyMenuItem.setSnackMenu(snackItems);
        dailyMenuItem.setDinnerMenu(dinnerItems);
    }

    private List<String> collectItemsForMeal(String mealType) throws IOException {
        int numItems = inputHandler.getIntegerInput("Enter number of " + mealType + " items to add: ");
        return collectItemNamesForMeal(mealType, numItems);
    }

    private List<String> collectItemNamesForMeal(String mealType, int totalNumberOfItems) throws IOException {
        List<String> items = new ArrayList<>();
        System.out.println("Enter " + totalNumberOfItems + " " + mealType + " items:");
        for (int itemCount = 0; itemCount < totalNumberOfItems; itemCount++) {
            String itemName = inputHandler.getStringInput(mealType + " item " + (itemCount + 1) + ": ");
            items.add(itemName);
        }
        return items;
    }

    public void sendMenuRollout() {
        try {
        	this.requestPath = "/" + role + "/rolloutMenu";
            String jsonRequest = jsonConverter.convertObjectToJson(dailyMenuItem, this.requestPath);
            System.out.println("JSON Request: " + jsonRequest);

            String response = Client.requestServer(jsonRequest);
            System.out.println("Server Response: " + response);
        } catch (IOException e) {
            System.err.println("Failed to send menu rollout: " + e.getMessage());
        }
    }

    public void executeMenuRollout() {
        try {
            collectMenuItems();
            sendMenuRollout();
        } catch (IOException e) {
            System.err.println("Error during menu rollout: " + e.getMessage());
        } finally {
            this.requestPath = "/" +this.role;
        }
    }
}
