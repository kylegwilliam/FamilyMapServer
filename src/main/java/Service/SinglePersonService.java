package Service;

import DataAccess.*;
import Request.SinglePersonRequest;
import Result.SinglePersonResult;
import model.AuthToken;
import model.Person;

import javax.xml.crypto.Data;

/**
 * SinglePersonService Class
 */

public class SinglePersonService {

    /**
     * person Method
     *
     * @return
     */


    public SinglePersonResult person(SinglePersonRequest r) throws DataAccessException {

        Database db = new Database();
        SinglePersonResult SPR = new SinglePersonResult(null, null, null,
                                            null, null, null, null,
                                            null, false, null);


        try {

            PersonDao PD = new PersonDao(db.getConnection());
            AuthTokenDao ATD = new AuthTokenDao(db.getConnection());
            EventDao ED = new EventDao(db.getConnection());

            String correctAuthToken = null;
            if (ATD.find(r.getAuthToken()) != null) {
                correctAuthToken = r.getAuthToken();
            }

            if (correctAuthToken == null) {
                SPR.setMessage("Error: bad auth token");
                SPR.setSuccess(false);
                db.closeConnection(true);
                return SPR;
            }

            Person person = PD.find(r.getPersonID());
            String username1 = null;

            if (person.getAssociatedUsername() != null) {
                username1 = person.getAssociatedUsername();
            }

            AuthToken auth = ATD.find(r.getAuthToken());
            String username2 = null;

            if (auth.getUsername() != null) {
                username2 = auth.getUsername();
            }

            if (username1 == null) {
                SPR.setMessage("Error: null username1");
                SPR.setSuccess(false);
                db.closeConnection(true);
                return SPR;
            }

            if (username2 == null) {
                SPR.setMessage("Error: null username2");
                SPR.setSuccess(false);
                db.closeConnection(true);
                return SPR;
            }

            if (!username1.equals(username2)) {
                SPR.setMessage("Error: usernames are not the same");
                SPR.setSuccess(false);
                db.closeConnection(true);
                return SPR;

            }

//            String correctAuthToken = null;
//            if (ATD.find(r.getAuthToken()) != null) {
//                correctAuthToken = r.getAuthToken();
//            }
//
//            if (correctAuthToken == null) {
//                SPR.setMessage("Error: bad auth token");
//                SPR.setSuccess(false);
//                db.closeConnection(true);
//                return SPR;
//            }

            Person rPerson = PD.find(r.getPersonID());
            if (PD.find(r.getPersonID()) == null) {
                SPR.setMessage("Error: bad personID");
                SPR.setSuccess(false);
                db.closeConnection(true);
                return SPR;
            }

            if (rPerson == null) {
                SPR.setMessage("Error: PersonID");
                SPR.setSuccess(false);
                db.closeConnection(true);
                return SPR;
            }


            //String username = rPerson.getAssociatedUsername();

            //if (rPerson.getPersonID() == null) {
            //    SPR.setMessage("Error: The db is empty");
            //    SPR.setSuccess(false);
            //    db.closeConnection(true);
            //    return SPR;
            //}



//            String correctPersonID = null;
//            if (PD.find(username) != null) {
//                correctPersonID = username;
//            }
//
//            if (username == null) {
//                SER.setMessage("Error: auth token is null");
//                SER.setSuccess(false);
//                db.closeConnection(true);
//                return SER;
//            }


            if (correctAuthToken != null) {

                SPR.setAssociatedUsername(rPerson.getAssociatedUsername());
                SPR.setPersonID(r.getPersonID());
                SPR.setFirstName(rPerson.getFirstName());
                SPR.setLastName(rPerson.getLastName());
                SPR.setGender(rPerson.getGender());
                SPR.setFatherID(rPerson.getFatherID());
                SPR.setMotherID(rPerson.getMotherID());
                SPR.setSpouseID(rPerson.getSpouseID());
                SPR.setSuccess(true);
            }

            else {
                SPR.setMessage("Error: Invalid auth token");
                SPR.setSuccess(false);
            }

            db.closeConnection(true);


        } catch (DataAccessException dae) {
            SPR.setMessage("Error: " + dae);
            SPR.setSuccess(false);
            db.closeConnection(true);

        }

        return SPR;
    }


}
