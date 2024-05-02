package server;

import server.messages.Message;
import utils.Utils;

import java.io.*;
import java.net.Socket;
import java.util.Iterator;

public class ServerThread implements Runnable{

    private final Socket socket;
    private String username;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            // Username
            while(true) {
                writer.println("Enter your username:");
                username = reader.readLine();

                ServerRepository.usernamesLock.lock();
                if(ServerRepository.usernames.contains(username)) {
                    ServerRepository.usernamesLock.unlock();
                    writer.println("Username already taken. Try again.");
                } else {
                    ServerRepository.usernames.add(username);
                    ServerRepository.usernamesLock.unlock();

                    writer.println("Welcome " + username + "!");
                    break;
                }
            }

            ServerRepository.clients.add(this.socket);

            // Print history
            ServerRepository.historyLock.lock();
            String historyString = ServerRepository.history.toString();
            ServerRepository.historyLock.unlock();
            writer.println(historyString);

            // Chat
            while(true) {
                String text = reader.readLine();
                Message message = new Message(username, text);

                ServerRepository.historyLock.lock();
                ServerRepository.history.add(message);
                ServerRepository.historyLock.unlock();

                broadcastMessage(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Utils.closeResource(writer);
            Utils.closeResource(reader);
            Utils.closeResource(socket);
        }
    }

    private void broadcastMessage(Message message) {
        // iterator takes an immutable snapshot of the list
        Iterator<Socket> iterator = ServerRepository.clients.iterator();

        while(iterator.hasNext()) {
            Socket client = iterator.next();

            // we cannot remove it because we are iterating over a copy of the list
            if(client.isClosed())
                continue;

            try  {
                PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
                writer.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
