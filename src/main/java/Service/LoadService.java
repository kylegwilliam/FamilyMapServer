package Service;

import DataAccess.*;
import Request.LoadRequest;
import Request.RegisterRequest;
import Result.AllEventResult;
import Result.LoadResult;
import model.Event;
import model.Person;
import model.User;
import passoffresult.EventResult;
import passoffresult.RegisterResult;

import javax.xml.crypto.Data;

/**
 * LoadService Class
 */

public class LoadService {

    /**
     *
     * register Method
     *
     * @param r
     * @return
     */

    public LoadResult register(LoadRequest r) throws DataAccessException {

        Database db = new Database();
        LoadResult LR = new LoadResult(null, false);

        try {
            EventDao ED = new EventDao(db.getConnection());
            PersonDao PD = new PersonDao(db.getConnection());
            UserDao UD = new UserDao(db.getConnection());
            AuthTokenDao ATD = new AuthTokenDao(db.getConnection());

            ED.clear();
            PD.clear();
            UD.clear();
            ATD.clear();



            for (int i = 0; i < r.getUsers().length; i++) {
                UD.insert(r.getUsers()[i]);
            }

            for (int i = 0; i < r.getPerson().length; i++) {
                PD.insert(r.getPerson()[i]);
            }

            for (int i = 0; i < r.getEvents().length; i++) {
                ED.insert(r.getEvents()[i]);
            }


            LR.setMessage("Successfully added " + r.getUsers().length + " users, " + r.getPerson().length + " persons, and " +
                    r.getEvents().length + " events to the database.");

            LR.setSuccess(true);

            db.closeConnection(true);



        } catch (DataAccessException dae) {
            LR.setMessage("Error: " + dae);
            LR.setSuccess(false);
            db.closeConnection(true);

        }

        return LR;
    }


}
