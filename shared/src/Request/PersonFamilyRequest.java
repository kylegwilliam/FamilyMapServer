package Request;


/**
 * PersonFamilyRequest class
 */

public class PersonFamilyRequest {

    /**
     * This needs an AuthToken in order for the PersonFamilyRequest to
     * be made.
     */

    public String authtoken;


    /**
     * The constructor for the PersonFamilyRequest
     *
     * @param authtoken
     */

    public PersonFamilyRequest(String authtoken) {

        this.authtoken = authtoken;

    }



    public String getAuthToken() {
        return authtoken;
    }

    public void setAuthToken(String authToken) {
        authtoken = authToken;
    }


}
