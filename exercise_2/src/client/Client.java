package client;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 9001);

            Thread input = new Thread(new InputThread(socket));
            Thread output = new Thread( new OutputThread(socket));

            input.start();
            output.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
