package DataAccess;

import model.User;
import java.sql.*;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.*;
import java.util.Objects;


/**
 * UserDao Class
 * Database operations that need to be preformed by the User
 */

public class UserDao {

    private final Connection conn;

    public UserDao(Connection conn)
    {
        this.conn = conn;
    }


    public void insert(User user) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Users (Username, Password, Email, firstName, " +
                "lastName, gender, personID) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }


    public User find(String Username) throws DataAccessException {
        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM Users WHERE Username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, Username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("Username"), rs.getString("Password"),
                        rs.getString("Email"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("personID"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");
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
            String sql = "DELETE FROM Users";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }

    }


    /**
     * createUser Method
     * If someone wants to create a user for the Family Map Server
     * This would add it to the database for them.
     * @param user
     */

    public void createUser (User user) throws DataAccessException {
        //This should create a new user. generate 4 generations of ancestors, returns
        //logs the user in, and returns the auth token.
        //return null;
        //WAIT UNTIL FILL! VIDEO ABOUT THAT.
        //How to make 4 generations of ancestors?!?!?

    }


    /**
     * validateUser Method
     * This would be a boolean to make sure that the user is in the system.
     * It would check to see if the person has a username as well as a password.
     * @param username
     * @param password
     * @return
     */


    public boolean validateUser(String username, String password) {
        return true;
    }

    /**
     * getUserByID Method
     * This would pass in the userID into the database and then allow us to access who
     * the user is.
     *
     * @param userID
     * @return
     */

    public User getUserByID(String userID) {

        return null;
    }

    /**
     * clearUser Method
     * This would be used if a person wants to clear all of their information from the database.
     * You would need to pass in the users userID, and then it would clear their data.
     * NEEDS TO REMOVE ALL THE DATA
     *
     * Not sure if I need to have individual clear methods or one big clear for all four.
     *
     * @param userID
     */

    public void clearUser(String userID) {

    }





}
