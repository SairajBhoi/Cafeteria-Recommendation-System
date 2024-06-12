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
            handleClientRequest();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    private void handleClientRequest() throws IOException {
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        String userId = inputReader.readLine();
        String password = inputReader.readLine();

        UserDAO userDAO = new UserDAO(DatabaseConnection.getConnection());
        AuthenticationService authService = new AuthenticationService(userDAO);
        UserDetail user = authService.authenticate(userId, password);

        if (user != null) {
            out.println("Authentication successful. Welcome, " + user.getUsername() + "!");
            RoleBasedService roleService = new RoleBasedService();
            roleService.handleRole(user, inputReader, out);
        } else {
            out.println("Authentication failed.");
        }

        inputReader.close();
        out.close();
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
