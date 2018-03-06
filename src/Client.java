import java.io.*;
import java.net.*;
import java.util.Scanner;

final class Client {
    public static void main(String[] args) {
        final int PORT = 1250;

        /* Read user input from CLI */
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the IP or hostname of the server to connect to: ");
        String server;

        do {
            server = scanner.nextLine();
            if (!server.equalsIgnoreCase("exit")) {
                try (Socket socket = new Socket(server, PORT);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

                    /* Reads instructions from server */
                    String welcome = reader.readLine();
                    String instruction = reader.readLine();
                    System.out.println(welcome);
                    System.out.println(instruction);

                    /* Read message from user */
                    System.out.print("Expression: ");
                    String line = scanner.nextLine();
                    while (!line.equals("")) {
                        writer.println(line);  // Sends message to server
                        String response = reader.readLine();  // Receives response from server.
                        System.out.println("\n" + response);
                        System.out.print("Expression: ");
                        line = scanner.nextLine();
                    }

                } catch (IOException ioe) {
                    System.err.println(ioe.getMessage() + ", Type exit to quit.");
                }
            }
        } while (!server.equalsIgnoreCase("exit"));
    }
}
