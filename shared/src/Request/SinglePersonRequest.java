package Request;

//import DataAccess.AuthTokenDao;
import Result.SinglePersonResult;

/**
 * SinglePersonRequest class
 */

public class SinglePersonRequest {

    /**
     * This is information that is able to be extracted from
     * the web URL.
     */

    public String personID;
    public String authtoken;


    /**
     * This is the constructor for the SinglePersonRequest
     *
     * @param personID
     */

    public SinglePersonRequest (String personID, String AuthToken) {

        this.personID = personID;
        this.authtoken = AuthToken;

    }



    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAuthToken() {
        return authtoken;
    }

    public void setAuthToken(String authToken) {
        authtoken = authToken;
    }

}

