package main_service;

import main_service.http.request.*;
import main_service.http.response.Response;
import main_service.utils.Utils;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class ServerThread implements Runnable {

    private RequestHandler requestHandler;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.requestHandler = new RequestHandler();

        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {

            String requestLine = in.readLine();

            if(requestLine.contains("favicon.ico")) {
                closeResources();
                return;
            }

            // Parse request line
            System.out.println("Received request: " + requestLine);
            StringTokenizer stringTokenizer = new StringTokenizer(requestLine);
            String method = stringTokenizer.nextToken();
            String path = stringTokenizer.nextToken();

            // Read headers
            Map<String, String> headers = new HashMap<>();
            String headerLine;
            while ((headerLine = in.readLine()) != null && !headerLine.isEmpty()) {
                String[] headerParts = headerLine.split(": ");
                if (headerParts.length == 2)
                    headers.put(headerParts[0], headerParts[1]);
            }

            // Check if it's a POST request and has a body
            String body = "";
            if (method.equals("POST") && headers.containsKey("Content-Length")) {
                int contentLength = Integer.parseInt(headers.get("Content-Length"));
                StringBuilder bodyBuilder = new StringBuilder();
                char[] buffer = new char[contentLength];
                int bytesRead = in.read(buffer, 0, contentLength);
                if (bytesRead == contentLength) {
                    bodyBuilder.append(buffer);
                }
                body = bodyBuilder.toString();

                System.out.println("Received body: " + body);
            }

            Request request = new Request(HttpMethod.valueOf(method), path, body);

            // handle the request and send a response
            Response response = requestHandler.handle(request);
            out.println(response.getResponseString());

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
        Utils.closeResource(clientSocket);
    }
}
