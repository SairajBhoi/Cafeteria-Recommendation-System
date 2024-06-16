package client;

import java.io.IOException;

public class Main {

	public static void main(String args []) {
    
		  try{
	            System.out.println("WELCOME !!!!");
	            
	            
	            Authenticator authenticator = new Authenticator();
	            
	            System.out.println("please login !!!!");
	            
	            String userId = InputHandler.getStringInput("Enter User ID");
	            String userPassword = InputHandler.getStringInput("Enter user Password:");
	            
	            
	            authenticator.authenticate(userId, userPassword);
	          
	        } catch (IOException e) {
	            System.err.println("IO ERROR.");
	        } catch (InterruptedException e) {
	            System.err.println("INTERRUPTION ERROR.");
	        } catch (Exception e) {
	            System.err.println("ERROR: " + e.getMessage());
	        }
	    }
		
	
	
	
}
