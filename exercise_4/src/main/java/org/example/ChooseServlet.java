package org.example;

import org.example.html.HtmlStrings;
import org.example.storage.ChoiceForWeek;
import org.example.storage.Storage;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@WebServlet("/choose")
public class ChooseServlet extends HttpServlet {

    public void init() {
        // initialize Storage.overview
        Storage.overviewLock.lock();
        Storage.initialiseOverview(getServletContext());
        Storage.overviewLock.unlock();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId = req.getSession().getId();
        String cookie = extractCookie(req);

        if (!cookie.isEmpty() && Storage.orders.containsKey(cookie))
            processViewOrders(cookie, req, resp);
        else
            processMakeOrder(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String cookie = extractCookie(req);

        if (!cookie.isEmpty()) {
            String monday = req.getParameter("monday");
            String tuesday = req.getParameter("tuesday");
            String wednesday = req.getParameter("wednesday");
            String thursday = req.getParameter("thursday");
            String friday = req.getParameter("friday");

            Storage.ordersLock.lock();
            Storage.orders.put(cookie, new ChoiceForWeek(monday, tuesday, wednesday, thursday, friday));
            Storage.ordersLock.unlock();

            resp.sendRedirect("/success");
        } else {
            resp.getWriter().write("No cookie found. Cannot process order.");
        }
    }

    private void processMakeOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append(HtmlStrings.chooseOptionsStart);

        String[] weekdays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        for (String day : weekdays) {
            htmlBuilder.append("<label>").append(day).append("</label>");
            htmlBuilder.append("<select name='").append(day.toLowerCase()).append("'>");

            String fileName = day.toLowerCase() + ".txt";
            List<String> options = readOptionsFromFile(req, fileName);

            for (String option : options)
                htmlBuilder.append("<option value='").append(option).append("'>").append(option).append("</option>");

            htmlBuilder.append("</select>");
        }

        htmlBuilder.append(HtmlStrings.chooseOptionsEnd);
        resp.getWriter().write(htmlBuilder.toString());
    }

    private void processViewOrders(String cookie, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append(HtmlStrings.viewPersonalOrdersStart);

        // Append personal orders
        Storage.ordersLock.lock();
        ChoiceForWeek choice = Storage.getOrders().get(cookie);
        Storage.ordersLock.unlock();

        htmlBuilder.append("<h4>").append("Monday: ").append(choice.getMonday()).append("</h4><br>");
        htmlBuilder.append("<h4>").append("Tuesday: ").append(choice.getTuesday()).append("</h4><br>");
        htmlBuilder.append("<h4>").append("Wednesday: ").append(choice.getWednesday()).append("</h4><br>");
        htmlBuilder.append("<h4>").append("Thursday: ").append(choice.getThursday()).append("</h4><br>");
        htmlBuilder.append("<h4>").append("Friday: ").append(choice.getFriday()).append("</h4><br>");

        htmlBuilder.append(HtmlStrings.viewPersonalOrdersEnd);
        resp.getWriter().write(htmlBuilder.toString());
    }

    private String extractCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();

        if(cookies == null) return "";

        for(Cookie c : cookies) {
            if(c.getName().equals("JSESSIONID"))
                return c.getValue();
        }
        return "";
    }

    private List<String> readOptionsFromFile(HttpServletRequest req, String fileName) throws IOException {
        String filePath = "/WEB-INF/meals/" + fileName;
        return Files.readAllLines(Paths.get(req.getServletContext().getRealPath(filePath)));
    }

}
