package Service;

import DataAccess.*;
import Handler.FillUserGenHandler;
import Request.FillRequest;
import Request.RegisterRequest;
import Result.ClearResult;
import Result.FillResult;
import Result.RegisterResult;
import model.AuthToken;
import model.User;

import java.io.IOException;
import java.util.UUID;


/**
 * register Method
 */

public class RegisterService {

    /**
     * register Method
     *
     * @param r
     * @return
     */


    //public static RegisterResult RR = new RegisterResult();

    public RegisterResult register(RegisterRequest r) throws DataAccessException {

        Database db = new Database();
        RegisterResult RR = new RegisterResult(null, null,
                                        null, false, null);


        try {

            UserDao UD = new UserDao(db.getConnection());
            AuthTokenDao ATD = new AuthTokenDao(db.getConnection());

            String genAuthToken = UUID.randomUUID().toString();

            //The problem is with my r.getUsername. It is saying r is null.
            User user = new User(r.getUsername(), r.getPassword(), r.getEmail(), r.getFirstName(),
                        r.getLastName(), r.getGender(), genAuthToken);


            String username = r.getUsername();

            //if (ATD.find(username) == null) {
            //    RR.setMessage("Error: Bad auth token");
            //    RR.setSuccess(false);
            //    return RR;
            //}

            //StringBuilder str = new StringBuilder();

            //str.append("/" + username + "/4");

            //String path = str.toString();


            String personID = UUID.randomUUID().toString();

            AuthToken newPerson = new AuthToken(genAuthToken, r.getUsername());
            ATD.insert(newPerson);
            user.setPersonID(personID);

            UD.insert(user);

            RR.setAuthToken(genAuthToken);
            RR.setUsername(r.getUsername());
            RR.setPersonID(personID);
            RR.setSuccess(true);

            db.closeConnection(true);

            //FillUserGenHandler handler = new FillUserGenHandler();

            //How do I check to make sure that it added everything okay?
            //if ()
            FillRequest request = new FillRequest(username, 4);

            FillService service = new FillService();
            FillResult result = service.fill(request);


        }
        catch (DataAccessException dae) {
            RR.setMessage("Error: " + dae);
            RR.setSuccess(false);
            db.closeConnection(true);

        }

        return RR;
    }

}
