package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static BufferedReader in;
    private static PrintWriter out;
    private static Socket socket = null;
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5700;

    public static String requestServer(String requestToServer) throws IOException {
        try {
            if (socket == null || socket.isClosed()) {
                socket = openSocket(SERVER_PORT);
            }
            openConnection(socket);
            out.println(requestToServer);

            String serverResponse = in.readLine();

            return serverResponse;  

        } catch (IOException ex) {
            throw new IOException("Error communicating with server: " + ex.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Socket openSocket(int port) throws IOException {
        return new Socket(SERVER_ADDRESS, port);
    }

    private static void closeSocket(Socket socket) {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void openConnection(Socket socket) throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public static void closeConnection() {
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
