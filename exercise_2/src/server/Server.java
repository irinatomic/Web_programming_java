package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(9001)) {

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected!");
                Thread serverThread = new Thread(new ServerThread(clientSocket));
                serverThread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
