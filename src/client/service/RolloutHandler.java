package client.service;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import client.RequestGateway.RolloutRequestGateway;

import client.model.ChefMenuRollout;
import client.model.TodayMenu;
import client.util.InputHandler;

import client.util.RequestHandler;
import server.model.FoodCategory;

public class RolloutHandler {
    private final RolloutRequestGateway requestGateway;
    private final RequestHandler requestHandler;

    public RolloutHandler(String role) {
        this.requestGateway = new RolloutRequestGateway(role);
        this.requestHandler = new RequestHandler();
    }

    public RolloutHandler() {
        this.requestGateway = new RolloutRequestGateway(null);
        this.requestHandler = new RequestHandler();
    }
    public void recommendation() {
        FoodCategory category = new FoodCategory();
        category.setCategoryName(InputHandler.getStringInput("Please enter category name: "));
        category.setNumberOfItems(InputHandler.getIntegerInput("Enter number of items required: "));

        String jsonRequest = requestGateway.createRecommendationRequest(category);
        requestHandler.sendRequestToServer(jsonRequest);
    }

    public void getFinalVoteMenu() {
        String jsonRequest = requestGateway.createFinalVoteMenuRequest();
        requestHandler.sendRequestToServer(jsonRequest);
    }

    public void getFinalDecidedMenu() {
        String jsonRequest = requestGateway.createFinalDecidedMenuRequest();
        requestHandler.sendRequestToServer(jsonRequest);
    }

    public void todaysMenu() {
        String jsonRequest = requestGateway.createTodaysMenuRequest();
        requestHandler.sendRequestToServer(jsonRequest);
    }

    public void createChefMenuRollouts(String mealType) {
        try {
            processMenuItems(mealType, new MenuItemProcessor() {
                @Override
                public void process() throws IOException {
                    ChefMenuRollout chefMenuRollout = new ChefMenuRollout();
                    chefMenuRollout.setCategoryName(mealType);
                    chefMenuRollout.setItemName(InputHandler.getStringInput("Item Name: "));
                    chefMenuRollout.setRolloutDate(new Date(System.currentTimeMillis()));

                    String jsonRequest = requestGateway.createChefMenuRolloutRequest(chefMenuRollout);
                    requestHandler.sendRequestToServer(jsonRequest);
                }
            });
        } catch (IOException e) {
            handleException("Error creating chef menu rollouts", e);
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
            processMenuItems(mealType, new MenuItemProcessor() {
                @Override
                public void process() throws IOException {
                    TodayMenu todayMenu = new TodayMenu();
                    todayMenu.setCategoryName(mealType);
                    todayMenu.setItemName(InputHandler.getStringInput("Item Name: "));
                    todayMenu.setMenuDate(Date.valueOf(LocalDate.now().plusDays(1)));

                    String jsonRequest = requestGateway.createFinalDecidedMenuAfterRolloutRequest(todayMenu);
                    requestHandler.sendRequestToServer(jsonRequest);
                }
            });
        } catch (IOException e) {
            handleException("Error creating final decided menu", e);
        }
    }

    public void createAllFinalDecidedChefMenu() {
        createFinalDecidedMenuAfterRollout("breakfast");
        createFinalDecidedMenuAfterRollout("lunch");
        createFinalDecidedMenuAfterRollout("snacks");
        createFinalDecidedMenuAfterRollout("dinner");
    }

    private void processMenuItems(String mealType, MenuItemProcessor processor) throws IOException {
        int itemCount = InputHandler.getIntegerInput("Enter the number of " + mealType + " food items to add: ");
        for (int count = 0; count < itemCount; count++) {
            processor.process();
        }
    }

    private void handleException(String message, IOException e) {
        System.err.println(message + ": " + e.getMessage());
    }

    private interface MenuItemProcessor {
        void process() throws IOException;
    }
}
