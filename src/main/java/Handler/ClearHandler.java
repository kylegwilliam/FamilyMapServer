package Handler;

import java.io.*;
import java.net.*;

import Service.ClearService;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import Result.ClearResult;

public class ClearHandler implements HttpHandler {

    /**
     * For other handlers. that require request.
     // Get the request body input stream
     InputStream reqBody = exchange.getRequestBody();

     // Read JSON string from the input stream
     String reqData = readString(reqBody);

     // Display/log the request JSON data
     System.out.println(reqData);

     */

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;
        Gson gson = new Gson();

        try {

            if (exchange.getRequestMethod().toLowerCase().equals("post")) {


                InputStream reqBody = exchange.getRequestBody();

                String reqData = readString(reqBody);

                System.out.println(reqData);


                ClearService service = new ClearService();
                ClearResult result = service.clear();

                if (result.isSuccess()) {
                    // Start sending the HTTP response to the client, starting with
                    // the status code and any defined headers.
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                OutputStream resBody = exchange.getResponseBody();

                OutputStreamWriter writer = new OutputStreamWriter(resBody);

                gson.toJson(result, writer);

                writer.close();
                resBody.close();

                // We are not sending a response body, so close the response body
                // output stream, indicating that the response is complete.

                //Dont think I need this
                //exchange.getResponseBody().close();

                success = true;

            }

            if (!success) {

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

                exchange.getResponseBody().close();
            }

        } catch (Exception e) {

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

            exchange.getResponseBody().close();

            e.printStackTrace();
        }
    }

    /*
        The readString method shows how to read a String from an InputStream.
    */
    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}

