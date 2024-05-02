package main_service.http.response;

public class HtmlResponse extends Response {

    // the already generated html from quotes controller
    private final String html;

    public HtmlResponse(String html) {
        this.html = html;
    }

    @Override
    public String getResponseString() {
        String header = "HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=UTF-8\r\n\r\n";
        String response = header + html;

        return response;
    }
}
