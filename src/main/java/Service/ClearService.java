package Service;

import DataAccess.*;
import Result.ClearResult;

import javax.xml.crypto.Data;
import java.sql.SQLException;

/**
 * ClearService class
 */

public class ClearService {

    /**
     * clear Method
     *
     * @return
     */


    public ClearResult clear() throws DataAccessException {

        Database db = new Database();
        ClearResult CR = new ClearResult(null, false);


        try {
            EventDao ED = new EventDao(db.getConnection());
            PersonDao PD = new PersonDao(db.getConnection());
            UserDao UD = new UserDao(db.getConnection());
            AuthTokenDao ATD = new AuthTokenDao(db.getConnection());

            ED.clear();
            PD.clear();
            UD.clear();
            ATD.clear();

            CR.setMessage("Clear succeeded.");
            CR.setSuccess(true);

            db.closeConnection(true);


        } catch (DataAccessException dae) {
            CR.setMessage("Error: " + dae);
            CR.setSuccess(false);
            db.closeConnection(true);

        }

        return CR;
    }

}
