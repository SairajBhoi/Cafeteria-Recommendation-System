package client;

import java.io.IOException;

import client.auth.Authenticator;
import client.auth.Encryption;
import client.util.InputHandler;

public class Main {

	private static String password;

	public static void main(String args []) {
    
		  try{
	            System.out.println("WELCOME !!!!");
	            
	            
	            Authenticator authenticator = new Authenticator();
	            
	            System.out.println("please login !!!!");
	            
	            String userId = InputHandler.getStringInput("Enter User ID");
	            Encryption encrypt= new Encryption();
	            String password=InputHandler.getStringInput("Enter the password");
	            String userPassword = encrypt.encrypt(password);
	            
	            
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
