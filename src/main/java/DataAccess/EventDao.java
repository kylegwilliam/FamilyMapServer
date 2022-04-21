package DataAccess;

import model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * EventDao Class
 * Database operations that need to be preformed by the Event
 */

public class EventDao {

    private final Connection conn;

    public EventDao(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * Insert Method
     *
     * @param event
     * @throws DataAccessException
     */


    public void insert(Event event) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Events (eventID, Username, personID, latitude, longitude, " +
                "country, city, eventType, year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * Find Method
     *
     * @param eventID
     * @return
     * @throws DataAccessException
     */


    public Event find(String eventID) throws DataAccessException {
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("eventID"), rs.getString("Username"),
                        rs.getString("personID"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));
                return event;
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
            String sql = "DELETE FROM Events";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }

    }


    /**
     * returnEvent Method
     * This will take a specific id (eventID) and will return a single event object
     * (this is important because it allows us to take a json string and transform it into
     * an object that we can use in Java)
     *
     * @param Username
     * @return
     */

    public List<Event> returnEvent(String Username) throws DataAccessException {

        List<Event> eventList = new ArrayList<Event>();

        int count = 0;
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE Username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, Username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                event = new Event(rs.getString("eventID"), rs.getString("Username"),
                        rs.getString("personID"), rs.getFloat("latitude"),
                        rs.getFloat("longitude"), rs.getString("country"),
                        rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));

                eventList.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        if (eventList.size() > 0) {
            return eventList;
        }

        return null;
    }

    /**
     * familyEvents Method
     * This will return all the family evens of the current user (we will use the authToken to
     * know which user it is).
     *
     *
     * @return
     */
    public Event[] familyEvents(String authToken) {
        return null;
    }

    /**
     * fillEvents Method
     * This will add events to a certain user (by using their username)
     *
     * @param username
     * @return
     */

    public Event fillEvents(String username) {
        return null;
    }

    /**
     * clearEvent Method
     * This would clear the Event data from the database for a specific user
     *
     * Not sure if I need to have individual clear methods or one big clear for all four.
     * @param Username
     */

    public void clearEvent(String Username) throws DataAccessException {

        //Event event;
        ResultSet rs = null;
        String sql = "DELETE FROM Events WHERE Username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, Username);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
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
