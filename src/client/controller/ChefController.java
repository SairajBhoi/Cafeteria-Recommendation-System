package client.controller;

import client.model.Chef;
import client.util.InputHandler;

public class ChefController {
    private Chef chef;
    private String userName;

    public ChefController(String userName, String userId) {
        this.userName = userName;
        chef = new Chef(userId, userName); 
    }

    public void runHomePage() throws Exception {
        System.out.println("\nWelcome " + userName);
        run();
    }

    private void run() throws Exception {
        while (true) {
            displayOptions();
            int choice = InputHandler.getIntegerInput("Enter your choice: ");
            processOption(choice);
            if (choice == 11) {
                break;
            }
        }
    }

    private void processOption(int choice) throws Exception {
        switch (choice) {
        case 1:
        	chef.viewMainMenu();
        	break;
            case 2:
                chef.menurollout();
                break;
            case 3:
                chef.viewRecommendation();
                break;
            case 4:
                chef.ViewFeedbackonFoodItem();
                break;
            case 5:
                chef.FinalVoteMenu();
                break;
            case 6:
                chef.addFinalMenuafterRollout();
                break;
            case 7:
                chef.viewFinalMenuDecided();
                break;
            case 8:
                chef.viewChefDiscardList();
                break;
            case 9:
            	chef.generateDiscardList();
            	break;
            case 10: 
            	 chef.viewFeedbackOnDiscardList();
            	 break;
            case 11:
                System.out.println("Logging out...");
                chef.logout();
                break;
            default:
                System.out.println("Invalid choice");
                run();
                break;
        }
    }

    private void displayOptions() {
    	System.out.println("\n1. View Main Menu");
    	System.out.println("2. Rollout menu for tomorrow.");
        System.out.println("3. View Recommendation from Engine.");
        System.out.println("4. View Feedback on food item.");
        System.out.println("5. View rollout.");
        System.out.println("6. Add Decided Menu To be prepared tomorrow.");
        System.out.println("7. View Decided Menu To be prepared tomorrow.");
        System.out.println("8. View Chef DiscardList");
        System.out.println("9. Generate DiscardList");
        System.out.println("10. Feedback on Discard List");
        System.out.println("11. Logout");
    }
}
