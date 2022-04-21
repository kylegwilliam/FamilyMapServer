package Test;

import DataAccess.*;
import Result.ClearResult;
import Service.ClearService;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class ClearServiceTest {

    private Database db;

    private Event bestEvent;
    private EventDao eventDao;

    private Person bestPerson;
    private PersonDao personDao;

    private User bestUser;
    private UserDao userDao;

    private AuthToken bestAuthToken;
    private AuthTokenDao authDao;


    @BeforeEach
    public void setUp() throws DataAccessException {

        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();

        //and a new event with random data
        bestAuthToken = new AuthToken("cf7a368f", "kylegwilliam");

        bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);

        bestPerson = new Person("Kyle_123A", "kylegwilliam", "Kyle",
                "Gwilliam", "m", "Bruce_123A", "Denise_123a",
                "Sally_123a");

        bestUser = new User("Kyle_123A", "password", "kylegwilliam@gmail.com",
                "Kyle", "Gwilliam", "m", "123456789");

        //Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        //db.clearTables();
        //Then we pass that connection to the EventDAO so it can access the database
        authDao = new AuthTokenDao(conn);
        userDao = new UserDao(conn);
        personDao = new PersonDao(conn);
        eventDao = new EventDao(conn);

        authDao.clear();
        userDao.clear();
        personDao.clear();
        eventDao.clear();


    }


    @Test
    public void clearPass() throws DataAccessException {

        eventDao.insert(bestEvent);
        personDao.insert(bestPerson);
        authDao.insert(bestAuthToken);
        userDao.insert(bestUser);

        db.closeConnection(true);

        ClearService clearService = new ClearService();
        ClearResult result = clearService.clear();

        assertEquals(result.getMessage(), "Clear succeeded.");


    }

    @Test
    public void clearFail() throws DataAccessException {

        eventDao.insert(bestEvent);
        personDao.insert(bestPerson);
        authDao.insert(bestAuthToken);
        userDao.insert(bestUser);

        ClearService clearService = new ClearService();
        //not sure how to finish these tests?
        ClearResult result = clearService.clear();

        assertEquals(result.getMessage(), "Error: DataAccess.DataAccessException: SQL Error encountered while clearing tables");
        db.closeConnection(true);

    }

}
