package client.model;

import client.service.ChefRolloutHandler;
import client.service.FeedbackHandler;
import client.service.MenuRollout;

public class Chef extends User {
	private MenuRollout menurollout;
	
	
	public Chef(String userName,String userId){
		   this.setUserRole("CHEF");
		    this.setUserName(userName);
	        this.setUserId(userId);
	        menurollout= new MenuRollout(this.getUserRole());
		System.out.print("Constructor");
	}
	
	public void menurollout() {

		ChefRolloutHandler chef= new ChefRolloutHandler(this.getUserRole());
		chef.createAllChefMenuRollouts();
		System.out.print("after");
	
		//menurollout.executeMenuRollout();
		
	
		
	}
	


	
	public void viewRecommendation() {
		ChefRolloutHandler chef= new ChefRolloutHandler(this.getUserRole());
		chef.recommendation();
	System.out.println("Recommendation engine");
	 
	}
	
	public void viewFinalMenu() {
		ChefRolloutHandler chef= new ChefRolloutHandler(this.getUserRole());
		chef.getFinalMenu();
		
		System.out.println("Final Menu");
		
	}
	
	;
		public void ViewFeedbackonFoodItem(){
			 FeedbackHandler feedbackHandler=new FeedbackHandler (this.getUserRole());
		    feedbackHandler.viewFeedbackonFoodItem();

		}
		
	
	
	
	void generateReport() {
		
		
		System.out.println("generate report");
	}
	
	
	
	
	
	
	
}