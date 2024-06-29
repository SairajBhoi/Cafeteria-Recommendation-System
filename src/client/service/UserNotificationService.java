package client.service;

import java.io.IOException;

import client.Client;
import client.util.JsonConverter;
import client.util.PrintOutToConsole;

public class UserNotificationService{
    private String role;
    private String requestPath;
 
    
public UserNotificationService(String role)
{
   this.role=role;
   this.requestPath="/"+role;

}

public void viewUnseenNotification(String userID){

    this.requestPath=this.requestPath+"/viewUnseenNotification";
    String jsonRequest =JsonConverter.convertObjectToJson(this.requestPath, null);
    
    
    String userIdjsonString  =JsonConverter.createJson("UserID", userID);
   
    
    System.out.print(userIdjsonString);
    
    String jsonRequestToServer =JsonConverter.addJsonObjectToDataField(jsonRequest, userIdjsonString);
    
    System.out.print(jsonRequestToServer);
    
    String jsonRespose = null;
	try {
		jsonRespose = Client.requestServer(jsonRequestToServer);
		System.out.print(jsonRespose);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    PrintOutToConsole.printToConsole(jsonRespose);
    
    
    this.requestPath="/"+role;
    
    PrintOutToConsole.printToConsole(jsonRespose);
   
}




public void viewNotification(String userID){

    this.requestPath=this.requestPath+"/viewNotification";
    
    String jsonRequest = JsonConverter.convertObjectToJson(this.requestPath,null);
    
  
    System.out.println(jsonRequest);
    String jsonRespose = null;
	try {
		jsonRespose = Client.requestServer(jsonRequest);
		System.out.print(jsonRespose);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    PrintOutToConsole.printToConsole(jsonRespose);
    
    
    this.requestPath="/"+role;
    this.requestPath=this.requestPath+"/viewUnseenNotification";
     jsonRequest = JsonConverter.convertObjectToJson(this.requestPath,JsonConverter.convertStatusAndMessageToJson("UserID", userID));
   
    jsonRespose = null;
	try {
		jsonRespose = Client.requestServer(jsonRequest);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    PrintOutToConsole.printToConsole(jsonRespose);
    
    
   
   
}

}