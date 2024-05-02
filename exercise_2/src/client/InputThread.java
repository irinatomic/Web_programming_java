package client;

import utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class InputThread implements Runnable {

    private final Socket socket;
    private BufferedReader reader;

    public InputThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while(true){
                String message = reader.readLine();
                if( message == null) continue;
                System.out.println(message);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            Utils.closeResource(reader);
        }
    }

}
