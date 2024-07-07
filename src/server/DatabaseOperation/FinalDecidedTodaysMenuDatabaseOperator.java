package server.DatabaseOperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import server.model.TodayMenu;
import server.DatabaseConnection;

public class FinalDecidedTodaysMenuDatabaseOperator {
    
    private Connection connection;
    
   
    public FinalDecidedTodaysMenuDatabaseOperator() {
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        this.connection = dbInstance.getConnection();
    }

 
    public String addFinalDecidedMenu(TodayMenu todayMenu) throws SQLException {
        MenuDatabaseOperator menu = new MenuDatabaseOperator();
        int categoryID = 0;
		try {
			categoryID = menu.getCategoryID(todayMenu.getCategoryName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int itemID = 0;
		try {
			itemID = menu.getItemID(todayMenu.getItemName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Prepare the SQL insert statement
        String sql = "INSERT INTO TodaysMenu (itemID, categoryID, menuDate) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, itemID);
            stmt.setInt(2, categoryID);
            stmt.setDate(3, todayMenu.getMenuDate());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return "Successfully added menu item to Today's Menu.";
            } else {
                return "Failed to add menu item to Today's Menu.";
            }
        } catch (SQLException e) {
            throw new SQLException("Error adding final decided menu item: " + e.getMessage());
        }
    }

    
    
    
    public List<TodayMenu> getTodaysMenuItems() throws SQLException {
        List<TodayMenu> todaysMenuItems = new ArrayList<>();

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Prepare the SQL query
        String sql = "SELECT t.menuID, f.nameOfFood AS itemName, f.foodPrice, c.categoryName " +
                     "FROM TodaysMenu t " +
                     "JOIN FoodMenuItem f ON t.itemID = f.itemID " +
                     "JOIN Category c ON t.categoryID = c.categoryID " +
                     "WHERE t.menuDate = ? " +
                     "ORDER BY t.categoryID";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Set the current date as parameter
            stmt.setDate(1, java.sql.Date.valueOf(currentDate));

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // Process the result set
            while (rs.next()) {
                int menuID = rs.getInt("menuID");
                String itemName = rs.getString("itemName");
                double foodPrice = rs.getDouble("foodPrice");
                String categoryName = rs.getString("categoryName");

                // Create a TodayMenu object and add to the list
                TodayMenu menuItem = new TodayMenu();
                
                menuItem.setItemName(itemName);
                menuItem.setCategoryName(categoryName);
                menuItem.setItemPrice(foodPrice);

                
                todaysMenuItems.add(menuItem);
            }
        }

        return todaysMenuItems;
    }
}

    
    
    
    
    
    
    
    
    
    
    
    

