public class Employee extends User {

    private FeedbackHandler feedbackHandler;
    private UserNotificationService userNotificationService;

    Employee(){
          
        feedbackHandler=new FeedbackHandler(this.getUserRole(),this.getUserName());
        userNotificationService = new UserNotificationService(this.getUserRole());

    }
void ViewFeedbackonFoodItem(){
    
    String itemName=InputHandler.getStringInput("Enter Food Item Name");
    feedbackHandler.viewFeedbackonFoodItem();

}

void addFeedbackonFooditem(){
  feedbackHandler.addFeedbackOnFoodItem();
}

void viewNotification(){
    userNotificationService.viewNotification(this.getUserId());
}

}