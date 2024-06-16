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
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    String jsonResponse;
    try {
		 jsonResponse=  userDAO.authenticateUser(user.getUserId(),user.getUserPassword());
	} catch (Exception e) {
            jsonResponse = "{\"error\": \"" + e.getMessage() + "\"}";
        }
	return jsonResponse;
    }
}
