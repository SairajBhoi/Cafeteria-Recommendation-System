package server;

public class Server {
    public static void main(String[] args) {
        System.out.println("Hello Server!!");
        SocketServer server = new SocketServer();
        server.runServer();
    }
}