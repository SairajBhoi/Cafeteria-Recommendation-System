package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


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
            while((clientRequest = in.readLine())!= null) {
                System.out.println("Received: " +  clientRequest);
                String response = handleClientRequest(request);
                out.println(response);
            }
       
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    private void handleClientRequest() throws IOException {
       
}
        // UserDAO userDAO = new UserDAO(DatabaseConnection.getConnection());
        // AuthenticationService authService = new AuthenticationService(userDAO);
        // UserDetail user = authService.authenticate(userId, password);
        ClientRequestRouter clientRequestRouter = new ClientRequestRouter();
        clientRequestRouter.route(request);

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
