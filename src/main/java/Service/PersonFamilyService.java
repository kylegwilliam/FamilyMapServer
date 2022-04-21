package Service;

import DataAccess.*;
import Request.PersonFamilyRequest;
import Result.PersonFamilyResult;
import model.AuthToken;
import model.Person;

import java.util.List;

/**
 * PersonFamilyService Class
 */

public class PersonFamilyService {

    /**
     * person Method
     *
     * @return
     */



    public PersonFamilyResult person(PersonFamilyRequest r) throws DataAccessException {

        List<Person> personArray;
        Database db = new Database();
        PersonFamilyResult PFR = new PersonFamilyResult(null, false, null);

        try {

            AuthTokenDao ATD = new AuthTokenDao(db.getConnection());
            PersonDao PD = new PersonDao(db.getConnection());

            String authString = r.getAuthToken();
            AuthToken foundAuth = ATD.find(authString);


            if (foundAuth == null) {
                PFR.setData(null);
                PFR.setSuccess(false);
                PFR.setMessage("Error: There is no correct auth token in Person Family Service.");
                db.closeConnection(true);
                return PFR;
            }

            personArray = PD.getFamily(foundAuth.getUsername());

            //check my get family function.

            if (personArray == null) {
                PFR.setData(null);
                PFR.setSuccess(false);
                PFR.setMessage("Error: The person array is empty.");
                db.closeConnection(true);
                return PFR;

            }

            else {
                PFR.setData(personArray);
                PFR.setSuccess(true);
            }

            db.closeConnection(true);


        }
        catch (DataAccessException DAE) {

            PFR.setMessage("Error: " + DAE);
            PFR.setData(null);
            PFR.setSuccess(false);
            db.closeConnection(true);

        }

        return PFR;
    }
}

