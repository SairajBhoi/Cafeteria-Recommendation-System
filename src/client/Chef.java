package client;

public class Chef extends User {
	private MenuRollout menurollout;
	Chef(){
		MenuRollout menurollout= new MenuRollout(this.getUserId());
	}
	
	void Menurollout() {
		menurollout.executeMenuRollout();
	}
	
	
	
}
