package Test;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import model.Person;
import model.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;



public class UserDaoTest {

    private Database db;
    private User bestUser;
    private UserDao eDao;

    @BeforeEach
    public void setUp() throws DataAccessException {

        db = new Database();

        bestUser = new User("Kyle_123A", "password", "kylegwilliam@gmail.com",
                "Kyle", "Gwilliam", "m", "123456789");

        Connection conn = db.getConnection();

        eDao = new UserDao(conn);
        eDao.clear();

        //eDao = new UserDao(conn);


    }

    @AfterEach
    public void tearDown() throws DataAccessException {

        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {

        eDao.insert(bestUser);
        User compareTest = eDao.find(bestUser.getUsername());
        assertNotNull(compareTest);

        assertEquals(bestUser, compareTest);

    }

    @Test
    public void insertFail() throws DataAccessException {

        eDao.insert(bestUser);
        assertThrows(DataAccessException.class, () -> eDao.insert(bestUser));

    }

    @Test
    public void findPass() throws DataAccessException {

        eDao.insert(bestUser);
        User compareTest = eDao.find(bestUser.getUsername());
        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);

    }

    @Test
    public void findFail() throws DataAccessException {

        assertNull(eDao.find(bestUser.getPersonID()));

    }

    @Test
    public void clearPass() throws DataAccessException {

        eDao.insert(bestUser);
        eDao.clear();

        assertNull(eDao.find(bestUser.getPersonID()));

    }
}