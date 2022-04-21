package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Request.AllEventRequest;
import Result.AllEventResult;

import model.AuthToken;
import model.Event;

import java.util.List;

/**
 * AllEventsService Class
 */

public class AllEventsService {

    /**
     * event Method
     *
     * @return
     */


    public AllEventResult event(AllEventRequest r) throws DataAccessException {

        List<Event> eventArray;
        Database db = new Database();
        AllEventResult AER = new AllEventResult(null, false, null);

        try {

            AuthTokenDao ATD = new AuthTokenDao(db.getConnection());

            String authString = r.getAuthToken();

            if (authString == null) {
                AER.setData(null);
                AER.setSuccess(false);
                AER.setMessage("Error: auth string is bad");
                db.closeConnection(true);
                return AER;
            }

            AuthToken foundAuth = ATD.find(authString);

            if (foundAuth == null) {
                AER.setData(null);
                AER.setSuccess(false);
                AER.setMessage("Error: auth token is null");
                db.closeConnection(true);
                return AER;
            }

            EventDao ED = new EventDao(db.getConnection());
            String username = foundAuth.getUsername();

            if (username == null) {
                AER.setData(null);
                AER.setSuccess(false);
                AER.setMessage("Error: username is null");
                db.closeConnection(true);
                return AER;
            }

            eventArray = ED.returnEvent(username);

            if (eventArray == null) {
                AER.setData(null);
                AER.setSuccess(false);
                AER.setMessage("Error: event Array is empty");
                db.closeConnection(true);
                return AER;
            }

            else {
                AER.setData(eventArray);
                AER.setSuccess(true);
            }

            db.closeConnection(true);



        } catch (DataAccessException dae) {
            AER.setMessage("Error: " + dae);
            dae.printStackTrace();
            db.closeConnection(true);
            return AER;

        }

        return AER;
    }

}
