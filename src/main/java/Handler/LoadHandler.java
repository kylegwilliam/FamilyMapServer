package Handler;

import java.io .*;
import java.net .*;

import Request.LoadRequest;
import Request.RegisterRequest;
import Result.LoadResult;
import Service.LoadService;
import com.google.gson.Gson;
import com.sun.net.httpserver .*;
import model.Event;
import model.Person;
import model.User;


public class LoadHandler implements HttpHandler {

    /*
        The ClaimRouteHandler is the HTTP handler that processes
        incoming HTTP requests that contain the "/routes/claim" URL path.

        Notice that ClaimRouteHandler implements the HttpHandler interface,
        which is define by Java.  This interface contains only one method
        named "handle".  When the HttpServer object (declared in the Server class)
        receives a request containing the "/routes/claim" URL path, it calls
        ClaimRouteHandler.handle() which actually processes the request.
    */


        @Override
        public void handle(HttpExchange exchange) throws IOException {


            boolean success = false;
            Gson gson = new Gson();

            //Need to access the dows in order to get the AuthToken.
            //Leave that to the service class to find the authTokens.
            //Put all of the logic in there.

            try {
                // Determine the HTTP request type (GET, POST, etc.).
                // Only allow POST requests for this operation.
                // This operation requires a POST request, because the
                // client is "posting" information to the server for processing.
                if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                    InputStream reqHeaders = exchange.getRequestBody();

                    String reqData = readString(reqHeaders);

                    System.out.println(reqData);

                    LoadRequest request = (LoadRequest) gson.fromJson(reqData, LoadRequest.class);

                    LoadService service = new LoadService();
                    LoadResult result = service.register(request);

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
                    //exchange.getResponseBody().close();

                    success = true;

                }

                if (!success) {
                    // The HTTP request was invalid somehow, so we return a "bad request"
                    // status code to the client.
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

                    // We are not sending a response body, so close the response body
                    // output stream, indicating that the response is complete.
                    exchange.getResponseBody().close();
                }
            } catch (Exception e) {
                // Some kind of internal error has occurred inside the server (not the
                // client's fault), so we return an "internal server error" status code
                // to the client.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

                // We are not sending a response body, so close the response body
                // output stream, indicating that the response is complete.
                exchange.getResponseBody().close();

                // Display/log the stack trace
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

