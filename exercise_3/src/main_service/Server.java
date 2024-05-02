package main_service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static final int TCP_PORT = 8113;
    public static List<String> quotes = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println("Main server starting...");

        // For testing
        quotes.add("Nikola Tesla - \"I don't care that they stole my idea, I care that they don't have any of their own.\"");
        quotes.add("Steve Jobs - \"If today were the last day of my life, would I want to do what I am about to do today?\"");

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
}
