package server.DatabaseOperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.DatabaseConnection;
import server.model.MenuItem;

public class Menu {

    private Connection connection;

    public Menu() {
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        this.connection = dbInstance.getConnection();
    }

    public boolean addMenuItem(MenuItem menuItem) throws Exception {
        
    	if (isItemNameExists(menuItem.getItemName())) {
    		int itemId= getItemID(menuItem.getItemName());
    		int categoryId = getCategoryID(menuItem.getItemCategory());
    		
    		
    		if(!(isItemandCategoryAssociationExist(itemId,categoryId))) {
    		 boolean success = insertIntoFoodItemCategory(itemId, categoryId);
             return success;	
    		}
    	}
    	String query = "INSERT INTO FoodMenuItem (nameOfFood, foodPrice, foodAvailable) VALUES (?, ?, ?)";
        try (PreparedStatement addMenuItemStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            addMenuItemStmt.setString(1, menuItem.getItemName());
            addMenuItemStmt.setDouble(2, menuItem.getItemPrice());
            addMenuItemStmt.setBoolean(3, menuItem.isItemAvailable());

            int rowsInserted = addMenuItemStmt.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet generatedKeys = addMenuItemStmt.getGeneratedKeys();
                int itemId = -1;
                if (generatedKeys.next()) {
                    itemId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to get generated itemID.");
                }

                int categoryId = getCategoryID(menuItem.getItemCategory());
                
                if(!(isItemandCategoryAssociationExist(itemId,categoryId))) {
                boolean success = insertIntoFoodItemCategory(itemId, categoryId);
                return success;
                }
            } else {
                return false;
            }
        } catch (SQLException ex) {
            throw new Exception("Failed to add menu item.\n" + ex.getMessage());
        }
        
        
        
        
		return false;
    }

    public int getCategoryID(String categoryName) throws Exception {
    	System.out.print(categoryName);
        String query = "SELECT categoryID FROM Category WHERE categoryName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, categoryName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("categoryID");
            } else {
                throw new Exception("Meal type not found: " + categoryName);
            }
        } catch (SQLException ex) {
            throw new Exception("Failed to get meal type ID.\n" + ex.getMessage());
        }
    }

    private boolean insertIntoFoodItemCategory(int itemId, int categoryId) throws Exception {
        String query = "INSERT INTO FoodItemCategory (itemID, categoryID) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, itemId);
            stmt.setInt(2, categoryId);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            throw new Exception("Failed to insert into FoodItemCategory.\n" + ex.getMessage());
        }
    }

    public boolean isItemNameExists(String itemName) throws Exception {
        String query = "SELECT COUNT(*) AS count FROM FoodMenuItem WHERE nameOfFood = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, itemName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
        } catch (SQLException ex) {
            throw new Exception("Failed to check if item name exists.\n" + ex.getMessage());
        }
        return false;
    }
    
    
    public boolean isItemandCategoryAssociationExist(int itemId,int categoryId) throws Exception {
        String query = "SELECT COUNT(*) AS count FROM FoodItemCategory WHERE itemID = ? and  categoryID  = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, itemId);
            stmt.setInt(2, categoryId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
        } catch (SQLException ex) {
            throw new Exception("Failed to check if item name exists.\n" + ex.getMessage());
        }
        return false;
    }

    public boolean updateMenuItem(MenuItem menuItem) throws Exception {
    	System.out.println(menuItem.getItemPrice());
    	if(isItemNameExists(menuItem.getItemName())) {
    		System.out.println(menuItem.getItemName());
    		
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
    	else { 
    		throw new Exception("Failed to update menu Item does not exist item.\n");
    	}
    	}
    

    public boolean updateAvailability(String itemName,boolean foodAvailable) throws Exception {
    	if(isItemNameExists(itemName) ){
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
    	throw new Exception("item name does not exist.\n" );
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

    public boolean deleteMenuItem(String itemName, String category) throws Exception {
        int itemId = getItemID(itemName);

        if (!category.equals("all")) {
            int categoryId = getCategoryID(category);
            deleteMenuItemFromSpecificCategory(itemId, categoryId);
        } else {
            deleteMenuItemFromAllCategories(itemId);
            deleteMenuItem(itemName);
        }

        return true;
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

    public boolean updateAvailabilityStatus(String itemName, boolean foodAvailable) throws Exception {
        int itemId = getItemID(itemName);
        String query = "UPDATE FoodMenuItem SET foodAvailable = ? WHERE itemID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setBoolean(1, foodAvailable);
            stmt.setInt(2, itemId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            throw new Exception("Failed to update availability status.\n" + ex.getMessage());
        }
    }

    private void deleteMenuItemFromAllCategories(int itemId) throws SQLException {
        String query = "DELETE FROM FoodItemCategory WHERE itemID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, itemId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Failed to delete menu item from all categories.\n" + ex.getMessage());
        }
    }

    private void deleteMenuItemFromSpecificCategory(int itemId, int categoryID) throws SQLException {
        String query = "DELETE FROM FoodItemCategory WHERE itemID = ? AND categoryID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, itemId);
            stmt.setInt(2, categoryID);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Failed to delete menu item from specified category.\n" + ex.getMessage());
        }
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