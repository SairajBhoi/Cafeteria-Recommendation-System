package client;



public class Authenticator {
    private String requestPath;
    
    public Authenticator() {
        requestPath = "/authenticate"; 
    }

    public void authenticate(String userId, String password) throws Exception {
    	User user= new User();
    	
        String jsonRequest = JsonConverter.convertObjectToJson(requestPath,user);
        System.out.println("JSON Request: " + jsonRequest);
        
        String response = Client.requestServer(jsonRequest);
        
          user =JsonStringToObject.fromJsonToObject(response, User.class);
        
        
        
        String role = InputHandler.getStringInput("Enter role");
        String userName = "Sairaj";

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
        }
    }

 
}
