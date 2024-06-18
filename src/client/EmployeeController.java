package client;

public class EmployeeController {
		 private Employee employee;
		 private String userName;

	    public EmployeeController(String userName, String userId) {
	        this.userName = userName;
	        employee = new Employee(userName, userId);
	        
	        
	    }

	  public void runHomePage() throws Exception {
	        System.out.println("\nWelcome "+ userName);
	        run();
	    }

	  private void run() throws Exception {
	        while (true) {
	            displayOptions();
	            int choice = InputHandler.getIntegerInput("Enter your choice: ");
	            processOption(choice);
	            if (choice == 8) {
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
	            	employee.viewRecommendation();
	                break;
	            case 3:
	            	employee.ViewFeedbackonFoodItem();
	                break;
	            case 4:
	            	 employee.addFeedbackonFooditem();
	                 break;
	            case 5:
	            	 employee.viewNotification();
	                 break;
	            case 6:
	            	  employee.viewChefRollout();
	            	  break;
	            	  
	            case 7:
	            	 System.out.println("vote to rollout");
	            	 employee.votefortomorrowsMenu();
	                 break;	  
	            case 8:
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
	        System.out.println("\n1. View Menu menu.");
	        System.out.println("2. View Recommendation");
	        System.out.println("3. View  Feedback on Food item.");
	        System.out.println("4. Add Feedback on Food item.");
	        System.out.println("5. View Notifications.");
	        System.out.println("6. roolout view");
	        System.out.println("7. vote to rollout");
	        System.out.println("8. logout");
	        
	    }
	


}
