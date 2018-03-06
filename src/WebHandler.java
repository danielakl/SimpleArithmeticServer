import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class WebHandler implements Handler {
    private final Socket webSocket;

    WebHandler(Socket webSocket) {
        this.webSocket = webSocket;
    }

    @Override
    public void run() {
        try (InputStream reader = webSocket.getInputStream();
             OutputStream writer = webSocket.getOutputStream()) {

            Scanner headerScanner = new Scanner(reader).useDelimiter("\\n");
            String header = headerScanner.hasNext() ? headerScanner.next() : "";

        writer.write(("HTTP/1.1 200 OK Content-Type: text/html\n\n" +
                "<html>" +
                    "<head>" +
                        "<title>Socket Server</title>" +
                    "</head>" +
                    "<body>" +
                        "<h1>Welcome to the Server</h1>" +
                        "<ul>" +
                            "<li>" + header + "</li>" +
                        "</ul>" +
                    "</body>" +
                "</html>").getBytes());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
