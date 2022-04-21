package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Request.LoginRequest;
import Result.LoginResult;
import model.AuthToken;
import model.User;

import javax.xml.crypto.Data;
import java.util.UUID;

/**
 * LoginService Class
 */

public class LoginService {

    /**
     *
     * login Method
     *
     * @param r
     * @return
     */

    public LoginResult login(LoginRequest r) throws DataAccessException {

        Database db = new Database();
        LoginResult LR = new LoginResult(null, null, null,
                                                false, null);

        try {
            UserDao UD = new UserDao(db.getConnection());
            AuthTokenDao ATD = new AuthTokenDao(db.getConnection());

            User user = UD.find(r.getUsername());
            String genAuthToken = UUID.randomUUID().toString();

            // is this user null
            if (user == null) {
                LR.setMessage("Error: Invalid Value");
                LR.setSuccess(false);
                db.closeConnection(true);
                return LR;
            }

            // does the password match the request from the database
            if (user.getPassword().equals(r.getPassword())) {
                LR.setAuthToken(genAuthToken);
                LR.setUsername(r.getUsername());
                LR.setPersonID(user.getPersonID());
                LR.setSuccess(true);


                AuthToken newPerson = new AuthToken(genAuthToken, r.getUsername());
                ATD.insert(newPerson);

                db.closeConnection(true);
            }
            // if it doenst, invalid password.
            else {
                LR.setMessage("Error: Request property missing value");
                db.closeConnection(true);
                LR.setSuccess(false);
            }

        }
        catch (DataAccessException dae) {
            LR.setMessage("Error: " + dae);
            dae.printStackTrace();
            db.closeConnection(true);
            LR.setSuccess(false);
        }

        return LR;
    }

}

