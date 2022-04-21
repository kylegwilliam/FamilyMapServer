package Test;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import model.Event;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

import model.Person;


public class PersonDaoTest {

    private Database db;
    private Person bestPerson;
    private PersonDao eDao;

    @BeforeEach
    public void setUp() throws DataAccessException {

        db = new Database();

        bestPerson = new Person("Kyle_123A", "kylegwilliam", "Kyle",
                "Gwilliam", "m", "Bruce_123A", "Denise_123a",
                "Sally_123a");

        Connection conn = db.getConnection();
        db.clearTables();
        eDao = new PersonDao(conn);

    }

    @AfterEach
    public void tearDown() throws DataAccessException {

        db.closeConnection(false);
   }

    @Test
    public void insertPass() throws DataAccessException {

        eDao.insert(bestPerson);
        Person compareTest = eDao.find(bestPerson.getPersonID());
        assertNotNull(compareTest);

        assertEquals(bestPerson, compareTest);

    }

    @Test
    public void insertFail() throws DataAccessException {

        eDao.insert(bestPerson);
        assertThrows(DataAccessException.class, ()-> eDao.insert(bestPerson));

    }

    @Test
    public void findPass() throws DataAccessException {

        eDao.insert(bestPerson);
        Person compareTest = eDao.find(bestPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);

    }

    @Test
    public void findFail() throws DataAccessException {

        assertNull(eDao.find(bestPerson.getPersonID()));

    }

    @Test
    public void clearPass() throws DataAccessException {

        eDao.insert(bestPerson);
        eDao.clear();

        assertNull(eDao.find(bestPerson.getPersonID()));

    }
}