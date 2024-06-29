package server.DatabaseOperation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import server.model.Notification;
import server.DatabaseConnection;

public class NotificationDatabaseOperator {
    private Connection connection;

    public NotificationDatabaseOperator() {
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        this.connection = dbInstance.getConnection();
    }

    public int addNotification(String message) {
        String sqlQuery = "INSERT INTO Notification (notificationMessage, notificationDate) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {

            LocalDate notificationDate = LocalDate.now();

            preparedStatement.setString(1, message);
            preparedStatement.setDate(2, Date.valueOf(notificationDate));

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // return the generated notificationID
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; 
    }
    
    
    public boolean associateNotificationWithUsers(int notificationID) {
        String insertQuery = "INSERT INTO UserNotification (notificationID, userID, isOpened) " +
                             "SELECT ?, userID, 0 FROM User";

        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            insertStatement.setInt(1, notificationID);
            int rowsAffected = insertStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public List<Notification> getUnseenNotifications(String userID) {
        String selectQuery = "SELECT n.notificationID, n.notificationMessage, n.notificationDate " +
                             "FROM Notification n " +
                             "JOIN UserNotification un ON n.notificationID = un.notificationID " +
                             "WHERE un.userID = ? AND un.isOpened = 0";

        List<Notification> notifications = new ArrayList<>();

        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            selectStatement.setString(1, userID);

            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                int notificationID = resultSet.getInt("notificationID");
                String notificationMessage = resultSet.getString("notificationMessage");
                Date notificationDate = resultSet.getDate("notificationDate");
                Notification notification = new Notification();
                notification.setNotificationID(notificationID);
                notification.setNotificationMessage(notificationMessage);
                LocalDate localDate = LocalDate.now();
                Date currentDate = Date.valueOf(localDate);
                notification.setNotificationDate(currentDate);
                  
                notifications.add(notification);
                markNotificationsAsSeen(userID,notification.getNotificationID());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!notifications.isEmpty()) {
           
        }

        return notifications;
    }

    private boolean markNotificationsAsSeen(String userID, int notificationID) {
        String updateQuery =  "UPDATE UserNotification SET isOpened = 1 " +
                "WHERE userID = ? AND notificationID = ? AND isOpened = 0";

        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setString(1, userID);
            updateStatement.setInt(2, notificationID);
            int rowsAffected = updateStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Notification> getAllNotifications() {
        String selectQuery = "SELECT notificationID, notificationMessage, notificationDate FROM Notification ORDER BY notificationDate DESC";

        List<Notification> notifications = new ArrayList<>();

        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                int notificationID = resultSet.getInt("notificationID");
                String notificationMessage = resultSet.getString("notificationMessage");
                LocalDate notificationDate = resultSet.getDate("notificationDate").toLocalDate();

                Notification notification = new Notification();
                notification.setNotificationID(notificationID);
                notification.setNotificationMessage(notificationMessage);
                LocalDate localDate = LocalDate.now();
                Date currentDate = Date.valueOf(localDate);
                notification.setNotificationDate(currentDate);
                notifications.add(notification);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notifications;
    }
    
    
}