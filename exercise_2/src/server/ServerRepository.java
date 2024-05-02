package server;

import server.messages.MessagesHistory;

import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public final class ServerRepository {

    public static Set<String> censured = Set.of("stupid", "dumb", "idiot", "fool");
     public static List<Socket> clients = new CopyOnWriteArrayList<>();
    public static Set<String> usernames = new HashSet<>();
    public static MessagesHistory history = new MessagesHistory();

    public static Lock usernamesLock = new ReentrantLock();
    public static Lock historyLock = new ReentrantLock();
}
