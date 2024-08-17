package client.service;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import client.Client;
import RequestGateway.RolloutRequestGateway;
import client.model.ChefMenuRollout;
import client.model.TodayMenu;
import client.util.InputHandler;
import client.util.PrintOutToConsole;
import server.model.FoodCategory;

public class RolloutHandler {
    private RolloutRequestGateway requestGateway;

    public RolloutHandler(String role) {
        this.requestGateway = new RolloutRequestGateway(role);
    }

    public void recommendation() {
        try {
            String categoryName = InputHandler.getStringInput("Please enter category name: ");
            int numberOfItems = InputHandler.getIntegerInput("Enter number of items required: ");

            FoodCategory category = new FoodCategory();
            category.setCategoryName(categoryName);
            category.setNumberOfItems(numberOfItems);

            String jsonRequest = requestGateway.createRecommendationRequest(category);
            String jsonResponse = Client.requestServer(jsonRequest);

            PrintOutToConsole.printToConsole(jsonResponse);
        } catch (IOException e) {
            System.err.println("Error processing recommendation request: " + e.getMessage());
        }
    }

    public void getFinalVoteMenu() {
        try {
            String jsonRequest = requestGateway.createFinalVoteMenuRequest();
            String jsonResponse = Client.requestServer(jsonRequest);

            System.out.println("Final Vote Menu:");
            PrintOutToConsole.printToConsole(jsonResponse);
        } catch (IOException e) {
            System.err.println("Error fetching final vote menu: " + e.getMessage());
        }
    }

    public void getFinalDecidedMenu() {
        try {
            String jsonRequest = requestGateway.createFinalDecidedMenuRequest();
            String jsonResponse = Client.requestServer(jsonRequest);

            System.out.println("Today's Menu:");
            PrintOutToConsole.printToConsole(jsonResponse);
        } catch (IOException e) {
            System.err.println("Error fetching final decided menu: " + e.getMessage());
        }
    }

    public void todaysMenu() {
        try {
            String jsonRequest = requestGateway.createTodaysMenuRequest();
            String jsonResponse = Client.requestServer(jsonRequest);

            System.out.println("Today's Menu:");
            PrintOutToConsole.printToConsole(jsonResponse);
        } catch (IOException e) {
            System.err.println("Error fetching today's menu: " + e.getMessage());
        }
    }

    public void createChefMenuRollouts(String mealType) {
        try {
            int itemCount = InputHandler.getIntegerInput("Enter the number of " + mealType + " food items to add: ");
            for (int count = 0; count < itemCount; count++) {
                ChefMenuRollout chefMenuRollout = new ChefMenuRollout();
                chefMenuRollout.setCategoryName(mealType);
                String itemName = InputHandler.getStringInput("Item Name: ");
                chefMenuRollout.setItemName(itemName);
                chefMenuRollout.setRolloutDate(new Date(System.currentTimeMillis()));

                String jsonRequest = requestGateway.createChefMenuRolloutRequest(chefMenuRollout);
                String jsonResponse = Client.requestServer(jsonRequest);

                PrintOutToConsole.printToConsole(jsonResponse);
            }
        } catch (IOException e) {
            System.err.println("Error creating chef menu rollouts: " + e.getMessage());
        }
    }

    public void createAllChefMenuRollouts() {
        createChefMenuRollouts("breakfast");
        createChefMenuRollouts("lunch");
        createChefMenuRollouts("snacks");
        createChefMenuRollouts("dinner");
    }

    public void createFinalDecidedMenuAfterRollout(String mealType) {
        try {
            int itemCount = InputHandler.getIntegerInput("Enter the number of " + mealType + " food items to add: ");
            LocalDate nextDay = LocalDate.now().plusDays(1);
            Date nextDayDate = Date.valueOf(nextDay);

            for (int count = 0; count < itemCount; count++) {
                TodayMenu todayMenu = new TodayMenu();
                todayMenu.setCategoryName(mealType);
                String itemName = InputHandler.getStringInput("Item Name: ");
                todayMenu.setItemName(itemName);
                todayMenu.setMenuDate(nextDayDate);

                String jsonRequest = requestGateway.createFinalDecidedMenuAfterRolloutRequest(todayMenu);
                String jsonResponse = Client.requestServer(jsonRequest);

                PrintOutToConsole.printToConsole(jsonResponse);
            }
        } catch (IOException e) {
            System.err.println("Error creating final decided menu: " + e.getMessage());
        }
    }

    public void createAllFinalDecidedChefMenu() {
        createFinalDecidedMenuAfterRollout("breakfast");
        createFinalDecidedMenuAfterRollout("lunch");
        createFinalDecidedMenuAfterRollout("snacks");
        createFinalDecidedMenuAfterRollout("dinner");
    }
}
