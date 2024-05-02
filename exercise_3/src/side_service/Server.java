package side_service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static final int TCP_PORT = 8114;
    public static List<String> quotes = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println("Side server starting...");

        initQuotes();

        try {
            ServerSocket serverSocket = new ServerSocket(TCP_PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ServerThread(socket)).start();
            }
        }  catch (SocketTimeoutException e) {
            System.err.println("Accept timed out: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void initQuotes() {
        quotes.add("Albus Dumbledore - \"It does not do to dwell on dreams and forget to live.\"");
        quotes.add("Gandalf - \"All we have to decide is what to do with the time that is given to us.\"");
        quotes.add("Yoda - \"Do, or do not. There is no try.\"");
        quotes.add("Obi-Wan Kenobi - \"The Force will be with you, always.\"");
        quotes.add("Harry Potter - \"It is our choices, Harry, that show what we truly are, far more than our abilities.\"");
        quotes.add("Hermione Granger - \"Fear of a name increases fear of the thing itself.\"");
    }
}
