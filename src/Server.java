import java.io.*;
import java.net.*;

final class Server {
    public static void main(String[] args) {
        final int PORT = 1250;

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
                new Thread(new ClientHandler(clientSocket)).start();
            } while(true);
        } catch (IOException ioe) {
            System.err.println("Failed to create a server socket on port " + PORT + ".");
            ioe.printStackTrace();
        }
    }
}
