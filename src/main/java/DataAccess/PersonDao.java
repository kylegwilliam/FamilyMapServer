package DataAccess;

import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PersonDao Class
 * Database operations that need to be preformed by the Person
 */

public class PersonDao {

    private final Connection conn;

    public PersonDao(Connection conn) {this.conn = conn;}

    public void insert(Person person) throws DataAccessException {
        String sql = "INSERT INTO Persons (personID, Username, firstName, lastName, gender, " +
                "fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    public Person find(String personID) throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("Username"),
                        rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("fatherID"),
                        rs.getString("motherID"), rs.getString("spouseID"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
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
            String sql = "DELETE From Persons";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }




    /**
     * fillGenerations Method
     * This would add a set number of generations (the int that is passed in) to the
     * individual (personID). Allowing him to have more information connected to him or herself
     *
     * @param personID
     */


    public void fillGenerations(String personID, Integer generations) {


    }

    /**
     * personObject Method
     * This allows you to return a single person object by passing in their personID
     * (I believe this is important becasue it will change a json string into a
     * java object, which we can use in java)
     *
     * @param personID
     * @return
     */

    public Person personObject(String personID) {
        return null;
    }

    /**
     * fillPerson method
     * This will fill person information on a specific user. (by using his or her username)
     *
     * @param username
     * @return
     */

    public Person fillPerson(String username) {
        return null;
    }

    /**
     * getFamily Method
     * This would be used to return all the family members of the individual
     * who's AuthToken that is passed in.
     *
     * It says in the code that it needs to be done by using the AuthToken.
     * @param Username
     * @return
     */

    public List<Person> getFamily(String Username) throws DataAccessException {
        //I think I would want to use something similar to the find function
        //Person[] personArray = new Person[0];//how will I know the potential size?
        //make a list, then go form list to array once i know the size i can add it.

        List<Person> personList = new ArrayList<Person>();

        int count = 0;
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE Username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, Username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("Username"),
                        rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("fatherID"),
                        rs.getString("motherID"), rs.getString("spouseID"));

                personList.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        if (personList.size() > 0) {
            return personList;
        }

        return null;

    }

    /**
     * clearPerson Method
     * This would clear all the person data from the database by passing in the userID
     *
     * Not sure if I need to have individual clear methods or one big clear for all four.
     * @param Username
     */

    public void clearPerson(String Username) throws DataAccessException {

        ResultSet rs = null;
        String sql = "DELETE FROM Persons WHERE Username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, Username);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
