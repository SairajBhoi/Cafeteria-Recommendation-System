package client;

import java.io.IOException;

import client.auth.Authenticator;
import client.util.InputHandler;

public class Main {

	public static void main(String args []) {
    
		  try{
	            System.out.println("WELCOME !!!!");
	            
	            
	            Authenticator authenticator = new Authenticator();
	            
	            System.out.println("please login !!!!");
	            
	            String userId = InputHandler.getStringInput("Enter User ID");
	            String userPassword = InputHandler.getStringInput("Enter user Password:");
	            
	            
	            boolean authenticationStatus = authenticator.authenticate(userId, userPassword);
	            if (!authenticationStatus) {
                    System.out.println("Authentication failed. Please try again.");
                }
	        } catch (IOException e) {
	            System.err.println("IO ERROR.");
	        } catch (InterruptedException e) {
	            System.err.println("INTERRUPTION ERROR.");
	        } catch (Exception e) {
	            System.err.println("ERROR: " + e.getMessage());
	        }
	    }
		
	
	
	
}
