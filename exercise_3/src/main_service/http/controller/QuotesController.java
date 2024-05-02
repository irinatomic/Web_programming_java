package main_service.http.controller;

import main_service.Server;
import main_service.http.request.Request;
import main_service.http.response.HtmlResponse;
import main_service.http.response.RedirectResponse;
import main_service.http.response.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URLDecoder;

public class QuotesController extends Controller {

    public QuotesController(Request request) {
        super(request);
    }

    @Override
    public Response doGet() {
        String htmlForm = "" +
                "<form method=\"POST\" action=\"/save-quote\">" +
                "<label>Author: </label> <input name=\"author\" type=\"author\"><br><br>" +
                "<label>Quote: </label> <input name=\"quote\" type=\"quote\"><br><br>" +
                "<button>Save Quote</button>" +
                "</form><br><br>";

        String QOD = getQuoteOfDay();
        String htmlQuoteOfTheDay = "<h1>Quote of the day</h1>" +
                "<p>" + QOD +"</p><br><br>";

        String htmlQuotes = "<h1>Quotes</h1>";
        for (String quote : Server.quotes)
            htmlQuotes += "<p>" + quote + "</p>";

        String metaCharsetTag = "<meta charset=\"UTF-8\">";

        String html = "<!DOCTYPE html><html>" +
                "<head>" + metaCharsetTag + "</head>" +
                "<body>" + htmlForm + htmlQuoteOfTheDay + htmlQuotes + "</body>" +
                "</html>";

        return new HtmlResponse(html);
    }

    @Override
    public Response doPost() {
        // Received body: author=autor&quote=tekst

        String[] parts = request.getBody().split("&");
        String authorEncoded = parts[0].split("=")[1];
        String quoteEncoded = parts[1].split("=")[1];

        try {
            String author = URLDecoder.decode(authorEncoded, "UTF-8");
            String quote = URLDecoder.decode(quoteEncoded, "UTF-8");

            Server.quotes.add(author + " - \"" + quote + "\"" );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new RedirectResponse("/quotes");
    }

    // Method to send GET request to side server
    private String getQuoteOfDay() {

        try (Socket socket = new Socket("localhost", 8114);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Send GET request
            out.println("GET /quote-of-day HTTP/1.1");
            out.println("Host: localhost");
            out.println();

            // Read response
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                responseBuilder.append(line);
            }

            // Extract quote of the day from response
            // Assuming response format is plain text
            String response = responseBuilder.toString();
            return parseJSON(response);


        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to retrieve quote of the day";
        }
    }

    private String parseJSON(String json) {
        String quote = json.split(":")[1].replace("}", "").trim();
        // remove first and last \"
        return quote.substring(1, quote.length() - 1);
    }
}
