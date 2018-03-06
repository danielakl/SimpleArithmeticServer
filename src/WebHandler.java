import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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

            List<String> headers = new ArrayList<>();
            Scanner headerScanner = new Scanner(reader);
            String header = headerScanner.nextLine();
            while (!header.equals("")) {
                headers.add(header);
                header = headerScanner.nextLine();
            }

            StringBuilder sb = new StringBuilder();
            for (String s : headers) {
                sb.append("<li>");
                sb.append(s);
                sb.append("</li>");
            }

            writer.write(("HTTP/1.1 200 OK Content-Type: text/html\n\n" +
                    "<html>" +
                    "<head>" +
                    "<title>Socket Server</title>" +
                    "</head>" +
                    "<body>" +
                    "<h1>Welcome to the Server</h1>" +
                    "<ul>" +
                    sb.toString() +
                    "</ul>" +
                    "</body>" +
                    "</html>").getBytes());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
