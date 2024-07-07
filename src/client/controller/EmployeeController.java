package client.controller;

import client.model.Employee;
import client.util.InputHandler;

public class EmployeeController {
    private Employee employee;
    private String userName;

    public EmployeeController(String userName, String userId) {
        this.userName = userName;
        employee = new Employee(userName, userId);
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
                employee.viewMainMenu();
                break;
            case 2:
            	employee.viewUserProfile();
            	break;
            case 3:
            	employee.updateUserProfile();
            	break;
            case 4:
                employee.viewRecommendation();
                break;
            case 5:
                employee.viewFeedbackOnFoodItem();
                break;
            case 6:
                employee.addFeedbackOnFoodItem();
                break;
            case 7:
                employee.viewNotification();
                break;
            case 8:
                employee.viewUnseenNotification();
                break;
            case 9:
                employee.viewChefRollout();
                break;
            case 10:
                System.out.println("Vote for tomorrow's menu");
                employee.voteForTomorrowsMenu();
                break;
            case 11:
                employee.viewTodaysMenu();
                break;
            case 12:
                System.out.println("Logging out...");
                employee.logout(); 
                break;
            default:
                System.out.println("Invalid choice");
                run();
                break;
        }
    }

    private void displayOptions() {
        System.out.println("\n1. View Menu");
        System.out.println("2. View Profile");
        System.out.println("3. Update User Profile");
        System.out.println("4. View Recommendation");
        System.out.println("5. View Feedback on Food item");
        System.out.println("6. Add Feedback on Food item");
        System.out.println("7. View Notifications");
        System.out.println("8. View Unseen Notifications");
        System.out.println("9. View Chef Rollout");
        System.out.println("10. Vote for tomorrow's menu");
        System.out.println("11. View Today's Menu");
        System.out.println("12. Logout");
    }
}
