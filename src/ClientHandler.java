import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ClientHandler implements Runnable {
    private final Socket clientSocket;

    ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

            /* Send instructions to client */
            writer.println("You've successfully connected to the server.");
            writer.println("Give me a simple arithmetic expression to calculate.");

            /* Receives data from client */
            String line = reader.readLine();  // Receives a line of text.
            while (line != null) {
                // Retrieve numbers.
                Pattern pattern = Pattern.compile("[0-9]+");
                Matcher matcher = pattern.matcher(line);
                List<Integer> values = new ArrayList<>();
                while (matcher.find()) {
                    values.add(Integer.parseInt(matcher.group()));
                }

                // Process operators.
                pattern = Pattern.compile("[*/+-]");
                matcher = pattern.matcher(line);
                double result = (values.size() >= 1) ? values.get(0) : 0.0;
                for (int i = 1; i < values.size(); i++) {
                    if (matcher.find()) {
                        try {
                            switch (matcher.group()) {
                                case "+":
                                    result += values.get(i);
                                    break;
                                case "-":
                                    result -= values.get(i);
                                    break;
                                case "*":
                                    result *= values.get(i);
                                    break;
                                case "/":
                                    result /= values.get(i);
                                    break;
                                default:
                                    break;
                            }
                        } catch (ArithmeticException ae) {
                            writer.println("Error " + ae.getMessage());
                        }
                    }
                }

                // Respond to client with result.
                writer.println("Result of '" + line + "' is " + result);

                // Read another line.
                line = reader.readLine();
            }
        } catch (IOException ioe) {
            System.err.println("Failed to read or write from client socket.");
            ioe.printStackTrace();
        }
    }
}
