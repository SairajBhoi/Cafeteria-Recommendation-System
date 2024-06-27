package client;

import client.auth.Encryption;
import client.auth.Authenticator;
import client.util.InputHandler;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("WELCOME !!!!");

            Authenticator authenticator = new Authenticator();

            System.out.println("Please login !!!!");

            String userId = InputHandler.getStringInput("Enter User ID");
            Encryption encrypt = new Encryption();
            String password = InputHandler.getStringInput("Enter the password");
            String userPassword = encrypt.encrypt(password);
            
          
            

            boolean authenticationStatus = authenticator.authenticate(userId, userPassword);
            if (!authenticationStatus) {
                System.out.println("Authentication failed. Please try again.");
            } else {
                System.out.println("--------------------------------------");
            }
        } catch (IOException e) {
            System.err.println("IO ERROR.");
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }
}
