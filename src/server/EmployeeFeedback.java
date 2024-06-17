package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

public class EmployeeFeedback {
	 private Connection connection;
	 public EmployeeFeedback() {
	        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
			this.connection = dbInstance.getConnection();
	    }

    public String addFeedback(Feedback feedback) throws Exception {
    
    	String   status = null;
    	Menu menu = new Menu();
    	
        String sqlQuery = "INSERT INTO UserFeedbackOnFoodItem (userUserId, itemID, feedbackGivenDate, qualityRating, tasteRating, freshnessRating, valueForMoneyRating, feedbackMessage, sentiment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement addMenuItemStmt = connection.prepareStatement(sqlQuery)) {

          
        	addMenuItemStmt.setString(1, feedback.getEmployeeName()); 
        	addMenuItemStmt.setInt(2, menu.getItemID(feedback.getItemName()));
        	addMenuItemStmt.setDate(3, new java.sql.Date(Calendar.getInstance().getTime().getTime())); // Current date
        	addMenuItemStmt.setInt(4, feedback.getQualityRating());
        	addMenuItemStmt.setInt(5, feedback.getTasteRating());
        	addMenuItemStmt.setInt(6, feedback.getFreshnessRating());
        	addMenuItemStmt.setInt(7, feedback.getValueForMoneyRating());
        	addMenuItemStmt.setString(8, feedback.getFeedbackMessage());
        	addMenuItemStmt.setString(9, feedback.getFeedbackMessageSentiment());

        	addMenuItemStmt.executeUpdate();

           status= "Feedback added successfully.";
            

        } catch (SQLException e) {
        	 throw new Exception("Error during authentication: " + e.getMessage());
        	 }
        finally {
        return status ;
        }
    }

 
}
