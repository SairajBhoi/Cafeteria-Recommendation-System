package server;

import java.sql.SQLException;

public class MenuItemHandler {
    private final JsonStringToObject jsonStringToObject = new JsonStringToObject();
    private final Menu menu;

    public MenuItemHandler() throws SQLException {
        this.menu = new Menu();
    }

    public String handleAddMenuItem(String data) throws Exception {
        MenuItem menuItem = jsonStringToObject.fromJsonToObject(data, MenuItem.class);

        if (!menu.isItemInCategory(menuItem.getItemName(), menuItem.getItemCategory())) {
            if (menu.addMenuItem(menuItem)) {
                return "Added item to menu.";
            } else {
                return "Error adding menu item.";
            }
        } else {
            return "Item already present in menu.";
        }
    }

    public String handleUpdateMenuItem(String data) throws Exception {
        MenuItem menuItem = jsonStringToObject.fromJsonToObject(data, MenuItem.class);

        if (menu.updateMenuItem(menuItem)) {
            return "Updated menu item.";
        } else {
            return "Error updating menu item.";
        }
    }

    public String handleDeleteMenuItem(String data) throws Exception {
        MenuItem menuItem = jsonStringToObject.fromJsonToObject(data, MenuItem.class);

        if (menu.deleteMenuItem(menuItem.getItemName())) {
            return "Deleted menu item.";
        } else {
            return "Error deleting menu item.";
        }
    }
}
