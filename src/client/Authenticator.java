package client;

public class Authenticator {
    private JsonConverter jsonConverter;
    private String requestPath;

    public Authenticator() {
        jsonConverter = new JsonConverter();
        requestPath = "/authenticate"; 
    }

    public void authenticate(String userId, String password) throws Exception {
    	UserCredentials userCredentials=new UserCredentials(userId, password);
        // Convert user credentials to JSON
        String jsonRequest = jsonConverter.convertObjectToJson(requestPath,userCredentials);
        System.out.println("JSON Request: " + jsonRequest);

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

 public  class UserCredentials {
        private String userId;
        private String password;
        private String roll;
  

        public UserCredentials(String userId, String password) {
            this.userId = userId;
            this.password = password;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
        
        public String getRoll() {
            return roll;
        }

        public void setRoll(String roll) {
            this.roll = roll;
        }
    }
}
