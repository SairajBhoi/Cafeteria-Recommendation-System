package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {

    private Connection connection;

    public Menu() {
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
		this.connection = dbInstance.getConnection();
    }

    public boolean addMenuItem(MenuItem menuItem) throws Exception {
        String query = "INSERT INTO FoodMenuItem (nameOfFood, foodPrice, foodAvailable) VALUES (?, ?, ?)";
        try (PreparedStatement addMenuItemStmt = connection.prepareStatement(query)) {
            addMenuItemStmt.setString(1, menuItem.getItemName());
            addMenuItemStmt.setDouble(2, menuItem.getItemPrice());
            addMenuItemStmt.setBoolean(3, menuItem.isItemAvailable());

            int rowsInserted = addMenuItemStmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            throw new Exception("Failed to add menu item.\n" + ex.getMessage());
        }
    }

    public boolean updateMenuItem(MenuItem menuItem) throws Exception {
        String query = "UPDATE FoodMenuItem SET foodPrice = ?, foodAvailable = ? WHERE nameOfFood = ?";
        try (PreparedStatement updateMenuItemStmt = connection.prepareStatement(query)) {
            updateMenuItemStmt.setDouble(1, menuItem.getItemPrice());
            updateMenuItemStmt.setBoolean(2, menuItem.isItemAvailable());
            updateMenuItemStmt.setString(3, menuItem.getItemName());

            int rowsUpdated = updateMenuItemStmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            throw new Exception("Failed to update menu item.\n" + ex.getMessage());
        }
    }
    
    
    public boolean updateAvailability(String itemName, boolean foodAvailable) throws Exception {
        String query = "UPDATE FoodMenuItem SET foodAvailable = ? WHERE nameOfFood = ?";
        try (PreparedStatement updateAvailabilityStmt = connection.prepareStatement(query)) {
            updateAvailabilityStmt.setBoolean(1, foodAvailable);
            updateAvailabilityStmt.setString(2, itemName);

            int rowsUpdated = updateAvailabilityStmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            throw new Exception("Failed to update availability status.\n" + ex.getMessage());
        }
    }

    public boolean deleteMenuItem(String itemName) throws Exception {
        String query = "DELETE FROM FoodMenuItem WHERE nameOfFood = ?";
        try (PreparedStatement deleteMenuItemStmt = connection.prepareStatement(query)) {
            deleteMenuItemStmt.setString(1, itemName);

            int rowsDeleted = deleteMenuItemStmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException ex) {
            throw new Exception("Failed to delete menu item.\n" + ex.getMessage());
        }
    }

    public List<Map<String, Object>> viewMenuItems() throws Exception {
        List<Map<String, Object>> menuList = new ArrayList<>();

        String query = "SELECT itemID, nameOfFood, foodPrice, foodAvailable FROM FoodMenuItem";

        try (PreparedStatement viewMenuItemsStmt = connection.prepareStatement(query)) {
            ResultSet rs = viewMenuItemsStmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("itemID", rs.getInt("itemID"));
                item.put("nameOfFood", rs.getString("nameOfFood"));
                item.put("foodPrice", rs.getDouble("foodPrice"));
                item.put("foodAvailable", rs.getBoolean("foodAvailable"));
                menuList.add(item);
            }
        } catch (SQLException ex) {
            throw new Exception("Failed to view menu items.\n" + ex.getMessage());
        }

        return menuList;
    }

    public int getItemID(String menuItem) throws Exception {
        int itemID = 0;
        String query = "SELECT itemID FROM FoodMenuItem WHERE nameOfFood = ?";
        try (PreparedStatement getItemIDStmt = connection.prepareStatement(query)) {
            getItemIDStmt.setString(1, menuItem);
            ResultSet rs = getItemIDStmt.executeQuery();
            if (rs.next()) {
                itemID = rs.getInt("itemID");
            }
        } catch (SQLException ex) {
            throw new Exception("Failed to get item ID.\n" + ex.getMessage());
        }
        return itemID;
    }
    
    public boolean isItemInCategory(String itemName, String categoryName) throws Exception {
        boolean isInCategory = false;

        String query = "SELECT COUNT(*) AS count FROM FoodMenuItem fm " +
                       "JOIN FoodItemCategory fic ON fm.itemID = fic.itemID " +
                       "JOIN Category c ON fic.categoryID = c.categoryID " +
                       "WHERE fm.nameOfFood = ? AND c.categoryName = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, itemName);
            stmt.setString(2, categoryName);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                isInCategory = count > 0;
            }
        } catch (SQLException ex) {
            throw new Exception("Failed to check item category.\n" + ex.getMessage());
        }

        return isInCategory;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
