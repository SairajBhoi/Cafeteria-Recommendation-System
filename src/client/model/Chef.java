package client.model;

import client.service.RolloutHandler;

import java.io.IOException;

import client.service.FeedbackHandler;
//import client.service.MenuRollout;

public class Chef extends User {
	//private MenuRollout menurollout;
	
	
	public Chef(String userName,String userId){
		   this.setUserRole("CHEF");
		    this.setUserName(userName);
	        this.setUserId(userId);
	        //menurollout= new MenuRollout(this.getUserRole());
		System.out.print("Constructor");
	}
	
	public void menurollout() {

		RolloutHandler chef= new RolloutHandler(this.getUserRole());
		chef.createAllChefMenuRollouts();
		System.out.print("after");
	
		//menurollout.executeMenuRollout();
		
	}
	


	
	public void viewRecommendation() {
		RolloutHandler chef= new RolloutHandler(this.getUserRole());
	
			chef.recommendation();
	
	System.out.println("Recommendation engine");
	 
	}
	
	public void FinalVoteMenu() {
		System.out.println("Final Result Vote Menu");
		RolloutHandler chefrollout= new RolloutHandler(this.getUserRole());
		chefrollout.getFinalVoteMenu();
		
	
		
	}
	
	
	public void viewFinalMenuDecided() {
		System.out.println("Final Menu after Rollout");
		RolloutHandler chefrollout= new RolloutHandler(this.getUserRole());
		chefrollout.getFinalDecidedMenu();
		
		System.out.println("Final Menu");
		
	}
	
	
	public void addFinalMenuafterRollout() {
		RolloutHandler chef= new RolloutHandler(this.getUserRole());
		chef.createAllFinalDecidedChefMenu();
		System.out.println("adding");
	}
	
	
		public void ViewFeedbackonFoodItem(){
			 FeedbackHandler feedbackHandler=new FeedbackHandler (this.getUserRole());
			 System.out.print("view fooditem");
		    feedbackHandler.viewFeedbackonFoodItem();

		}
		

	
	void generateReport() {
		System.out.println("generate report");
	}
	
	
	
	
	
	
	
}