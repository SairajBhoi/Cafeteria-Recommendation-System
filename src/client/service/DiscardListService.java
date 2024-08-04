package client.service;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import client.Client;
import client.model.DiscardedFoodItem;
import client.model.UserDiscardedFeedback;
import client.util.InputHandler;
import client.util.JsonConverter;
import client.util.PrintOutToConsole;

public class DiscardListService {
    private String requestPath;
    private String role;

    public DiscardListService(String role) {
        this.role = role;
        this.requestPath = "/" + role;
    }

    public void viewChefDiscardList() {
        try {
        	
            String fullPath = "/viewChefDiscardedList";
            String jsonRequest = JsonConverter.convertObjectToJson(fullPath, null);
          //  System.out.println("JSON Request: " + jsonRequest);

            String jsonResponse = Client.requestServer(jsonRequest);
            System.out.println("Discarded Menu Item:");
            
            PrintOutToConsole.printToConsole(jsonResponse);
        } catch (IOException e) {
            System.err.println("Error viewing Discarded menu items: " + e.getMessage());
        } finally {
            this.requestPath = "/" + this.role;
        }
    }

    public void generateDiscardList() {
        try {
            String fullPath = "/viewDiscardedList";
            String jsonRequest = JsonConverter.convertObjectToJson(fullPath, null);
          //  System.out.println("JSON Request: " + jsonRequest);

            String jsonResponse = Client.requestServer(jsonRequest);

            ObjectMapper objectMapper = new ObjectMapper();
            List<DiscardedFoodItem> discardedFoodItemList = objectMapper.readValue(jsonResponse, new TypeReference<List<DiscardedFoodItem>>() {});

            System.out.println("Discarded Food Items:");
            PrintOutToConsole.printToConsole(jsonResponse);

            if (discardedFoodItemList != null && !discardedFoodItemList.isEmpty()) {
                for (DiscardedFoodItem discardedFoodItem : discardedFoodItemList) {
                    Boolean decision = getUserDecision("Enter whether you want to add " + discardedFoodItem.getItemName() + " in Discarded Table for User Feedback");

                    if (decision == null) {
                        System.out.println("Operation terminated by user.");
                        break; 
                    }

                    if (decision) {
                        processDiscardedFoodItem(discardedFoodItem, "addChefDiscardedFoodItem");
                    } else {
                        Boolean deleteDecision = getUserDecision("Delete " + discardedFoodItem.getItemName() + " from main menu");

                        if (deleteDecision == null) {
                            System.out.println("Operation terminated by user.");
                            break; 
                        }

                        if (deleteDecision) {
                            processDiscardedFoodItem(discardedFoodItem, "deleteChefDiscardedFoodItem");
                        }
                    }
                }
            } else {
                System.out.println("No discarded food items available.");
            }
        } catch (IOException e) {
            System.out.println("Server connection failed.");
            e.printStackTrace();
        } finally {
            resetRequestPath();
        }
    }

    private Boolean getUserDecision(String message) {
        while (true) {
            String input = InputHandler.getStringInput(message + " (yes/no/q/quit): ");
            switch (input.toLowerCase()) {
                case "yes":
                case "y":
                    return true;
                case "no":
                case "n":
                    return false;
                case "q":
                case "quit":
                    return null; 
                default:
                    System.out.println("Invalid input. Please enter 'yes', 'no', 'q', or 'quit'.");
            }
        }
    }

    private void processDiscardedFoodItem(DiscardedFoodItem discardedFoodItem, String actionPath) {
        JSONObject discardedFoodItemJson = new JSONObject();
        discardedFoodItemJson.put("discardID", discardedFoodItem.getDiscardID());
        discardedFoodItemJson.put("itemID", discardedFoodItem.getItemID());
        discardedFoodItemJson.put("itemName", discardedFoodItem.getItemName());
        discardedFoodItemJson.put("averageRating", discardedFoodItem.getAverageRating());
        discardedFoodItemJson.put("positivePercentage", discardedFoodItem.getPositivePercentage());
        discardedFoodItemJson.put("negativePercentage", discardedFoodItem.getNegativePercentage());
        discardedFoodItemJson.put("discardedDate", discardedFoodItem.getDiscardedDate());

      //  JSONObject requestJson = new JSONObject();
        
        String path = "/" + this.role + "/" + actionPath;
       // System.out.println("=============path =="+path);
        
        //requestJson.put("path",path);
       // requestJson.put("data", discardedFoodItemJson);
 
       // String jsonpath = JsonConverter.createJson("path", path);
        
        String jsonrequset =JsonConverter.convertObjectToJson(path,discardedFoodItemJson);
        
       // System.out.println("=================================== jsonrequset " + jsonrequset);
       
        //System.out.println("==================================="+requestJson.toString());
        String response = null;
		try {
			response = Client.requestServer(jsonrequset);
		} catch (IOException e) {
			e.printStackTrace();
		}
        PrintOutToConsole.printToConsole(response);
    }

    private void resetRequestPath() {
        this.requestPath = "/" + this.role;
    }
    
    
    
    public void viewFeedbackOnDiscardList(){
    	try {
    		
    	  String fullPath = "/viewFeedbackOnDiscardFoodItem";
    	  
    	  UserDiscardedFeedback feedback = new UserDiscardedFeedback();
    	  
    	 int discardID = InputHandler.getIntegerInput("Enter the Discard ID: ");
          feedback.setDiscardID(discardID);
          String jsonRequest = JsonConverter.convertObjectToJson(fullPath, feedback);
         // System.out.println("JSON Request: " + jsonRequest);

          String jsonResponse = Client.requestServer(jsonRequest);
          PrintOutToConsole.printToConsole(jsonResponse);
      } catch (IOException e) {
          System.out.println("Server connection failed.");
          e.printStackTrace();
      } finally {
          resetRequestPath();
      }
    }
  
    
    
    
    
    
    
    
    
    
    
    
    
}
