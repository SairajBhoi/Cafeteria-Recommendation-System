package server;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public UserDetail authenticate(String userId, String password) {
        UserDetail userDetail = new UserDetail();
        userDetail.setUserId(userId);

        String query = "SELECT username, role FROM users WHERE user_id = ? AND password = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, userId);
            statement.setString(2, password);
            
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String role = resultSet.getString("role");
                userDetail.setUsername(username);
                userDetail.setPassword(password);
                userDetail.setRole(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
          
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
           
        }
        return userDetail;
    }
}
