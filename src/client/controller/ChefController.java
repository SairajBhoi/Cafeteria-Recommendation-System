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
            if (choice == 10) {
                break;
            }
        }
    }

    private void processOption(int choice) throws Exception {
        switch (choice) {
            case 1:
                chef.menurollout();
                break;
            case 2:
                chef.viewRecommendation();
                break;
            case 3:
                chef.ViewFeedbackonFoodItem();
                break;
            case 4:
                chef.FinalVoteMenu();
                break;
            case 5:
                chef.addFinalMenuafterRollout();
                break;
            case 6:
                chef.viewFinalMenuDecided();
                break;
            case 7:
                chef.viewChefDiscardList();
                break;
            case 8:
            	chef.generateDiscardList();
            	break;
            case 9: 
            	 chef.viewFeedbackOnDiscardList();
            	 break;
            case 10:
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
        System.out.println("\n1. Rollout menu for tomorrow.");
        System.out.println("2. View Recommendation from Engine.");
        System.out.println("3. View Feedback on food item.");
        System.out.println("4. View rollout.");
        System.out.println("5. Add Decided Menu To be prepared tomorrow.");
        System.out.println("6. View Decided Menu To be prepared tomorrow.");
        System.out.println("7. View Chef DiscardList");
        System.out.println("8. Generate DiscardList");
        System.out.println("9. Feedback on Discard List");
        System.out.println("10. Logout");
    }
}
