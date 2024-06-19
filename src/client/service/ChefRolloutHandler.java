package client.service;

import java.io.IOException;
import java.sql.Date;

import client.Client;
import client.model.ChefMenuRollout;
import client.util.InputHandler;
import client.util.JsonConverter;

public class ChefRolloutHandler {
    private String requestPath;
    private String role;

    public ChefRolloutHandler(String role) {
        this.requestPath = "/" + role;
        this.role = role;
    }
    
    
    public void recommendation() {
    	
    	this.requestPath=	this.requestPath + "/recommendation";
    	
    	String jsonRequest=JsonConverter.convertObjectToJson(this.requestPath, null);
    	String jsonResponse = null;
    	 try {
			 jsonResponse = Client.requestServer(jsonRequest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 this.requestPath = "/" + role;
    	 System.out.println("recommendation system");
    	System.out.println(jsonResponse);
    	
    }
    
    
    public void getFinalMenu() {
    	this.requestPath=	this.requestPath + "/finalReport";
    	String jsonRequest=JsonConverter.convertObjectToJson(this.requestPath, null);
    	String jsonResponse = null;
    	 try {
			 jsonResponse = Client.requestServer(jsonRequest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 this.requestPath = "/" + role;
    	 System.out.println("recommendation system");
    	System.out.println(jsonResponse);
    	
    }
    	
    	


    public void createChefMenuRollouts(String mealType) {
        int itemCount = 0;
		try {
			itemCount = InputHandler.getIntegerInput("Enter the number of " + mealType + " Food items to add");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        for (int count = 0; count < itemCount; count++) {
            ChefMenuRollout chefMenuRollout = new ChefMenuRollout();

            chefMenuRollout.setCategoryName(mealType);
            String itemName = null;
            try {
                itemName = InputHandler.getStringInput("Item Name: ");
            } catch (IOException e) {
                e.printStackTrace();
            }

            chefMenuRollout.setItemName(itemName);

            long currentTimeMillis = System.currentTimeMillis();
            Date currentDate = new Date(currentTimeMillis);

            System.out.println("Current Date: " + currentDate);

            chefMenuRollout.setRolloutDate(currentDate);

            String currentRequestPath = this.requestPath + "/rolloutMenu"; 
            String jsonRequest = JsonConverter.convertObjectToJson(currentRequestPath, chefMenuRollout);
            System.out.println("JSON Request: " + jsonRequest);

            String jsonResponse = null;
            this.requestPath =  "/" + this.role;
            try {
                jsonResponse = Client.requestServer(jsonRequest);
                System.out.println("JSON Response: " + jsonResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createAllChefMenuRollouts() {
        createChefMenuRollouts("breakfast");
        createChefMenuRollouts("lunch");
        createChefMenuRollouts("snacks");
        createChefMenuRollouts("dinner");
    }
}