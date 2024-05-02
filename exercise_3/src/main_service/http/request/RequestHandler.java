package main_service.http.request;

import main_service.http.controller.QuotesController;
import main_service.http.response.Response;

public class RequestHandler {

    public Response handle(Request request) {
        if (request.getPath().equals("/quotes") && request.getHttpMethod().equals(HttpMethod.GET)) {
            return (new QuotesController(request)).doGet();
        } else if (request.getPath().equals("/save-quote") && request.getHttpMethod().equals(HttpMethod.POST)) {
            return (new QuotesController(request)).doPost();
        }

        return null;
    }
}
