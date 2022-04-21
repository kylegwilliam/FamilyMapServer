package Result;

/**
 * SinglePersonResult Class
 */

public class SinglePersonResult {

    /**
     * Varaibles that can be used in SinglePersonResult
     */

    public String associatedUsername;
    public String personID;
    public String firstName;
    public String lastName;
    public String gender;
    public String fatherID;
    public String motherID;
    public String spouseID;
    public boolean success;
    public String message;



    /**
     * Constructor for SinglePersonResult
     *
     * @param associatedUsername
     * @param personID
     * @param firstName
     * @param lastName
     * @param gender
     * @param fatherID
     * @param motherID
     * @param spouseID
     * @param success
     * @param message
     */


    public SinglePersonResult(String associatedUsername, String personID, String firstName,
                              String lastName, String gender, String fatherID, String motherID,
                              String spouseID, boolean success, String message) {


        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
        this.success = success;
        this.message = message;


    }


    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
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
