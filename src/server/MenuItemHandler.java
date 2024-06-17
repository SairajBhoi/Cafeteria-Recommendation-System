package server;

import java.sql.SQLException;

public class MenuItemHandler {

    private final Menu menu;

    public MenuItemHandler() throws SQLException {
        this.menu = new Menu();
    }

    public String handleAddMenuItem(String data) throws Exception {
        MenuItem menuItem = JsonStringToObject.fromJsonToObject(data, MenuItem.class);
        String jsonresponse;
        if (!menu.isItemInCategory(menuItem.getItemName(), menuItem.getItemCategory())) {
            if (menu.addMenuItem(menuItem)) {
               jsonresponse =JsonConverter.convertStatusAndMessageToJson("success","Added item to menu.");

                
            } else {
            	 jsonresponse = JsonConverter.convertStatusAndMessageToJson("error","Error adding menu item");
            }
        } else {
        	jsonresponse = JsonConverter.convertStatusAndMessageToJson("info", "Item already present in menu");
 

        }
		return jsonresponse;
    }
    
   

    public String handleUpdateMenuItem(String data) throws Exception {
        MenuItem menuItem = JsonStringToObject.fromJsonToObject(data, MenuItem.class);
        String jsonresponse = null;
        if (menu.updateMenuItem(menuItem)) {
           jsonresponse=JsonConverter.convertStatusAndMessageToJson("success", "Updated menu item.");
        }else {
        	jsonresponse =JsonConverter.convertStatusAndMessageToJson("error","Error in updating menu item.");
        }
        
		return jsonresponse;
    }

    public String handleDeleteMenuItem(String data) throws Exception {
        MenuItem menuItem = JsonStringToObject.fromJsonToObject(data, MenuItem.class);
         String jsonresponse;
        if (menu.deleteMenuItem(menuItem.getItemName())) {
            jsonresponse =JsonConverter.convertStatusAndMessageToJson("success","Deleted menu item.");
        } else {
        	jsonresponse =JsonConverter.convertStatusAndMessageToJson("error","Error deleting menu item.");
    }
        return jsonresponse;
        }
    
    
    public String handleUpdateFoodAvailableStatus(String data) throws Exception {
    	String itemName =JsonStringToObject.getValueFromData("itemName", data);
    	boolean availabilityStatus=JsonStringToObject.getValueFromData("isItemAvailable", data).equalsIgnoreCase("true");  	
    	boolean status = menu.updateAvailability(itemName, availabilityStatus);
    	String jsonresponse;
    	if (status) {
    		jsonresponse =JsonConverter.convertStatusAndMessageToJson("success","Added item to menu.");
    	}
    	else {
    		jsonresponse =JsonConverter.convertStatusAndMessageToJson("error","Error in  updating status");
    	}
		return jsonresponse;
   
    }
}
