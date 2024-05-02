package org.example;

import org.example.html.HtmlStrings;
import org.example.storage.Storage;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@WebServlet("/chosen-meals")
public class ChosenMealsServlet extends HttpServlet {

    private String password;

    public void init() {
        // initialize Storage.overview
        Storage.overviewLock.lock();
        Storage.initialiseOverview(getServletContext());
        Storage.overviewLock.unlock();

        // read the password from the file
        String filePath = "WEB-INF/meals/password.txt";

        try {
            this.password = new String(Files.readAllBytes(Paths.get(getServletContext().getRealPath(filePath))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String enteredPassword = req.getParameter("password");

        if(!password.equals(enteredPassword))
            resp.sendRedirect("/");

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        String htmlPage = generateHtmlPage();
        resp.getWriter().write(htmlPage);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Storage.orders.clear();
        Storage.overview.clear();
        Storage.initialiseOverview(getServletContext());
    }

    private String generateHtmlPage() {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append(HtmlStrings.viewAllOrdersStart);

        // For every day of the week we have a 'day-title' and a table
        String[] weekdays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        Storage.overviewLock.lock();
        for (String day : weekdays) {
            htmlBuilder.append("<div class=\"day-title\">").append(day).append("</div>");
            htmlBuilder.append("<div class='table-container'>");
            htmlBuilder.append("<table>");
            htmlBuilder.append(HtmlStrings.viewAllOrdersTH);

            Map<String, Integer> dayOverview = Storage.overview.get(day);
            int counter = 1;
            for(Map.Entry<String, Integer> entry : dayOverview.entrySet()) {
                htmlBuilder.append("<tr>");
                htmlBuilder.append("<td>").append(counter++).append("</td>");
                htmlBuilder.append("<td>").append(entry.getKey()).append("</td>");
                htmlBuilder.append("<td>").append(entry.getValue()).append("</td>");
                htmlBuilder.append("<tr>");
            }

            htmlBuilder.append("</table>");
            htmlBuilder.append("</div>");
        }
        Storage.overviewLock.unlock();

        htmlBuilder.append(HtmlStrings.viewAllOrdersEnd);
        return htmlBuilder.toString();
    }

}
