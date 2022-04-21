package Request;

import model.Event;
import model.Person;
import model.User;

/**
 * LoadRequest Class
 */

public class LoadRequest {

    /**
     * Variables that will be used in LoadRequests
     */

    public User[] users;
    public Person[] persons;
    public Event[] events;


    /**
     * Constructor for LoadRequest
     *
     * @param users
     * @param person
     * @param events
     */

    public LoadRequest(User[] users, Person[] person, Event[] events) {

        this.users = users;
        this.persons = person;
        this.events = events;

    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPerson() {
        return persons;
    }

    public void setPerson(Person[] person) {
        this.persons = person;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }



}
