package Test;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import model.AuthToken;
import model.Event;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;


import static org.junit.jupiter.api.Assertions.*;

public class AuthTokenDaoTest {

    private Database db;
    private AuthToken bestAuthToken;
    private AuthTokenDao authDao;


    @BeforeEach
    public void setUp() throws DataAccessException {

        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        bestAuthToken = new AuthToken("cf7a368f", "kylegwilliam");
        //Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //Then we pass that connection to the EventDAO so it can access the database
        authDao = new AuthTokenDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        //Here we close the connection to the database file so it can be opened elsewhere.
        //We will leave commit to false because we have no need to save the changes to the database
        //between test cases
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {

        authDao.insert(bestAuthToken);
        AuthToken compareTest = authDao.find(bestAuthToken.getAuthToken());
        assertNotNull(compareTest);

        assertEquals(bestAuthToken, compareTest);

    }

    @Test
    public void insertFail() throws DataAccessException {
        //lets do this test again but this time lets try to make it fail
        //if we call the method the first time it will insert it successfully
        authDao.insert(bestAuthToken);
        //but our sql table is set up so that "eventID" must be unique. So trying to insert it
        //again will cause the method to throw an exception
        //Note: This call uses a lambda function. What a lambda function is is beyond the scope
        //of this class. All you need to know is that this line of code runs the code that
        //comes after the "()->" and expects it to throw an instance of the class in the first parameter.
        assertThrows(DataAccessException.class, ()-> authDao.insert(bestAuthToken));
    }

    @Test
    public void findPass() throws DataAccessException {

        authDao.insert(bestAuthToken);
        AuthToken compareTest = authDao.find(bestAuthToken.getAuthToken());
        assertNotNull(compareTest);

        assertEquals(bestAuthToken, compareTest);

    }

    @Test
    public void findFail() throws DataAccessException {

        assertNull(authDao.find(bestAuthToken.getAuthToken()));

    }

    @Test
    public void clearPass() throws DataAccessException {

        //This is just to make sure something has been added to the database
        //Before it can be deleted

        authDao.insert(bestAuthToken);
        authDao.clear();

        assertNull(authDao.find(bestAuthToken.getAuthToken()));

    }




}
