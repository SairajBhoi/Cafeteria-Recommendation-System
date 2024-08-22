package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import server.controller.ClientRequestRouter;


public class ServerRunnable implements Runnable {
    protected Socket clientSocket = null;

    public ServerRunnable(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
    
            String clientRequest;
            while((clientRequest = inputReader.readLine())!= null) {
                System.out.println("Received: " +  clientRequest);
                String response = handleClientRequest(clientRequest);
                out.println(response);
            }
       
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    private String handleClientRequest(String clientRequest) throws IOException {
       
        ClientRequestRouter clientRequestRouter = new ClientRequestRouter();
        String response= clientRequestRouter.route(clientRequest);
		return response;

    }

    private void closeResources() {
        if (clientSocket != null && !clientSocket.isClosed()) {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
