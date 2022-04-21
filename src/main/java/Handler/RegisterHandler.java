package Handler;

import java.io.*;
import java.net.*;

import Request.RegisterRequest;
import Service.RegisterService;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import Result.RegisterResult;

public class RegisterHandler implements HttpHandler {


    public Object Request;

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;
        Gson gson = new Gson();

        try {

            if (exchange.getRequestMethod().toLowerCase().equals("post")) {


                //For other handlers. that require request.
                // Get the request body input stream
                InputStream reqBody = exchange.getRequestBody();

                // Read JSON string from the input stream
                String reqData = readString(reqBody);

                // Display/log the request JSON data
                System.out.println(reqData);


                //Might not need this line
				//RegisterRequest request = (RegisterRequest) Request;

                //here - json help
                RegisterRequest request = (RegisterRequest)gson.fromJson(reqData, RegisterRequest.class);

				RegisterService service = new RegisterService();
				RegisterResult result = service.register(request);

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

                //Potential problem could be here!
                //gson.toJson(result, (Appendable)resBody);
                gson.toJson(result, writer);

                writer.close();
				resBody.close();
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
        }
        catch (Exception e) {
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


