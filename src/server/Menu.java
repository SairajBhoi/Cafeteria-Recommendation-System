import java.util.Scanner;
import java.util.List;

public class Menu {
    private MenuItem menuItem;
    private Scanner scanner;

    Menu() {
        menuItem = new MenuItem();
        scanner = new Scanner(System.in);
    }
   
    public void addMenuItem() {
        String messageToUser;
        
        messageToUser = "Enter the Food Item name: ";
        String itemName = InputHandler.getStringInput(messageToUser);

        messageToUser = "Enter the Food Item price for " + itemName + ": ";
        float itemPrice = InputHandler.getFloatInput(messageToUser);

        messageToUser = itemName + " is Available: Enter 'no' for not available, 'yes' for available: ";
        boolean isItemAvailable = InputHandler.getBooleanInput(messageToUser);

        messageToUser = "Enter Food Category: \n1 - breakfast\n2 - lunch\n3 - snacks\n4 - dinner\n";
        int category = InputHandler.getIntegerInput(messageToUser);

        menuItem.setItemName(itemName);
        menuItem.setItemPrice(itemPrice);
        menuItem.setItemAvailable(isItemAvailable);
        menuItem.setItemCategory(category);
    }

    public void updateMenuItem() {
        // Add implementation to update a menu item
    }

    public void deleteMenuItem() {
        // Add implementation to delete a menu item
    }

    public List<MenuItem> getAllMenuItems() {
        // Add implementation to retrieve all menu items
        return new ArrayList<>(); // Placeholder return statement
    }
}
