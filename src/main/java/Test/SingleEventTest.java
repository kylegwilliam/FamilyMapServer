package Test;

import DataAccess.*;
import Request.SingleEventRequest;
import Result.SingleEventResult;
import Service.SingleEventService;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SingleEventTest {

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
        bestAuthToken = new AuthToken("cf7a368f", "kyle");

        bestEvent = new Event("Biking_123A", "kyle", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);

        bestPerson = new Person("Kyle_123A", "kyle", "KYLE",
                "GWILLIAM", "m", "Bruce_123A", "Denise_123a",
                "Sally_123a");

        bestUser = new User("kyle", "gwilliam", "email@gmail.com",
                "KYLE", "GWILLIAM", "m", "123456789");

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
    public void SingleEventPass() throws DataAccessException {

        userDao.insert(bestUser);
        eventDao.insert(bestEvent);
        personDao.insert(bestPerson);
        authDao.insert(bestAuthToken);

        db.closeConnection(true);

        SingleEventRequest request = new SingleEventRequest("Biking_123A", "cf7a368f");

        SingleEventService service = new SingleEventService();
        SingleEventResult result = service.event(request);

        assertEquals(result.getEventID(), "Biking_123A");
        assertEquals(result.getPersonID(), "Gale123A");
        assertEquals(result.getAssociatedUsername(), "kyle");


    }


    @Test
    public void SingleEventFail() throws DataAccessException {

        userDao.insert(bestUser);
        eventDao.insert(bestEvent);
        personDao.insert(bestPerson);
        authDao.insert(bestAuthToken);

        db.closeConnection(true);

        SingleEventRequest request = new SingleEventRequest("Biking_3A", "cf7a368f");

        SingleEventService service = new SingleEventService();
        SingleEventResult result = service.event(request);

        assertEquals(result.getMessage(), "Error: rEvent is null");

    }
}

