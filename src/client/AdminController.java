package client;

public class AdminController {
    private Admin admin;
    private String userName;

    public AdminController(String userName, String userId) {
        this.userName = userName;
        admin = new Admin(userName, userId);
    }

    public void runHomepage() throws Exception {
        System.out.println("\nWelcome " + this.userName);
        run();
    }

    private void run() throws Exception {
        while (true) {
            displayOptions();
            int choice = InputHandler.getIntegerInput("Enter your choice: ");
            processOption(choice);
            if (choice == 5) {
                break;
            }
        }
    }

    private void processOption(int choice) throws Exception {
        switch (choice) {
            case 1:
                admin.addMenuItem();
                break;
            case 2:
                admin.deleteMenuItem();
                break;
            case 3:
                admin.updateMenuItem();
                break;
            case 4:
                admin.viewAllMenuItems();
                break;
            case 5:
                System.out.println("Logging out...");
                admin.logout();
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

    private void displayOptions() {
        System.out.println("\n1. Add Menu Item");
        System.out.println("2. Delete Menu Item");
        System.out.println("3. Update Menu Item");
        System.out.println("4. View Menu Items");
        System.out.println("5. Logout");
    }
}
