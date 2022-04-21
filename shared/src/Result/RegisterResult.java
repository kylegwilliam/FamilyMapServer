package Result;

/**
 * RegisterService Method
 */

public class RegisterResult {

    /**
     * All the variables that are going to be used
     * in Register Service.
     */

    public String authtoken;
    public String username;
    public String personID;
    public boolean success;
    public String message;



    /**
     * Constructor for Register Service
     *
     * @param authtoken
     * @param username
     * @param personID
     * @param success
     * @param message
     */


    public RegisterResult(String authtoken, String username, String personID,
                          boolean success, String message) {

        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
        this.message = message;


    }

    public String getAuthToken() {
        return authtoken;
    }

    public void setAuthToken(String authToken) {
        authtoken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
