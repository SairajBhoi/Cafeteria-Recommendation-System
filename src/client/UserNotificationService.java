package client;

public class UserNotificationService{
    private String role;
    private String requestPath;
 
    
UserNotificationService(String role)
{
   this.role=role;
   this.requestPath="/"+role;

}

void viewNotification(String userID){

    this.requestPath=this.requestPath+"/viewNotification";
    String jsonRequest = JsonConverter.convertObjectToJson(null, this.requestPath);
    //TO DO have send and handle notification;
}

}