package client.service;

import java.io.IOException;


import java.util.ArrayList;
import java.util.List;

import client.Client;
import client.model.MenuItem;
import client.util.InputHandler;
import client.util.JsonConverter;
import client.util.PrintOutToConsole;

public class Menu {
    private MenuItem menuItem;
   
    private String requestPath;
    private String role ;

    public Menu(String role) {
    
        this.role= role;
        this.requestPath = "/"+role; 
    }

    
    
    
    
    @SuppressWarnings({ "static-access", "static-access" })
	public void addMenuItem() {
        menuItem = new MenuItem();
        String itemName = null;
		try {
			itemName = InputHandler.getStringInput("Enter the Food Item name: ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("input string handler error");
		}
        float itemPrice = 0;
		try {
			itemPrice = InputHandler.getFloatInput("Enter the Food Item price for " + itemName + ": ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("input float handler error");
		}
        boolean isItemAvailable = false;
		try {
			isItemAvailable = InputHandler.getBooleanInput(itemName + " is Available: Enter 'no' for not available, 'yes' for available");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("input boolean handler error");
		}
        this.requestPath = this.requestPath + "/addMenuItem";

        char category;
        do {
            category = Character.toLowerCase(InputHandler.getCharInput("Enter Food Category: \nb - breakfast\nl - lunch\ns - snacks\nd - dinner\n"));
        } while (!isValidCategory(category));
        
        String categoryName=this.getCategoryName(category);
        menuItem.setItemName(itemName);
        menuItem.setItemPrice(itemPrice);
        menuItem.setItemAvailable(isItemAvailable);
        menuItem.setItemCategory(categoryName);
        
        String jsonRequest = JsonConverter.convertObjectToJson(this.requestPath,menuItem); 
        System.out.println("JSON Request: " + jsonRequest); 
        this.requestPath = "/"+ this.role; 
        
        String jsonResponse=null;
        try {
			jsonResponse = Client.requestServer(jsonRequest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       System.out.println(jsonResponse);
       PrintOutToConsole.printToConsole(jsonResponse);
       
       
    }

    private boolean isValidCategory(char category) {
        return category == 'b' || category == 'l' || category == 's' || category == 'd';
    }
    
    
    
    
    

    public void updateMenuItem() {
    	menuItem = new MenuItem();
        String itemName = null;
		try {
			itemName = InputHandler.getStringInput("Enter the Food Item name: ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("input string handler error");
		}
        float itemPrice = 0;
		try {
			itemPrice = InputHandler.getFloatInput("Enter the Food Item price for " + itemName + ": ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("input float handler error");
		}
        boolean isItemAvailable = false;
		try {
			isItemAvailable = InputHandler.getBooleanInput(itemName + " is Available: Enter 'no' for not available, 'yes' for available");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("input boolean handler error");
		}
        this.requestPath = this.requestPath + "/updateMenuItem";

        char category;
        do {
            category = Character.toLowerCase(InputHandler.getCharInput("Enter Food Category: \nb - breakfast\nl - lunch\ns - snacks\nd - dinner\n"));
        } while (!isValidCategory(category));
        
        String categoryName=this.getCategoryName(category);
        menuItem.setItemName(itemName);
        menuItem.setItemPrice(itemPrice);
        menuItem.setItemAvailable(isItemAvailable);
        menuItem.setItemCategory(categoryName);
        
        String jsonRequest = JsonConverter.convertObjectToJson(this.requestPath,menuItem); 
        System.out.println("JSON Request: " + jsonRequest); 
        this.requestPath = "/"+ this.role; 
        
        String jsonResponse=null;
        try {
			jsonResponse = Client.requestServer(jsonRequest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       System.out.println(jsonResponse);
       PrintOutToConsole.printToConsole(jsonResponse);
       
    }
    
    
    public void updateAvailabilityStatus() {
    	menuItem = new MenuItem();
        String itemName = null;
		try {
			itemName = InputHandler.getStringInput("Enter the Food Item name: ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("input string handler error");
		}
       
        boolean isItemAvailable = false;
		try {
			isItemAvailable = InputHandler.getBooleanInput(itemName + " is Available: Enter 'no' for not available, 'yes' for available");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("input boolean handler error");
		}
		
		
		 menuItem.setItemName(itemName);
		 menuItem.setItemAvailable(isItemAvailable);
		 
		
		
        this.requestPath = this.requestPath + "/updateFoodAvailableStatus";
        String jsonRequest = JsonConverter.convertObjectToJson(this.requestPath, menuItem); 
        System.out.println("JSON Request: " + jsonRequest); 
        this.requestPath = "/"+ this.role; 
        
        String jsonResponse=null;
        try {
			jsonResponse = Client.requestServer(jsonRequest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       System.out.println(jsonResponse);
       PrintOutToConsole.printToConsole(jsonResponse);
       
    }

    
    
    
    
    
    public void deleteMenuItem() {
        String itemName = null;
		try {
			itemName = InputHandler.getStringInput("\nEnter item name: ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("input string handler error");
		}
        this.requestPath = this.requestPath + "/deleteMenuItem";
        
       char category = 'a'; 
        boolean deleteFromAllCategory = false;
		try {
			deleteFromAllCategory = InputHandler.getBooleanInput(itemName + " delete the item from all categories? Enter 'no' to delete from specific category, 'yes' to delete from all categories");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("input boolean handler error");
		}
    
        if (!deleteFromAllCategory) {
            do {
                category = Character.toLowerCase(InputHandler.getCharInput("Enter Food Category: \nb - breakfast\nl - lunch\ns - snacks\nd - dinner\n"));
            } while (!isValidCategory(category));
        }
        String categoryName=this.getCategoryName(category);
        
        MenuItem menuItem = new MenuItem(itemName, categoryName); 
        String jsonRequest = JsonConverter.convertObjectToJson(this.requestPath,menuItem); 
        System.out.println("JSON Request: " + jsonRequest); 
        this.requestPath = "/"+this.role;  
        String jsonResponse=null;
        try {
			jsonResponse = Client.requestServer(jsonRequest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.println(jsonResponse);
        PrintOutToConsole.printToConsole(jsonResponse);
    }
          

    
    
    
    public void viewAllMenuItems() {
        this.requestPath = "/viewAllMenuItems";
        String jsonRequest = JsonConverter.convertObjectToJson(this.requestPath,null); 
        System.out.println("JSON Request: " + jsonRequest);
        this.requestPath = "/"+this.role;
        String jsonResponse=null;
        try {
			jsonResponse = Client.requestServer(jsonRequest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.println("Menu Item");
        PrintOutToConsole.printToConsole(jsonResponse);
       
       
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
}
