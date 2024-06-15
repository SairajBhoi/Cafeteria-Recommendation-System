import java.util.ArrayList;
import java.util.List;

public class Menu {
    private MenuItem menuItem;
    private JsonConverter jsonConverter;
    private String requestPath;

    public Menu() {
        jsonConverter = new JsonConverter();
        this.requestPath = "/admin"; 
    }

    public void addMenuItem() {
        menuItem = new MenuItem();
        String itemName = InputHandler.getStringInput("Enter the Food Item name: ");
        float itemPrice = InputHandler.getFloatInput("Enter the Food Item price for " + itemName + ": ");
        boolean isItemAvailable = InputHandler.getBooleanInput(itemName + " is Available: Enter 'no' for not available, 'yes' for available");
        this.requestPath = this.requestPath + "/addmenuitem";

        char category;
        do {
            category = Character.toLowerCase(InputHandler.getCharInput("Enter Food Category: \nb - breakfast\nl - lunch\ns - snacks\nd - dinner\n"));
        } while (!isValidCategory(category));

        menuItem.setItemName(itemName);
        menuItem.setItemPrice(itemPrice);
        menuItem.setItemAvailable(isItemAvailable);
        menuItem.setItemCategory(category);
        
        String jsonRequest = jsonConverter.convertObjectToJson(menuItem, this.requestPath); 
        System.out.println("JSON Request: " + jsonRequest); 
        this.requestPath = "/admin";
    }

    private boolean isValidCategory(char category) {
        return category == 'b' || category == 'l' || category == 's' || category == 'd';
    }

    public void updateMenuItem() {
       this.addMenuItem();
    }

    public void deleteMenuItem() {
        String itemName = InputHandler.getStringInput("\nEnter item name: ");
        this.requestPath = this.requestPath + "/deleteMenuItem/";
        
        char category = 'a'; 
        boolean deleteFromAllCategory = InputHandler.getBooleanInput(itemName + " delete the item from all categories? Enter 'no' to delete from specific category, 'yes' to delete from all categories");
    
        if (!deleteFromAllCategory) {
            do {
                category = Character.toLowerCase(InputHandler.getCharInput("Enter Food Category: \nb - breakfast\nl - lunch\ns - snacks\nd - dinner\n"));
            } while (!isValidCategory(category));
        }
        
        MenuItem menuItem = new MenuItem(itemName, category); 
        String jsonRequest = jsonConverter.convertObjectToJson(menuItem, this.requestPath); 
        System.out.println("JSON Request: " + jsonRequest); 
        this.requestPath = "/admin"; 
    }

    public void viewAllMenuItems() {
        this.requestPath = this.requestPath + "/viewAllMenuItems";
        String jsonRequest = jsonConverter.convertObjectToJson(null, this.requestPath); 
        System.out.println("JSON Request: " + jsonRequest);

        // sendRequestToServer(jsonRequest);
    }
}