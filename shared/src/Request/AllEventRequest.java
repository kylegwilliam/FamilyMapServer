package Request;

/**
 * AllEventRequest Class
 */

public class AllEventRequest {

    /**
     * This needs the AuthToken in order to be able to access all the everts.
     */

    public String authtoken;


    /**
     * constructor for AllEventRequest
     * @param authtoken
     */



    public AllEventRequest(String authtoken) {

        this.authtoken = authtoken;

    }

    public String getAuthToken() {
        return authtoken;
    }

    public void setAuthToken(String authToken) {
        authtoken = authToken;
    }



}
