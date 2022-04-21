package DataAccess;

import model.AuthToken;
import model.Event;
import passoffmodels.User;

import java.sql.*;

/**
 * AuthTokenDao Class
 * Database operations that need to be preformed by the AuthToken
 */

public class AuthTokenDao {

    private final Connection conn;

    public AuthTokenDao(Connection conn)
    {
        this.conn = conn;
    }


    public void insert(AuthToken authToken) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO AuthToken (authtoken, username) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, authToken.getAuthToken());
            stmt.setString(2, authToken.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    public AuthToken find(String AuthToken) throws DataAccessException {
        AuthToken authToken;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthToken WHERE authtoken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, AuthToken);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authToken = new AuthToken(rs.getString("authtoken"), rs.getString("username"));
                return authToken;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }



    public void clear() throws DataAccessException {

        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM AuthToken";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }

    }
    /**
     * createAuthToken Method
     * When a new user is made, an AuthToken needs to be made as well that is connected to
     * that user.
     * My thought was to use the userID as a way to add it, but I also think you could use
     * the (User user) that might be easier when I'm writing the code.
     * @param userID
     */

    public void createAuthToken(String userID) {

    }

    /**
     * returnAuthToken Method
     * This is used if we need to get the AuthToken for a specific user. You would pass in
     * the user and it would return the AuthToken.
     *
     * @param user
     * @return
     */

    public AuthToken returnAuthToken(User user) {

    //this is the example from the find up above.

        return null;

    }

    /**
     * deleteAuthToken
     * This would delete the AuthToken for a specific user.
     *
     * Not sure if I need to have individual clear methods or one big clear for all four.
     * @param userID
     */

    public void clearAuthToken(String userID) {

    }




}

