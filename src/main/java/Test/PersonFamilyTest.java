package Test;

import DataAccess.*;
import Request.PersonFamilyRequest;
import Result.PersonFamilyResult;
import Service.PersonFamilyService;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonFamilyTest {

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
    public void PersonFamilyPass() throws DataAccessException {

        userDao.insert(bestUser);
        eventDao.insert(bestEvent);
        personDao.insert(bestPerson);
        authDao.insert(bestAuthToken);

        db.closeConnection(true);

        PersonFamilyRequest request = new PersonFamilyRequest("cf7a368f");

        PersonFamilyService service = new PersonFamilyService();
        PersonFamilyResult result = service.person(request);

        assertEquals(result.getData().get(0).getPersonID(), "Kyle_123A");
        assertEquals(result.getData().get(0).getAssociatedUsername(), "kyle");
        assertEquals(result.getData().get(0).getFirstName(), "KYLE");


    }

    @Test
    public void PersonFamilyFail() throws DataAccessException {

        userDao.insert(bestUser);
        eventDao.insert(bestEvent);
        personDao.insert(bestPerson);
        authDao.insert(bestAuthToken);

        db.closeConnection(true);

        PersonFamilyRequest request = new PersonFamilyRequest("cf7a3683243f");

        PersonFamilyService service = new PersonFamilyService();
        PersonFamilyResult result = service.person(request);

        assertEquals(result.getMessage(), "Error: There is no correct auth token in Person Family Service.");

    }

}
