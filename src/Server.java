import java.io.*;
import java.net.*;

final class Server {
    public static void main(String[] args) {
        final int PORT = 1250;

        try (ServerSocket serverSocket = new ServerSocket(PORT);
             Socket clientSocket = serverSocket.accept();
             BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {
            System.out.println("Waiting for connections...");

            /* Send instructions to client */
            writer.println("You've successfully connected to the server.");
            writer.println("Write a message and we will repeat it to you, we know this is truly revolutionary!");

            /* Receives data from client */
            String line = reader.readLine();  // Receives a line of text.
            while (line != null) {
                System.out.println("A client wrote: " + line);
                writer.println("You wrote: " + line);  // Repeat message to client.
                line = reader.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
