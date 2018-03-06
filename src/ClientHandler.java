import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public final class ClientHandler implements Handler {
    private final Socket clientSocket;
    private static ScriptEngineManager mgr = new ScriptEngineManager();
    private static ScriptEngine engine = mgr.getEngineByName("JavaScript");

    ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        System.out.println("Client connected.");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

            /* Send instructions to client */
            writer.println("You've successfully connected to the server.");
            writer.println("Give me a simple arithmetic expression to calculate.");

            /* Receives data from client */
            String line = reader.readLine();  // Receives a line of text.
            while (line != null) {
                // Respond to client with result.
                try {
                    writer.println("Result of '" + line + "' is " + engine.eval(line).toString());
                } catch (ScriptException e) {
                    e.printStackTrace();
                }

                // Read another line.
                line = reader.readLine();
            }
        } catch (IOException ioe) {
            System.err.println("Failed to read or write from client socket.");
            ioe.printStackTrace();
        }
    }
}
