package server;

public class AuthenticationService {
    private final UserDAO userDAO;
    public AuthenticationService() {
        this.userDAO = new UserDAO();
    }

    public String authenticate(String  data) {
    User user = null;
	try {
		user = JsonStringToObject.fromJsonToObject(data, User.class);
		System.out.println("user ID========="+user.getUserId());
		System.out.println("user password========="+user.getUserPassword());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    String jsonResponse;
    try {
	user= userDAO.authenticateUser(user.getUserId(),user.getUserPassword());
	jsonResponse=JsonConverter.convertObjectToJson(user);
	
	
	
	} catch (Exception e) {
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", e.getMessage());             
        }
	return jsonResponse;
    }
}
