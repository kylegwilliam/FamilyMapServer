package Handler;

import java.io.*;
import java.net.*;
import java.nio.file.Files;

import com.sun.net.httpserver.*;


public class FileHandler implements HttpHandler {

    //Not really sure about this file.
    private File file;

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {

            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                try {

                    String urlPath = exchange.getRequestURI().toString();

                    if (urlPath.equals("/") || urlPath.equals("")) {
                        urlPath = "/index.html";

                    }

                    String FilePath = "web" + urlPath;

                    // get "/" -> give me home page. Returns index.html
                    // get /favicon.ico
                    // get /css/main.css

                    file = new File(FilePath);

                    if (!file.exists()) {
                        file = new File("web/HTML/404.html");
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    }

                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);

                    }

                    OutputStream respBody = exchange.getResponseBody();
                    Files.copy(file.toPath(), respBody);

                    respBody.close();
                }

                catch (FileNotFoundException fnf) {

                }
            }

        } catch(Exception e) {
            System.out.println("Error in Making GET Request");
        }




    }

}
