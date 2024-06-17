package client;



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
        
        
        
      String role = user.getUserRole();
      String userName= user.getUserName();

        try {
            switch (role) {
                case "ADMIN" -> {
                    AdminController adminController = new AdminController(userName, userId);
                    adminController.runHomepage();
                }
                case "CHEF" -> {
                    ChefController chefController = new ChefController(userName, userId);
                    chefController.runHomePage();
                }
                case "EMPLOYEE" -> {
                    EmployeeController employeeController = new EmployeeController(userName, userId);
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
