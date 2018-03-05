import java.io.*;
import java.net.*;
import java.util.Scanner;

final class Client {
    public static void main(String[] args) {
        final int PORT = 1250;

        /* Read user input from CLI */
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the IP or hostname of the server to connect to: ");
        String server = scanner.nextLine();

        try (Socket socket = new Socket(server, PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connection established.");

            /* Reads instructions from server */
            String instruction1 = reader.readLine();
            String instruction2 = reader.readLine();
            System.out.println(instruction1 + "\n" + instruction2);

            /* Read message from user */
            String line = scanner.nextLine();
            while (!line.equals("")) {
                writer.println(line);  // Sends message to server
                String response = reader.readLine();  // Receives response from server.
                System.out.println("From server: " + response);
                line = scanner.nextLine();
            }

        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
