package client;

import utils.Utils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class OutputThread implements Runnable {

    private final Socket socket;
    private PrintWriter writer;
    private Scanner keyboard;

    public OutputThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
            keyboard = new Scanner(System.in);

            while(true) {
                String message = keyboard.nextLine().trim();
                if( message == null ) continue;
                writer.println(message);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            Utils.closeResource(keyboard);
            Utils.closeResource(writer);
        }

    }

    public PrintWriter getWriter() {
        return writer;
    }
}
