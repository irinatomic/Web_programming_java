package main_service.http.controller;

import main_service.http.request.Request;
import main_service.http.response.Response;

public abstract class Controller {

    protected Request request;

    public Controller(Request request) {
        this.request = request;
    }

    public abstract Response doGet();
    public abstract Response doPost();
}

