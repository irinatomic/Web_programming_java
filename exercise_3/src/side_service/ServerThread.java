package side_service;

import main_service.utils.Utils;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class ServerThread implements Runnable {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ServerThread(Socket socket) {
        this.socket = socket;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String requestLine = in.readLine();

            // Parse request line
            System.out.println("Received request: " + requestLine);
            StringTokenizer stringTokenizer = new StringTokenizer(requestLine);
            String method = stringTokenizer.nextToken();
            String path = stringTokenizer.nextToken();

            if(method.equals("GET") && path.equals("/quote-of-day")) {
                int randomIndex = (int) (Math.random() * Server.quotes.size());
                String quote = Server.quotes.get(randomIndex);
                String json = "{\"quote\": \"" + quote + "\"}";
                out.println(json);
            } else {
                out.println("Invalid request");
            }

            closeResources();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    private void closeResources() {
        Utils.closeResource(out);
        Utils.closeResource(in);
        Utils.closeResource(socket);
    }
}
