package client.service;

import java.io.IOException;
import client.Client;
import client.RequestGateway.MenuRequestGateway;
import client.model.MenuItem;
import client.util.PrintOutToConsole;
import client.util.MenuItemHandler;
import client.util.RequestHandler;

public class Menu {
    private MenuRequestGateway requestGateway;
    private MenuItemHandler menuItemHandler;
    private RequestHandler requestHandler;

    public Menu(String role) {
        this.requestGateway = new MenuRequestGateway(role);
        this.menuItemHandler = new MenuItemHandler();
        this.requestHandler = new RequestHandler();
    }
    public Menu() {
        this.requestHandler = new RequestHandler();
        this.requestGateway = new MenuRequestGateway();
        
    }

    public void addMenuItem() {
        MenuItem menuItem = menuItemHandler.createMenuItem(true);
        String jsonRequest = requestGateway.createAddMenuItemRequest(menuItem);
       String  jsonResponse = requestHandler.sendRequestToServer(jsonRequest);
        PrintOutToConsole.printToConsole(jsonResponse);
    }

    public void updateMenuItem() {
        MenuItem menuItem = menuItemHandler.createMenuItem(false);
        String jsonRequest = requestGateway.createUpdateMenuItemRequest(menuItem);
        String  jsonResponse = requestHandler.sendRequestToServer(jsonRequest);
        PrintOutToConsole.printToConsole(jsonResponse);
    }

    public void updateAvailabilityStatus() {
        MenuItem menuItem = menuItemHandler.createMenuItemWithAvailabilityStatus();
        String jsonRequest = requestGateway.createUpdateFoodAvailableStatusRequest(menuItem);
        String  jsonResponse = requestHandler.sendRequestToServer(jsonRequest);
        PrintOutToConsole.printToConsole(jsonResponse);
    }

    public void deleteMenuItem() {
        MenuItem menuItem = menuItemHandler.gatherDeletionDetails();
        String jsonRequest = requestGateway.createDeleteMenuItemRequest(menuItem);
        String  jsonResponse = requestHandler.sendRequestToServer(jsonRequest);
        PrintOutToConsole.printToConsole(jsonResponse);
    }

    public void viewAllMenuItems() {
        try {
            String jsonRequest = requestGateway.createViewAllMenuItemsRequest();
            String jsonResponse = Client.requestServer(jsonRequest);
            System.out.println("Menu Items:");
            PrintOutToConsole.printToConsole(jsonResponse);
        } catch (IOException e) {
            System.err.println("Error viewing all menu items: " + e.getMessage());
        }
    }
}
