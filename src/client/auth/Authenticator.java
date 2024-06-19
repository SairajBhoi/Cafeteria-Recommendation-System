package client.auth;

import client.Client;
import client.controller.AdminController;
import client.controller.ChefController;
import client.controller.EmployeeController;
import client.model.User;
import client.util.JsonConverter;
import client.util.JsonStringToObject;

public class Authenticator {
    private String requestPath;
    
    public Authenticator() {
        requestPath = "/authenticate"; 
    
    }

    public boolean authenticate(String userId, String password) throws Exception {
    	User user= new User();
    	user.setUserId(userId);
    	user.setUserPassword(password);
        String jsonRequest = JsonConverter.convertObjectToJson(requestPath,user);
        
    
      
        System.out.println("JSON Request: " + jsonRequest);
        
        String jsonresponse = Client.requestServer(jsonRequest);
        System.out.println("JSON Request: " + jsonresponse);
        
        if(JsonStringToObject.getValueFromData("status", jsonresponse).equals("error"))
        {	
        System.out.println(JsonStringToObject.getValueFromData("message", jsonresponse));
        
       	user.logout();
       	return false ;
        	
        }
       else {
        
        user =JsonStringToObject.fromJsonToObject(jsonresponse, User.class);
        System.out.print("+++++++++++++++++++++++++++++++");
        System.out.print(user.getUserId());
        System.out.print(user.getUserPassword());
        System.out.print(user.getUserName());
        System.out.print("+++++++++++++++++++++++++++++++");
      String role = user.getUserRole();
      String userName= user.getUserName();
      System.out.print("+++++++++++++++++++++++++++++++"); 
      user.setUserId(JsonStringToObject.getValueFromData("userId", jsonresponse));
      user.setUserName(JsonStringToObject.getValueFromData("userName", jsonresponse));
      
      System.out.print(user.getUserId());
      System.out.print(user.getUserPassword());
      System.out.print(user.getUserName());


      
        try {
            switch (role) {
                case "ADMIN" -> {
                    AdminController adminController = new AdminController(user.getUserName(), user.getUserId());
                    adminController.runHomepage();
                }
                case "CHEF" -> {
                    ChefController chefController = new ChefController(user.getUserName(), user.getUserId());
                    chefController.runHomePage();
                }
                case "EMPLOYEE" -> {
                    EmployeeController employeeController = new EmployeeController(user.getUserName(), user.getUserId());
                    employeeController.runHomePage();
                }
                default -> {
                    System.out.println("Invalid role.");
                }
            }
        } catch (Exception ex) {
            System.out.println("Server connection failed.");
        }finally {
        	user.logout();
        }
   }
		return true;
    }
 
}
