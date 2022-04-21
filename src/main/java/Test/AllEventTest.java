package Test;

import DataAccess.*;
import Request.AllEventRequest;
import Result.AllEventResult;
import Result.ClearResult;
import Service.AllEventsService;
import Service.ClearService;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AllEventTest {


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

    //come back to this one.

    @Test
    public void AllEventPass() throws DataAccessException {

        authDao.insert(bestAuthToken);
        eventDao.insert(bestEvent);

        db.closeConnection(true);

        AllEventRequest request = new AllEventRequest("cf7a368f");

        AllEventsService service = new AllEventsService();
        AllEventResult result = service.event(request);

        assertEquals(result.getData().get(0).getEventID(), "Biking_123A");
        assertEquals(result.getData().get(0).getPersonID(), "Gale123A");
        assertEquals(result.getData().get(0).getAssociatedUsername(), "kyle");




    }

    @Test
    public void AllEventFail() throws DataAccessException {

        eventDao.insert(bestEvent);

        db.closeConnection(true);

        AllEventRequest request = new AllEventRequest("cf7a368f");

        AllEventsService service = new AllEventsService();
        AllEventResult result = service.event(request);

        assertEquals(result.getMessage(), "Error: auth token is null");

    }

}
