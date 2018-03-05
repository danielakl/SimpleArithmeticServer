import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public final class ClientHandler implements Runnable {
    private final Socket clientSocket;

    ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
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
            System.err.println("Failed to read or write from client socket.");
            ioe.printStackTrace();
        }
    }
}
