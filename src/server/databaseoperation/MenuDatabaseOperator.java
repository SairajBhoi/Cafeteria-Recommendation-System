package server.databaseoperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import server.DatabaseConnection;
import server.model.MenuItem;

public class MenuDatabaseOperator {

    private Connection connection;

    public MenuDatabaseOperator() {
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        this.connection = dbInstance.getConnection();
    }

    public boolean addMenuItem(MenuItem menuItem) throws Exception {
        if (isItemNameExists(menuItem.getItemName())) {
            int itemId = getItemID(menuItem.getItemName());
            int categoryId = getCategoryID(menuItem.getItemCategory());

            if (!(isItemAndCategoryAssociationExist(itemId, categoryId))) {
                boolean success = insertIntoFoodItemCategory(itemId, categoryId);
                if (!success) {
                    throw new Exception("Failed to insert into FoodItemCategory.");
                }
            }
        }
        else {
        String query = "INSERT INTO FoodMenuItem (nameOfFood, foodPrice, foodAvailable, CuisineType, FoodType, SpiceLevel, IsSweet) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement addMenuItemStmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
	     addMenuItemStmt.setString(1, menuItem.getItemName());
	     addMenuItemStmt.setFloat(2, menuItem.getItemPrice());
	     addMenuItemStmt.setBoolean(3, menuItem.isItemAvailable());
	     addMenuItemStmt.setString(4, menuItem.getCuisineType());
	     addMenuItemStmt.setString(5, menuItem.getFoodType());
	     addMenuItemStmt.setString(6, menuItem.getSpiceLevel());
	     addMenuItemStmt.setBoolean(7, menuItem.isSweet());

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

                // Insert into FoodItemCategory if association doesn't exist
                if (!(isItemAndCategoryAssociationExist(itemId, categoryId))) {
                    boolean success = insertIntoFoodItemCategory(itemId, categoryId);
                    if (!success) {
                        throw new Exception("Failed to insert into FoodItemCategory.");
                    }
                }

                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            throw new Exception("Failed to add menu item.\n" + ex.getMessage());
        }
        }
		return true;
    }

    public int getCategoryID(String categoryName) throws Exception {
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

    public boolean isItemAndCategoryAssociationExist(int itemId, int categoryId) throws Exception {
        String query = "SELECT COUNT(*) AS count FROM FoodItemCategory WHERE itemID = ? and categoryID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, itemId);
            stmt.setInt(2, categoryId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
        } catch (SQLException ex) {
            throw new Exception("Failed to check if item and category association exists.\n" + ex.getMessage());
        }
        return false;
    }

    public boolean updateMenuItem(MenuItem menuItem) throws Exception {
        if (isItemNameExists(menuItem.getItemName())) {
        	String query = "UPDATE FoodMenuItem SET foodPrice = ?, foodAvailable = ?, CuisineType = ?, FoodType = ?, SpiceLevel = ?, IsSweet = ? WHERE nameOfFood = ?";
            try (PreparedStatement updateMenuItemStmt = connection.prepareStatement(query)) {
                updateMenuItemStmt.setDouble(1, menuItem.getItemPrice());
                updateMenuItemStmt.setBoolean(2, menuItem.isItemAvailable());
                updateMenuItemStmt.setString(3, menuItem.getCuisineType());
                updateMenuItemStmt.setString(4, menuItem.getFoodType());
                updateMenuItemStmt.setString(5, menuItem.getSpiceLevel());
                updateMenuItemStmt.setBoolean(6, menuItem.isSweet());
                updateMenuItemStmt.setString(7, menuItem.getItemName());
                
                int rowsUpdated = updateMenuItemStmt.executeUpdate();
                return rowsUpdated > 0;
            } catch (SQLException ex) {
                throw new Exception("Failed to update menu item.\n" + ex.getMessage());
            }
        } else {
            throw new Exception("Failed to update menu item. Item does not exist.\n");
        }
    }

    public boolean updateAvailability(String itemName, boolean foodAvailable) throws Exception {
        if (isItemNameExists(itemName)) {
            String query = "UPDATE FoodMenuItem SET foodAvailable = ? WHERE nameOfFood = ?";
            try (PreparedStatement updateAvailabilityStmt = connection.prepareStatement(query)) {
                updateAvailabilityStmt.setBoolean(1, foodAvailable);
                updateAvailabilityStmt.setString(2, itemName);

                int rowsUpdated = updateAvailabilityStmt.executeUpdate();
                return rowsUpdated > 0;
            } catch (SQLException ex) {
                throw new Exception("Failed to update availability status.\n" + ex.getMessage());
            }
        } else {
            throw new Exception("Item does not exist.\n");
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

        String query = "SELECT m.itemID, m.nameOfFood, m.foodPrice, m.foodAvailable, " +
                "m.CuisineType, m.FoodType, m.SpiceLevel, m.IsSweet, " +
                "c.categoryName " +
                "FROM FoodMenuItem m " +
                "JOIN FoodItemCategory ic ON m.itemID = ic.itemID " +
                "JOIN Category c ON ic.categoryID = c.categoryID";

	    try (PreparedStatement viewMenuItemsStmt = connection.prepareStatement(query)) {
	     ResultSet rs = viewMenuItemsStmt.executeQuery();
	
	     while (rs.next()) {
	         Map<String, Object> item = new LinkedHashMap<>();
	         item.put("itemID", rs.getInt("itemID"));
	         item.put("nameOfFood", rs.getString("nameOfFood"));
	         item.put("foodPrice", rs.getDouble("foodPrice"));
	         item.put("foodAvailable", rs.getBoolean("foodAvailable"));
	         item.put("CuisineType", rs.getString("CuisineType"));
	         item.put("FoodType", rs.getString("FoodType"));
	         item.put("SpiceLevel", rs.getString("SpiceLevel"));
	         item.put("IsSweet", rs.getBoolean("IsSweet"));
	         item.put("categoryName", rs.getString("categoryName"));
	         menuList.add(item);
	     }
        } catch (SQLException ex) {
            throw new Exception("Failed to view menu items.\n" + ex.getMessage());
        }

        return menuList;
    }

    public int getItemID(String menuItem) throws Exception {
        String query = "SELECT itemID FROM FoodMenuItem WHERE nameOfFood = ?";
        try (PreparedStatement getItemIDStmt = connection.prepareStatement(query)) {
            getItemIDStmt.setString(1, menuItem);
            ResultSet rs = getItemIDStmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("itemID");
            } else {
                throw new Exception("Item not found for name: " + menuItem);
            }
        } catch (SQLException ex) {
            throw new Exception("Failed to get item ID.\n" + ex.getMessage());
        }
    }


    
    

    public boolean isItemInCategory(String itemName, String categoryName) throws Exception {
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
                return count > 0;
            }
        } catch (SQLException ex) {
            throw new Exception("Failed to check item category.\n" + ex.getMessage());
        }

        return false;
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

    private void deleteMenuItemFromSpecificCategory(int itemId, int categoryId) throws SQLException {
        String query = "DELETE FROM FoodItemCategory WHERE itemID = ? AND categoryID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, itemId);
            stmt.setInt(2, categoryId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Failed to delete menu item from specified category.\n" + ex.getMessage());
        }
    }

    public String getItemName(int itemId) throws Exception {
        String query = "SELECT nameOfFood FROM FoodMenuItem WHERE itemID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, itemId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nameOfFood");
            } else {
                throw new Exception("Item not found for ID: " + itemId);
            }
        } catch (SQLException ex) {
            throw new Exception("Failed to get item name.\n" + ex.getMessage());
        }
    }

    public String getCategoryName(int categoryId) throws Exception {
        String query = "SELECT categoryName FROM Category WHERE categoryID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("categoryName");
            } else {
                throw new Exception("Category not found for ID: " + categoryId);
            }
        } catch (SQLException ex) {
            throw new Exception("Failed to get category name.\n" + ex.getMessage());
        }
    }

  
}
