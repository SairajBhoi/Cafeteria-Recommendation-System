package client.service;

import java.io.IOException;

import client.Client;
import client.model.Feedback;
import client.util.InputHandler;
import client.util.JsonConverter;

public class FeedbackHandler {
    private Feedback feedback;
    private String requestPath;
    private String role;
    private String employeeName;
    private String employeeId;

    public FeedbackHandler(String role,String employeeName,String employeeID) {
        feedback = new Feedback();
        feedback.setEmployeeId(employeeID);
        feedback.setEmployeeName(employeeName);
        this.role = role;
        this.employeeName=employeeName;
        this.employeeId=employeeID;
        this.requestPath = "/" + role;
    }
    public FeedbackHandler(String role) {
    	
    	this.role=role;
    }
    
    
    

    public void addFeedbackOnFoodItem() throws IOException {
 

        String itemName=null;
		try {
			itemName = InputHandler.getStringInput("Enter the Food Item name: ");
		} catch (IOException e) {
			e.printStackTrace();
		}
        int tasteRating = 0, qualityRating = 0, freshnessRating=0, valueForMoneyRating=0;

        do {
            try {
				tasteRating = InputHandler.getIntegerInput("Enter the Taste rating of " + itemName + " (0-5): ");
			} catch (IOException e) {
				e.printStackTrace();
			}
        } while (!isValidRating(tasteRating));

        do {
            try {
				qualityRating =  InputHandler.getIntegerInput("Enter the Quality rating of " + itemName + " (0-5): ");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } while (!isValidRating(qualityRating));

        do {
            try {
				freshnessRating =  InputHandler.getIntegerInput("Enter the Freshness rating of " + itemName + " (0-5): ");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } while (!isValidRating(freshnessRating));

        do {
            valueForMoneyRating =  InputHandler.getIntegerInput("Enter the Value for Money rating of " + itemName + " (0-5): ");
        } while (!isValidRating(valueForMoneyRating));

        String feedbackMessage = InputHandler.getStringInput("Enter feedback message: ");

        this.requestPath = this.requestPath + "/addFeedbackOnFoodItem"; 

        feedback.setEmployeeName(this.employeeName);
        feedback.setItemName(itemName);
        feedback.setTasteRating(tasteRating);
        feedback.setQualityRating(qualityRating);
        feedback.setFreshnessRating(freshnessRating);
        feedback.setValueForMoneyRating(valueForMoneyRating);
        feedback.setFeedbackMessage(feedbackMessage);

        String jsonRequest = JsonConverter.convertObjectToJson(this.requestPath,feedback );
        this.requestPath = "/" + this.role;
        
        String jsonResponse=Client.requestServer(jsonRequest);
        System.out.print(jsonResponse);

//        System.out.println("Feedback added successfully!");
//        System.out.println("Feedback details:");
//        System.out.println("Item Name: " + feedback.getItemName());
//        System.out.println("Taste Rating: " + feedback.getTasteRating());
//        System.out.println("Quality Rating: " + feedback.getQualityRating());
//        System.out.println("Freshness Rating: " + feedback.getFreshnessRating());
//        System.out.println("Value for Money Rating: " + feedback.getValueForMoneyRating());
//        System.out.println("Feedback Message: " + feedback.getFeedbackMessage());
    }

    private boolean isValidRating(int rating) {
        return rating >= 0 && rating <= 5;
    }


   public void viewFeedbackonFoodItem() {
	   String itemName = null;
    try {
		 itemName = InputHandler.getStringInput("Enter the Food Item name: ");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    this.requestPath = "/viewFeedbackonFoodItem"; 
      feedback.setItemName(itemName);
    String jsonRequest = JsonConverter.convertObjectToJson(this.requestPath,feedback);
  
    String jsonRespose = null;
	try {
		jsonRespose = Client.requestServer(jsonRequest);
	} catch (IOException e) {
		e.printStackTrace();
	}

    this.requestPath = "/" + this.role;
    
    System.out.print(jsonRespose);


   }
  
}