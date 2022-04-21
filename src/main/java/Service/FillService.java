package Service;


import DataAccess.*;
import ExtraClasses.GeneratePerson;
import ExtraClasses.Location;
import ExtraClasses.LocationData;
import Request.FillRequest;
import Result.FillResult;
import Result.RegisterResult;
import Result.SingleEventResult;
import Result.SinglePersonResult;
import model.Event;
import model.Person;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Fill Service Class
 */

public class FillService {


    /**
     * fill Method
     * @param r
     * @return
     */


    public FillResult fill(FillRequest r) throws DataAccessException {

        Database db = new Database();
        FillResult FR = new FillResult(null, false);

        try {
            UserDao UD = new UserDao(db.getConnection());
            EventDao ED = new EventDao(db.getConnection());
            PersonDao PD = new PersonDao(db.getConnection());

            User userObj = UD.find(r.getUsername());

            if (UD.find(r.getUsername()) == null) {
                FR.setMessage("Error: Username not found in FillService");
                FR.setSuccess(false);
                db.closeConnection(true);
                return FR;
            }

            //There is a problem with this line.
            ED.clearEvent(userObj.getUsername());
            PD.clearPerson(userObj.getUsername());


            int genValue = r.getGenerations();

            if (genValue == 0) {
                genValue = 4;
            }

            if (genValue < 0) {
                FR.setMessage("Error: genValue is less than 0");
                FR.setSuccess(false);
                db.closeConnection(true);
                return FR;
            }

            GeneratePerson generatePerson = new GeneratePerson();

            LocationData locationData = generatePerson.locationData();
            //int randomNumber = generatePerson.randomInt(0,10);

            String genderLetter = userObj.getGender();


            if (genderLetter.equals("m")) {
                generatePerson.generatePerson("m", genValue, userObj, 2000);
            }

            else {
                generatePerson.generatePerson("f", genValue, userObj, 2000);
            }

            ArrayList<Person> allPersons = generatePerson.Persons();
            ArrayList<Event> allEvents = generatePerson.Events();

            if (allPersons.size() == 0) {
                FR.setMessage("Error: in all persons size");
                FR.setSuccess(false);
                db.closeConnection(true);
                return FR;
            }
            else if (allEvents.size() == 0) {
                FR.setMessage("Error: with allEvents size");
                FR.setSuccess(false);
                db.closeConnection(true);
                return FR;
            }

            else {
                for (Person allPerson : allPersons) {
                    PD.insert(allPerson);
                }
                //Unique eventID.
                //Event Table.
                for (Event allEvent : allEvents) {
                    ED.insert(allEvent);
                }


                FR.setMessage("Successfully added " + allPersons.size() + " persons and " + allEvents.size() +
                        " events to the database.");
                FR.setSuccess(true);

                db.closeConnection(true);
            }



        } catch (DataAccessException | FileNotFoundException dae) {
            FR.setMessage("Error: " + dae);
            FR.setSuccess(false);
            db.closeConnection(true);

        }

        return FR;
    }

}
