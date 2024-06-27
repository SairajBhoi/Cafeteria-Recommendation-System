package client.service;

import java.io.IOException;
import client.util.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

import client.Client;
import client.model.ChefMenuRollout;
import client.model.TodayMenu;
import client.util.InputHandler;
import client.util.JsonConverter;
import client.util.PrintOutToConsole;
import server.model.FoodCategory;

public class RolloutHandler {
    private String requestPath;
    private String role;

    public RolloutHandler(String role) {
        this.requestPath = "/" + role;
        this.role = role;
    }
    
    
    public void recommendation()  {
    	
    	this.requestPath=	this.requestPath + "/recommendation";
    	String categoryName = null;
		try {
			categoryName = InputHandler.getStringInput("please Enter Category Name");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	int numberOfItem = 0;
		try {
			numberOfItem = InputHandler.getIntegerInput("Enter number of items required");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	FoodCategory  category = new FoodCategory();
    	category.setCategoryName(categoryName); 
    	category.setNumberOfItems(numberOfItem);
   
    	
    	String jsonRequest=JsonConverter.convertObjectToJson(this.requestPath, category);
    	String jsonResponse = null;
    	 try {
			 jsonResponse = Client.requestServer(jsonRequest);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    	 this.requestPath = "/" + role;
    	
    	 PrintOutToConsole.printToConsole(jsonResponse);
    	
    }
    
    
    public void getFinalVoteMenu() {
    	this.requestPath=	this.requestPath + "/finalVoteMenu";
    	String jsonRequest=JsonConverter.convertObjectToJson(this.requestPath, null);
    	String jsonResponse = null;
    	 try {
			 jsonResponse = Client.requestServer(jsonRequest);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	 this.requestPath = "/" + role;
    	 System.out.println("recommendation system");
    	System.out.println(jsonResponse);
    	
    	 PrintOutToConsole.printToConsole(jsonResponse);
    }
    
    
    public void getFinalDecidedMenu() {
    	this.requestPath= "/finalDecidedMenuAfterRollout";
    	String jsonRequest=JsonConverter.convertObjectToJson(this.requestPath, null);
    	String jsonResponse = null;
    	 try {
			 jsonResponse = Client.requestServer(jsonRequest);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	 this.requestPath = "/" + role;
    	 System.out.println("Todays Menu");
    	System.out.println(jsonResponse);
    	 PrintOutToConsole.printToConsole(jsonResponse);
    	
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
    
    
    
    
    
    public void createfinalDecidedMenuAfterRollout(String mealType) {
        int itemCount = 0;
		try {
			itemCount = InputHandler.getIntegerInput("Enter the number of " + mealType + " Food items to add");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("--");
        for (int count = 0; count < itemCount; count++) {
            TodayMenu todayMenu = new TodayMenu();

            todayMenu.setCategoryName(mealType);
            String itemName = null;
            try {
                itemName = InputHandler.getStringInput("Item Name: ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        
            todayMenu.setItemName(itemName);
               
            
            System.out.println(todayMenu.getItemName());
       
            LocalDate currentDate = LocalDate.now();
            System.out.println("Current Date: " + currentDate);

            LocalDate nextDay = currentDate.plusDays(1);
            System.out.println("-----------------------");

            // Convert LocalDate to java.sql.Date
            Date nextDayDate = Date.valueOf(nextDay);

            todayMenu.setMenuDate(nextDayDate);
            System.out.println("-----------------------");

            todayMenu.setMenuDate(nextDayDate);
            System.out.println(nextDay);
            
            String currentRequestPath = this.requestPath + "/CHEF/addfinalResultMenu"; 
            String jsonRequest = JsonConverter.convertObjectToJson(currentRequestPath, todayMenu);
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

    public void createAllFinalDecidedChefMenu() {
    	createfinalDecidedMenuAfterRollout("breakfast");
    	System.out.println("--");
    	createfinalDecidedMenuAfterRollout("lunch");
    	createfinalDecidedMenuAfterRollout("snacks");
    	createfinalDecidedMenuAfterRollout("dinner");
    }
    
  
   
    }
    
    
  

