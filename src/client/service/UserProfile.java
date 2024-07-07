package client.service;

import java.io.IOException;

import client.Client;
import client.model.UserPreference;
import client.util.InputHandler;
import client.util.PrintOutToConsole;
import client.util.JsonConverter;

public class UserProfile {
	
	
	private String userID;
	private String userRole;
	private String requestPath;
	    
	    
	private UserPreference userPreference;
		
	public UserProfile(String userRole,String userID){
		this.userRole= userRole;
		this.userID= userID;
		this.requestPath="/"+this.userRole;
	}
	
	
	
	public void viewProfile() {
		
		
		userPreference = new UserPreference();
		userPreference.setUserID(this.userID);
		this.requestPath =  this.requestPath + "/viewUserProfile";
		
		
		String jsonRequest =JsonConverter.convertObjectToJson(this.requestPath, userPreference);
		System.out.println(jsonRequest);
		
		
		String jsonResponse=null;
		try {
			 jsonResponse =Client.requestServer(jsonRequest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resetRequestPath();
		
	   PrintOutToConsole.printToConsole(jsonResponse);
		
	}
	
	
	public void updateUserProfile() {
	    UserPreference userPreference = new UserPreference();
	    userPreference.setUserID(this.userID);

	    this.requestPath += "/updateUserProfile";

	   
	    int dietaryInput;
	    String dietaryPreference;
	    do {
	        dietaryInput = InputHandler.getIntegerInput("Enter dietary preference (1 - Vegetarian, 2 - Non-Vegetarian, 3 - Eggetarian): ");
	        dietaryPreference = mapDietaryPreference(dietaryInput);
	        if (dietaryPreference == null) {
	            System.out.println("Invalid input. Please enter a valid integer (1, 2, 3).");
	        }
	    } while (dietaryPreference == null);
	    userPreference.setDietaryPreference(dietaryPreference);

	   
	    int spiceInput;
	    String spiceLevel;
	    do {
	        spiceInput = InputHandler.getIntegerInput("Enter preferred spice level (1 - Low, 2 - Medium, 3 - High): ");
	        spiceLevel = mapSpiceLevel(spiceInput);
	        if (spiceLevel == null) {
	            System.out.println("Invalid input. Please enter a valid integer (1, 2, 3).");
	        }
	    } while (spiceLevel == null);
	    userPreference.setPreferredSpiceLevel(spiceLevel);

	   
	    int cuisineInput;
	    String cuisine;
	    do {
	        cuisineInput = InputHandler.getIntegerInput("Enter preferred cuisine (1 - North Indian, 2 - South Indian, 3 - Other): ");
	        cuisine = mapCuisine(cuisineInput);
	        if (cuisine == null) {
	            System.out.println("Invalid input. Please enter a valid integer (1, 2, 3).");
	        }
	    } while (cuisine == null);
	    userPreference.setPreferredCuisine(cuisine);

	   
	    userPreference.setHasSweetTooth(InputHandler.getBooleanInput("Do you have a sweet tooth?"));

	    String jsonRequest = JsonConverter.convertObjectToJson(this.requestPath, userPreference);
	    System.out.println("JSON Request: " + jsonRequest);

	    String jsonResponse = null;
	    try {
	        jsonResponse = Client.requestServer(jsonRequest);
	        System.out.println("jsonResponse:"+ jsonResponse);
	        
	        
	    } catch (IOException e) {
	        System.out.println("Error while communicating with server: " + e.getMessage());
	        return;
	    }

	    resetRequestPath();

	    PrintOutToConsole.printToConsole(jsonResponse);
	}

	
	private String mapDietaryPreference(int dietaryInput) {
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


	
	 private void resetRequestPath() {
	        this.requestPath = "/" + this.userRole;
	    }
	
}
