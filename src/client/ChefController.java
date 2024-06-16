package client;


	public class ChefController {
		 private Chef chef;
		 private String userName;

	    public ChefController(String userName, String userId) {
	        this.userName = userName;
	        chef = new Chef(userName, userId);
	        
	        
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
	            if (choice == 5) {
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
	                chef.viewFeedback();
	                break;
	            case 4:
	            	 chef.finalMenu();
	                break;
	            case 5:
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
	        System.out.println("3. View Feedback on food item");
	        System.out.println("4. create final Menu");
	        System.out.println("4. Generate Report");
	        System.out.println("5. Logout");
	    }
	}

