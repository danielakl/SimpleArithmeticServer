import java.io.*;
import java.net.Socket;

public class WebHandler implements Handler {
    private final Socket webSocket;

    WebHandler(Socket webSocket) {
        this.webSocket = webSocket;
    }

    @Override
    public void run() {
        try (InputStream reader = webSocket.getInputStream();
             OutputStream writer = webSocket.getOutputStream()) {

        writer.write(("HTTP/1.1 200 OK\n\n" +
                "<h1>Welcome to the Server</h1>").getBytes());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
