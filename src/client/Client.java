package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String hostName = "localhost";
        int portNumber = 5700;

        try (Socket clientSocket = new Socket(hostName, portNumber);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            // System.out.print("Enter User ID: ");
            // String userID = scanner.nextLine();

            // System.out.print("Enter Password: ");
            // String password = scanner.nextLine();

            // // Send userID and password to server
            // out.println(userID);
            // out.println(password);

            // Receive response from server
            String serverResponse = in.readLine();
            System.out.println("Server Says: " + serverResponse);

        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + hostName);
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
    }
}
