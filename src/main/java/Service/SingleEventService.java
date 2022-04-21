package Service;

import DataAccess.*;
import Request.SingleEventRequest;
import Result.SingleEventResult;
import model.AuthToken;
import model.Event;

import java.util.Objects;

/**
 * SingleEventService Class
 */

public class SingleEventService {


    /**
     * event Method
     *
     * @return
     */


    public SingleEventResult event(SingleEventRequest r) throws DataAccessException {

        Database db = new Database();
        SingleEventResult SER = new SingleEventResult(null, null, null,
                0, 0, null, null, null,
                0, false, null);


        try {

            EventDao ED = new EventDao(db.getConnection());
            AuthTokenDao ATD = new AuthTokenDao(db.getConnection());

            String authString = r.getAuthToken();
            String eventID = r.getEventID();



            if (authString.equals("")) {
                SER.setMessage("Error: no authToken");
                SER.setSuccess(false);
                db.closeConnection(true);
                return SER;
            }

            if (eventID.equals("")) {
                SER.setMessage("Error: now eventID");
                SER.setSuccess(false);
                db.closeConnection(true);
                return SER;
            }

//            Event event = ED.find(r.getEventID());
//            String Username1 = event.getAssociatedUsername();
//
//            AuthToken AT = ATD.find(r.getAuthToken());
//            String Username2 = AT.getUsername();
//
//            if (Username1 != Username2) {
//                SER.setMessage("Error: usernames dont match");
//                SER.setSuccess(false);
//                db.closeConnection(true);
//                return SER;
//            }


            String correctAuthToken = null;
            if (ATD.find(authString) != null) {
                correctAuthToken = authString;
            }

            if (correctAuthToken == null) {
                SER.setMessage("Error: auth token is null");
                SER.setSuccess(false);
                db.closeConnection(true);
                return SER;
            }

            Event rEvent = ED.find(r.getEventID());

            if (rEvent == null) {
                SER.setMessage("Error: rEvent is null");
                SER.setSuccess(false);
                db.closeConnection(true);
                return SER;
            }

            String username = rEvent.getAssociatedUsername();

            if (username == null) {
                SER.setMessage("Error: Error in Username, not a real username");
                SER.setSuccess(false);
                db.closeConnection(true);
                return SER;
            }

            //NEW stuff that is causing Event Valid Test to fail
            //But having EventWrongUser pass.

            Event event = ED.find(r.getEventID());
            String Username1 = event.getAssociatedUsername();

            AuthToken authToken = ATD.find(r.getAuthToken());
            String Username2 = authToken.getUsername();

            if (!Objects.equals(Username1, Username2)) {
                SER.setMessage("Error: usernames don't match");
                SER.setSuccess(false);
                db.closeConnection(true);
                return SER;
            }




            if (correctAuthToken != null) {

                SER.setAssociatedUsername(rEvent.getAssociatedUsername());
                SER.setEventID(r.getEventID());
                SER.setPersonID(rEvent.getPersonID());
                SER.setLatitude(rEvent.getLatitude());
                SER.setLongitude(rEvent.getLongitude());
                SER.setCountry(rEvent.getCountry());
                SER.setCity(rEvent.getCity());
                SER.setEventType(rEvent.getEventType());
                SER.setYear(rEvent.getYear());
                SER.setSuccess(true);

                db.closeConnection(true);

            }
            else {
                SER.setMessage("Error: Error in Single Event Service Auth Token");
                SER.setSuccess(false);
                db.closeConnection(true);
                return SER;
            }

        } catch (DataAccessException dae) {
            SER.setMessage("Error: " + dae);
            SER.setSuccess(false);
            db.closeConnection(true);
            return SER;

        }

        return SER;

    }
}
