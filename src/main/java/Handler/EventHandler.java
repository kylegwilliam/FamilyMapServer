package Handler;

import java.io.*;
import java.net.*;

import Request.AllEventRequest;
import Result.AllEventResult;
import Service.AllEventsService;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;

    /*
        The ListGamesHandler is the HTTP handler that processes
        incoming HTTP requests that contain the "/games/list" URL path.

        Notice that ListGamesHandler implements the HttpHandler interface,
        which is define by Java.  This interface contains only one method
        named "handle".  When the HttpServer object (declared in the Server class)
        receives a request containing the "/games/list" URL path, it calls
        ListGamesHandler.handle() which actually processes the request.
    */

public class EventHandler implements HttpHandler {


        // Handles HTTP requests containing the "/games/list" URL path.
        // The "exchange" parameter is an HttpExchange object, which is
        // defined by Java.
        // In this context, an "exchange" is an HTTP request/response pair
        // (i.e., the client and server exchange a request and response).
        // The HttpExchange object gives the handler access to all of the
        // details of the HTTP request (Request type [GET or POST],
        // request headers, request body, etc.).
        // The HttpExchange object also gives the handler the ability
        // to construct an HTTP response and send it back to the client
        // (Status code, headers, response body, etc.).
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            boolean success = false;
            Gson gson = new Gson();

            try {

                if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                    // Get the HTTP request headers
                    Headers reqHeaders = exchange.getRequestHeaders();

                    // Check to see if an "Authorization" header is present
                    if (reqHeaders.containsKey("Authorization")) {

                        String authToken = reqHeaders.getFirst("Authorization");

                        AllEventRequest request = new AllEventRequest(authToken);

                        AllEventsService service = new AllEventsService();
                        AllEventResult result = service.event(request);

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

                        String outputString = gson.toJson(result);

                        writer.write(outputString);

                        writer.close();
                        resBody.close();

                        success = true;

                    }
                }

                if (!success) {
                    // The HTTP request was invalid somehow, so we return a "bad request"
                    // status code to the client.
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    // Since the client request was invalid, they will not receive the
                    // list of games, so we close the response body output stream,
                    // indicating that the response is complete.
                    exchange.getResponseBody().close();
                }
            }
            catch (Exception e) {
                // Some kind of internal error has occurred inside the server (not the
                // client's fault), so we return an "internal server error" status code
                // to the client.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                // Since the server is unable to complete the request, the client will
                // not receive the list of games, so we close the response body output stream,
                // indicating that the response is complete.
                exchange.getResponseBody().close();

                // Display/log the stack trace
                e.printStackTrace();
            }
        }

        /*
            The writeString method shows how to write a String to an OutputStream.
        */
        private void writeString(String str, OutputStream os) throws IOException {
            OutputStreamWriter sw = new OutputStreamWriter(os);
            sw.write(str);
            sw.flush();
        }

}

