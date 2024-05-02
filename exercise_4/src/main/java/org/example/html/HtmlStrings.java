package org.example.html;

public class HtmlStrings {

    public static final String chooseOptionsStart =
        "<!DOCTYPE html>" +
        "<html><head>" +
        "<meta charset=\"UTF-8\">" +
        "<title> Choose Your Food </title>" +
        "<style>" +
            "body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; display: flex; justify-content: center; align-items: center; height: 100vh; }" +
            "form { background: #fff; padding: 2em; border-radius: 10px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }" +
            "h1 { text-align: center; font-size: 2em; }" +
            "h2 { text-align: center; font-size: 1.5em; margin-top: 0; }" +
            "label { display: block; font-size: 1.2em; }" +
            "select { width: 100%; padding: 1px; border: 1px solid #ccc; border-radius: 5px; font-size: 1em; margin-bottom: 20px; margin-top: 3px }" +
            "input[type=submit] { background-color: #007BFF; color: white; padding: 1px 15px; border: none; border-radius: 5px; cursor: pointer; font-size: 1em; margin-top: 2px; width: 100%; }" +
            "input[type=submit]:hover { background-color: #0056b3; }" +
        "</style>" +
        "</head><body>" +
            "<form action='/choose' method='POST'>" +
            "<h1>Choose your food</h1>" +
            "<h2>Odaberite vaš ručak:</h2>";

    public static final String chooseOptionsEnd =
        "<input type='submit' value='Potvrdite unos'></form>" +
        "</body>" +
        "</html>";

    public static final String viewPersonalOrdersStart =
        "<!DOCTYPE html>" +
        "<html><head>" +
        "<meta charset=\"UTF-8\">" +
        "<style>" +
            "h1 { text-align: center; font-size: 2em; }" +
            "h2 { text-align: center; font-size: 1.5em; margin-top: 0; margin-bottom: 15px}" +
            "h4 { text-align: center; margin: 3px}" +
        "</style>" +
        "</head><body>" +
            "<h1> Personal orders </h1>" +
            "<h2> Vaši odabrani ručkovi :</h2>";

    public static final String viewPersonalOrdersEnd = "</body></html>";

    public static final String viewAllOrdersStart =
        "<DOCTYPE html>" +
        "<html><head>" +
        "<meta charset=\"UTF-8\">" +
        "<style>" +
            "body { font-family: Arial, sans-serif; display: flex; justify-content: center; align-items: center; }" +
            "table { border-collapse: collapse; width: 100%; margin-bottom: 10px; }" +
            "th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }" +
            "th { background-color: #f2f2f2; }" +
            "h1 { text-align: center; font-size: 2em; padding-bottom: 5px }" +
            ".day-title { font-size: 24px; color: #333; }" +
            ".table-container { width: 550px }" +
            ".clear-btn { background-color: red; color: white; text-align: center; cursor: pointer; }" +
        "</style>" +
        "<script>" +
            "function clearOrders() {" +
                "fetch('/chosen-meals', { method: 'DELETE' })" +
                    ".then(response => {" +
                        "if(response.ok) {" +
                            "window.location.href = '/chosen-meals';" +
                        "}" +
                    "});" +
            "}" +
        "</script>" +
        "</head><body>" +
        "<h1> Chosen meals </h1>" +
        "<button class='clear-btn' onclick=\"clearOrders()\"> Clear orders </button>";

    public static final String viewAllOrdersTH =
        "<tr>" +
            "<th> # </th>" +
            "<th> Jelo </th>" +
            "<th> Kolicina </th>" +
        "</tr>";

    public static final String viewAllOrdersEnd = "</body></html>";

    public static final String successPage =
        "<!DOCTYPE html>" +
        "<html>" +
        "<head>" +
        "<meta charset=\"UTF-8\">" +
        "<style>" +
            "h1 { text-align: center; font-size: 2em; }" +
            "h2 { text-align: center; font-size: 1.5em; margin-top: 0; }" +
            "button { text-align: center; display: block; margin: 0 auto; }" +
        "</style>" +
        "</head><body>" +
            "<h1> Success </h1>" +
            "<h2> Return to start page </h2>" +
            "<button onclick=\"window.location.href='/'\"> Return to start </button>" +
        "</body>" +
        "</html>";
}
