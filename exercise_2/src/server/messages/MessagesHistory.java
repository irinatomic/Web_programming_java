package server.messages;

import java.util.ArrayList;

public class MessagesHistory extends ArrayList<Message> {

    private static final int MAX_SIZE = 100;

    @Override
    public boolean add(Message message) {
        if (size() >= MAX_SIZE) {
            remove(0);                   // remove the oldest message
        }
        return super.add(message);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Message message : this) {
            output.append(message).append("\n");
        }
        return output.toString();
    }
}

