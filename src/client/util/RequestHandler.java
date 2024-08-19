package client.util;

import java.io.IOException;
import client.Client;

public class RequestHandler {

    public String sendRequestToServer(String jsonRequest) {
        try {
            String jsonResponse = Client.requestServer(jsonRequest);
            PrintOutToConsole.printToConsole(jsonResponse);
            return jsonResponse;
        } catch (IOException e) {
            System.err.println("Error sending request to server: " + e.getMessage());
            return null; 
        }
    }
}
