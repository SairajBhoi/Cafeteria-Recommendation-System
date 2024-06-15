import java.util.ArrayList;
import java.util.List;

public class Menu {
    private MenuItem menuItem;
    private JsonConverter jsonConverter;
    private String request;

    public Menu() {
        menuItem = new MenuItem();
        jsonConverter = new JsonConverter();
        this.request = "/admin/"; 
    }

    public void addMenuItem() {
        String itemName = InputHandler.getStringInput("Enter the Food Item name: ");
        float itemPrice = InputHandler.getFloatInput("Enter the Food Item price for " + itemName + ": ");
        boolean isItemAvailable = InputHandler.getBooleanInput(itemName + " is Available: Enter 'no' for not available, 'yes' for available");
        this.request=this.request+"/addmenuitem";

        char category;
        do {
            category = Character.toLowerCase(InputHandler.getCharInput("Enter Food Category: \nb - breakfast\nl - lunch\ns - snacks\nd - dinner\n"));
        } while (!isValidCategory(category));

        menuItem.setItemName(itemName);
        menuItem.setItemPrice(itemPrice);
        menuItem.setItemAvailable(isItemAvailable);
        menuItem.setItemCategory(category);
        
        String jsonRequest = jsonConverter.convertObjectToJson(menuItem, request); 
        System.out.println("JSON Request: " + jsonRequest); 
        
    }

    private boolean isValidCategory(char category) {
        return category == 'b' || category == 'l' || category == 's' || category == 'd';
    }

    public void updateMenuItem() {
        // Placeholder for update logic
    }

    public void deleteMenuItem() {
        // Placeholder for delete logic
    }

    public List<MenuItem> getAllMenuItems() {
        // Placeholder for retrieving all menu items
        return new ArrayList<>(); // Replace with actual implementation
    }
}
