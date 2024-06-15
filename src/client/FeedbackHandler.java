import java.util.Scanner;

public class FeedbackHandler {
    private Feedback feedback;
    private String requestPath;
    private JsonConverter jsonConverter;
    private String role;

    public FeedbackHandler(String role) {
        jsonConverter = new JsonConverter();
        feedback = new Feedback();
        this.role = role;
        this.requestPath = "/" + role;
    }

    public void addFeedbackOnFoodItem(String employeeName) {
        Scanner scanner = new Scanner(System.in);

        String itemName = InputHandler.getStringInput("Enter the Food Item name: ");
        int tasteRating, qualityRating, freshnessRating, valueForMoneyRating;

        do {
            tasteRating = getIntegerInput("Enter the Taste rating of " + itemName + " (0-5): ", scanner);
        } while (!isValidRating(tasteRating));

        do {
            qualityRating = getIntegerInput("Enter the Quality rating of " + itemName + " (0-5): ", scanner);
        } while (!isValidRating(qualityRating));

        do {
            freshnessRating = getIntegerInput("Enter the Freshness rating of " + itemName + " (0-5): ", scanner);
        } while (!isValidRating(freshnessRating));

        do {
            valueForMoneyRating = getIntegerInput("Enter the Value for Money rating of " + itemName + " (0-5): ", scanner);
        } while (!isValidRating(valueForMoneyRating));

        String feedbackMessage = InputHandler.getStringInput("Enter feedback message: ");

        this.requestPath = this.requestPath + "/addFeedbackOnFoodItem"; 

        feedback.setEmployeeName(employeeName);
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
    String itemName = InputHandler.getStringInput("Enter the Food Item name: ");

    this.requestPath = this.requestPath + "/viewFeedbackonFoodItem"; 

    String jsonRequest = jsonConverter.convertObjectToJson(null, this.requestPath);

    this.requestPath = "/" + this.role;


   }
  
}
