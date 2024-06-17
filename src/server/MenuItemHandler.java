package server;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class MenuItemHandler {

    private final Menu menu;

    public MenuItemHandler() throws SQLException {
        this.menu = new Menu();
    }

    public String addMenuItem(String data) throws Exception {
    	System.out.println(data);
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
    
   

    public String updateMenuItem(String data)  {
        MenuItem menuItem = JsonStringToObject.fromJsonToObject(data, MenuItem.class);
        String jsonresponse = null;
        try {
        	boolean status=menu.updateMenuItem(menuItem);
			if (status) {
			   jsonresponse=JsonConverter.convertStatusAndMessageToJson("success", "Updated menu item.");
			}else {
				jsonresponse =JsonConverter.convertStatusAndMessageToJson("error","Error in updating menu item.");
			}
		} catch (Exception e) {
			JsonConverter.convertStatusAndMessageToJson("error","Error in updating menu item."+e.getMessage());
		}
        
		return jsonresponse;
    }

    public String deleteMenuItem(String data) throws Exception {
        MenuItem menuItem = JsonStringToObject.fromJsonToObject(data, MenuItem.class);
         String jsonresponse;
        if (menu.deleteMenuItem(menuItem.getItemName(),menuItem.getItemCategory())) {
            jsonresponse =JsonConverter.convertStatusAndMessageToJson("success","Deleted menu item.");
        } else {
        	jsonresponse =JsonConverter.convertStatusAndMessageToJson("error","Error deleting menu item.");
    }
        return jsonresponse;
        }
    
    
    public String updateFoodAvailableStatus(String data) throws Exception {
    	String itemName =JsonStringToObject.getValueFromData("itemName", data);
    	System.out.println(itemName);
    	
    	boolean availabilityStatus=JsonStringToObject.getValueFromData("itemAvailable", data).equalsIgnoreCase("true");  	
    	
    
    	System.out.println(JsonStringToObject.getValueFromData("itemAvailable", data));
    	boolean status = menu.updateAvailability(itemName, availabilityStatus);
    	
    	String jsonresponse;
    	if (status) {
    		jsonresponse =JsonConverter.convertStatusAndMessageToJson("success","updated Availability Status in  menu.");
    	}
    	else {
    		jsonresponse =JsonConverter.convertStatusAndMessageToJson("error","Error in  updating status");
    	}
		return jsonresponse;
   
    }
    
    public String viewAllMenuItems() {
    	String jsonresponse;
    	List<Map<String, Object>> menuList = null;
		try {
			menuList = menu.viewMenuItems();
		} catch (Exception e) {
			jsonresponse =JsonConverter.convertStatusAndMessageToJson("error",e.getMessage());
			
			e.printStackTrace();
		}
    	jsonresponse=JsonConverter.convertObjectToJson(menuList);
		return jsonresponse;
    }
}
