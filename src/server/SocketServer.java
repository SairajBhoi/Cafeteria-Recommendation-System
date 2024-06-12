package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    private static final int portNumber = 5700;

    public void runServer() {
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Server started and listening on port " + portNumber);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket.getInetAddress());

                // Handle the client connection in a new thread
                ServerRunnable serverRunnable = new ServerRunnable(clientSocket);
                new Thread(serverRunnable).start();
            }
        } catch (IOException e) {
            System.err.println("Server Socket Error: " + e.getMessage());
        }
    }
}
