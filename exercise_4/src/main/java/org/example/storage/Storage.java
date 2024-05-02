package org.example.storage;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// TODO: use the locks
public final class Storage {

    // for the customer
    public static Map<String, ChoiceForWeek> orders = new HashMap<>();
    public static Lock ordersLock = new ReentrantLock();

    // for the manager
    public static Map<String, Map<String, Integer>> overview = new HashMap<>();
    public static Lock overviewLock = new ReentrantLock();

    // Needs the servletContext from the 1st initialized servlet
    // we need it in order to find the path to the files in WEB-INF
    public static void initialiseOverview(ServletContext servletContext) {
        if (!overview.isEmpty()) {
            return;
        }

        overview.put("Monday", new HashMap<>());
        overview.put("Tuesday", new HashMap<>());
        overview.put("Wednesday", new HashMap<>());
        overview.put("Thursday", new HashMap<>());
        overview.put("Friday", new HashMap<>());
        String[] weekdays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        for (String day : weekdays) {
            String filePath = "/WEB-INF/meals/" + day.toLowerCase() + ".txt";
            try {
                List<String> options = Files.readAllLines(Paths.get(servletContext.getRealPath(filePath)));
                options.forEach(option -> overview.get(day).put(option, 0));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Map<String, ChoiceForWeek> getOrders() {
        return orders;
    }

}
