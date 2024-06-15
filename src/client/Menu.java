package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private MenuItem menuItem;
    private JsonConverter jsonConverter;
    private String requestPath;
    private String role ;

    public Menu(String role) {
        jsonConverter = new JsonConverter();
        this.role= role;
        this.requestPath = "/"+role; 
    }

    public void addMenuItem() {
        menuItem = new MenuItem();
        String itemName = null;
		try {
			itemName = InputHandler.getStringInput("Enter the Food Item name: ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        float itemPrice = 0;
		try {
			itemPrice = InputHandler.getFloatInput("Enter the Food Item price for " + itemName + ": ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        boolean isItemAvailable = false;
		try {
			isItemAvailable = InputHandler.getBooleanInput(itemName + " is Available: Enter 'no' for not available, 'yes' for available");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.requestPath = this.requestPath + "/addMenuItem";

        char category;
        do {
            category = Character.toLowerCase(InputHandler.getCharInput("Enter Food Category: \nb - breakfast\nl - lunch\ns - snacks\nd - dinner\n"));
        } while (!isValidCategory(category));

        menuItem.setItemName(itemName);
        menuItem.setItemPrice(itemPrice);
        menuItem.setItemAvailable(isItemAvailable);
        menuItem.setItemCategory(category);
        
        String jsonRequest = jsonConverter.convertObjectToJson(menuItem, this.requestPath); 
        System.out.println("JSON Request: " + jsonRequest); 
        this.requestPath = "/"+ this.role; 
    }

    private boolean isValidCategory(char category) {
        return category == 'b' || category == 'l' || category == 's' || category == 'd';
    }

    public void updateMenuItem() {
       this.addMenuItem();
    }

    public void deleteMenuItem() {
        String itemName = null;
		try {
			itemName = InputHandler.getStringInput("\nEnter item name: ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.requestPath = this.requestPath + "/deleteMenuItem/";
        
        char category = 'a'; 
        boolean deleteFromAllCategory = false;
		try {
			deleteFromAllCategory = InputHandler.getBooleanInput(itemName + " delete the item from all categories? Enter 'no' to delete from specific category, 'yes' to delete from all categories");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
        if (!deleteFromAllCategory) {
            do {
                category = Character.toLowerCase(InputHandler.getCharInput("Enter Food Category: \nb - breakfast\nl - lunch\ns - snacks\nd - dinner\n"));
            } while (!isValidCategory(category));
        }
        
        MenuItem menuItem = new MenuItem(itemName, category); 
        String jsonRequest = jsonConverter.convertObjectToJson(menuItem, this.requestPath); 
        System.out.println("JSON Request: " + jsonRequest); 
        this.requestPath = "/"+this.role;  
    }

    public void viewAllMenuItems() {
        this.requestPath = this.requestPath + "/viewAllMenuItems";
        String jsonRequest = jsonConverter.convertObjectToJson(null, this.requestPath); 
        System.out.println("JSON Request: " + jsonRequest);
        this.requestPath = "/"+this.role;

    }
}
