package Request;

/**
 * SingleEventRequest Class
 */

public class SingleEventRequest {

    /**
     * The SingleEventRequest needs an eventID. It is able to get it
     * through the webAPI.
     */

    public String eventID;
    public String authtoken;


    /**
     * Constructor for SingleEventRequest
     *
     * @param eventID
     */

    public SingleEventRequest(String eventID, String AuthToken) {

        this.eventID = eventID;
        this.authtoken = AuthToken;

    }


    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAuthToken() {
        return authtoken;
    }

    public void setAuthToken(String authToken) {
        authtoken = authToken;
    }

}
