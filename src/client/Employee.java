public class Employee extends User {

    private FeedbackHandler feedbackHandler;
    private UserNotificationService userNotificationService;

    Employee(){
          
        FeedbackHandler=new FeedbackHandler(String this.role,this.userName);
        userNotificationService = new UserNotificationService(String this.role);

    }
ViewFeedbackonFoodItem(){
    
    String itemName=InputHandler.getStringInput("Enter Food Item Name");
    feedbackHandler.ViewFeedbackonFoodItem();

}

addFeedbackonFooditem(){
  feedbackHandler.addFeedbackOnFoodItem();
}

viewNotification(){
    userNotificationService.viewNotification(this.userID);
}

}