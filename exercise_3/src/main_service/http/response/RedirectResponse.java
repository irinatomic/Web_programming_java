package main_service.http.response;

public class RedirectResponse extends Response {

    private final String location;

    public RedirectResponse(String location) {
        this.location = location;
    }

    @Override
    public String getResponseString() {
        String response = "HTTP/1.1 302 OK\r\nLocation: " + this.location + "\r\n\r\n";
        return response;
    }
}
