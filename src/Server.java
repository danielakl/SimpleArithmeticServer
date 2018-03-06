import java.io.*;
import java.net.*;

final class Server {
    private static final boolean WEB_SERVER = true;
    private static final int PORT = (WEB_SERVER) ? 8080 : 1250;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Waiting for connections...");
            Socket clientSocket;
            do {
                try {
                    clientSocket = serverSocket.accept();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    return;
                }
                System.out.println("Client connecting.");
                new Thread((WEB_SERVER) ? new WebHandler(clientSocket) : new ClientHandler(clientSocket)).start();
            } while(true);
        } catch (IOException ioe) {
            System.err.println("Failed to create a server socket on port " + PORT + ".");
            ioe.printStackTrace();
        }
    }
}
