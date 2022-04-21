package Result;

import model.Person;

import java.util.List;

/**
 * PersonFamilyRequest Class
 */

public class PersonFamilyResult {

    /**
     * Variables that will be used for PersonFamilyResult
     */

    public List<Person> data;
    public boolean success;
    public String message;



    /**
     * Constructor for PersonFamilyRequest
     *
     * @param data
     * @param success
     */


    public PersonFamilyResult(List<Person> data, boolean success, String message) {

        this.data = data;
        this.success = success;
        this.message = message;

    }


    public List<Person> getData() {
        return data;
    }

    public void setData(List<Person> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }


}
