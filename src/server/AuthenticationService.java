package server;

public class AuthenticationService {
    private final UserDAO userDAO;

    public AuthenticationService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserDetail authenticate(String userId, String password) {
        return userDAO.authenticate(userId, password);
    }
}
