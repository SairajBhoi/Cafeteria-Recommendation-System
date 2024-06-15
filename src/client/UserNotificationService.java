package client;

public class UserNotificationService{
    private String role;
    private String requestPath;
    private JsonConverter jsonConverter;	
 
    
UserNotificationService(String role)
{
    jsonConverter = new JsonConverter();
   this.role=role;
   this.requestPath="/"+role;

}

void viewNotification(String userID){

    this.requestPath=this.requestPath+"/viewNotification";
    String jsonRequest = jsonConverter.convertObjectToJson(null, this.requestPath);
    //to do have send and handle notification;
}

}