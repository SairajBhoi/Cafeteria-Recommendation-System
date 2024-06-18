package client;

public class Chef extends User {
	private MenuRollout menurollout;
	
	
	Chef(String userName,String userId){
		   this.setUserRole("CHEF");
		    this.setUserName(userName);
	        this.setUserId(userId);
	        menurollout= new MenuRollout(this.getUserRole());
		System.out.print("Constructor");
	}
	
	void menurollout() {

		ChefRolloutHandler chef= new ChefRolloutHandler(this.getUserRole());
		chef.createAllChefMenuRollouts();
		System.out.print("after");
	
		//menurollout.executeMenuRollout();
		
	}
	


	void viewRecommendation() {
	System.out.println("Recommendation engine");
	}
	
	void finalMenu() {
		System.out.println("Final Menu");
		
	}
	
	void viewFeedback() {
		System.out.println("viewFeedback");
		
	}
	
	
	void generateReport() {
		
		
		System.out.println("generate report");
	}
	
	
	
	
	
	
	
}