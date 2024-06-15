package client;

import java.io.IOException;

public class FeedbackHandler {
    private Feedback feedback;
    private String requestPath;
    private JsonConverter jsonConverter;
    private String role;
    private String employeeName;

    public FeedbackHandler(String role,String employeeName) {
        jsonConverter = new JsonConverter();
        feedback = new Feedback();
        this.role = role;
        this.employeeName=employeeName;
        this.requestPath = "/" + role;
    }

    public void addFeedbackOnFoodItem() throws IOException {
 

        String itemName=null;
		try {
			itemName = InputHandler.getStringInput("Enter the Food Item name: ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int tasteRating = 0, qualityRating = 0, freshnessRating=0, valueForMoneyRating=0;

        do {
            try {
				tasteRating = InputHandler.getIntegerInput("Enter the Taste rating of " + itemName + " (0-5): ");
			} catch (IOException e) {
				// TODO Auto-generated catch block
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

        String jsonRequest = jsonConverter.convertObjectToJson(feedback, this.requestPath);
        this.requestPath = "/" + this.role;

        System.out.println("Feedback added successfully!");
        System.out.println("Feedback details:");
        System.out.println("Item Name: " + feedback.getItemName());
        System.out.println("Taste Rating: " + feedback.getTasteRating());
        System.out.println("Quality Rating: " + feedback.getQualityRating());
        System.out.println("Freshness Rating: " + feedback.getFreshnessRating());
        System.out.println("Value for Money Rating: " + feedback.getValueForMoneyRating());
        System.out.println("Feedback Message: " + feedback.getFeedbackMessage());
    }

    private boolean isValidRating(int rating) {
        return rating >= 0 && rating <= 5;
    }


   void viewFeedbackonFoodItem(){
    try {
		String itemName = InputHandler.getStringInput("Enter the Food Item name: ");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    this.requestPath = this.requestPath + "/viewFeedbackonFoodItem"; 

    String jsonRequest = jsonConverter.convertObjectToJson(null, this.requestPath);

    this.requestPath = "/" + this.role;


   }
  
}
