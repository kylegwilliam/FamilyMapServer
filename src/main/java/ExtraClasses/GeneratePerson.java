package ExtraClasses;

import Request.FillRequest;
import com.google.gson.Gson;
import model.Event;
import model.Person;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

//13 years after parents marriage
//At least 13 years old to be married
//Can't die before a child is born
//Women can give birth if they are over 50 years old
//Birth, marriage, death
//120 is the max age
//Marriage event will have unique eventID but will have same year and location
//Event area is random.


public class GeneratePerson {

    public ArrayList<Person> Persons = new ArrayList<Person>();
    public ArrayList<Event> Events = new ArrayList<Event>();
    private ArrayList<Event> marriageDay = new ArrayList<Event>();
    public int initialGenerations = -1;



    //I would think it would just be "m" for male and "f" for female
    public Person generatePerson(String gender, int generations, User userObj, int year) throws FileNotFoundException {

        if (initialGenerations == -1) {
            initialGenerations = generations;
        }


        Person mother = null;
        Person father = null;

        if (generations > 0) {

            mother = generatePerson("f", generations - 1, userObj, year - 30);
            father = generatePerson("m", generations - 1, userObj, year - 30);


            Events.addAll(marriageEvent(mother, father, year));

            Events.add(deathEvent(mother, year));
            Events.add(deathEvent(father, year));

        }

        //Not reading in the files correctly.
        ArrayList<String> maleNamesList = BuffReader("/Users/kylegwilliam/Desktop/FamilyMapServerStudent-master/json/mnames.json");
        ArrayList<String> femaleNamesList = BuffReader("/Users/kylegwilliam/Desktop/FamilyMapServerStudent-master/json/fnames.json");
        ArrayList<String> sirNamesList = BuffReader("/Users/kylegwilliam/Desktop/FamilyMapServerStudent-master/json/snames.json");

        ArrayList<String> lastNameList = new ArrayList<String>();

        for (String s : sirNamesList) {
            if (s.equals("}") || s.equals("]}")) {
                continue;
            }
            else {
                lastNameList.add(s);
            }
        }

        Person person = new Person(null, null, null,
                null, null, null, null, null);

        String MaleUser = maleNamesList.get(randomInt(1, maleNamesList.size() - 1));
        String FemaleUser = femaleNamesList.get(randomInt(1, femaleNamesList.size() - 1));

        //String lastName = sirNamesList.get(randomInt(1, sirNamesList.size()) - 1);
        String lastName = lastNameList.get(randomInt(1, lastNameList.size()) - 1);


        if (initialGenerations == generations) {

            person.setAssociatedUsername(userObj.getUsername());
            person.setFirstName(userObj.getFirstName());
            person.setLastName(userObj.getLastName());
            person.setPersonID(userObj.getPersonID());
            person.setGender(userObj.getGender());
            person.setFatherID(father.getPersonID());
            person.setMotherID(mother.getPersonID());

            Persons.add(person);
            Events.add(birthEvent(person, year));

            return person;

        }

        else {

            if (gender.equals("m")) {

                person.setAssociatedUsername(userObj.getUsername());
                person.setFirstName(MaleUser);
                person.setGender("m");

            } else {
                person.setAssociatedUsername(userObj.getUsername());
                person.setFirstName(FemaleUser);
                person.setGender("f");

            }

            person.setLastName(lastName);
            person.setPersonID(UUID.randomUUID().toString());
            person.setSpouseID(null);

        }



        if (mother != null && father != null) {
            person.setMotherID(mother.getPersonID());
            person.setFatherID(father.getPersonID());
        }


        Persons.add(person);

        Events.add(birthEvent(person, year));

        return person;

    }


    public int randomInt(int min, int max) {
        int num = (int) (Math.random() * (max - min + 1)) + min;
        return num;
    }

    public ArrayList<String> BuffReader(String filePath) {
        try {
            BufferedReader readLocation = new BufferedReader(new FileReader(filePath));
            ArrayList<String> locationList = new ArrayList<>();

            String locationLine = readLocation.readLine();
            while (locationLine != null) {

                String str1 = locationLine.replace(" ", "");
                String str2 = str1.replace("\"", "");
                String goodString = str2.replace(",", "");

                locationList.add(goodString);
                locationLine = readLocation.readLine();
            }
            readLocation.close();

            return locationList;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("FilePath wrong Fill Service");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println();
        }

        return null;
    }

    public Event birthEvent(Person person, int year) throws FileNotFoundException {

        LocationData locationData = locationData();
        Location location = locationList(locationData);

        float Latitude = Float.parseFloat(location.latitude);
        float Longitude = Float.parseFloat(location.latitude);
        String Country = location.country;
        String City = location.city;

        Event birthEvent = new Event(null, null, null, 0,
                0, null, null, null, 0);

        //int birthYear = randomInt(2000, 2002);

        birthEvent.setAssociatedUsername(person.getAssociatedUsername());
        birthEvent.setEventID(UUID.randomUUID().toString());
        birthEvent.setPersonID(person.getPersonID());
        birthEvent.setLatitude(Latitude);
        birthEvent.setLongitude(Longitude);
        birthEvent.setCountry(Country);
        birthEvent.setCity(City);
        birthEvent.setEventType("birth");
        birthEvent.setYear(year);

        return birthEvent;

    }

    //
    public ArrayList<Event> marriageEvent(Person wife, Person husband, int year) throws FileNotFoundException {

        ArrayList<Event> weddingDay = new ArrayList<Event>();

        LocationData locationData = locationData();

        Location location = locationList(locationData);


        Event brideEvent = new Event(null, null, null, 0,
                0, null, null, null, 0);
        //Need to fix Lat, Long, Country, and city
        float Latitude = Float.parseFloat(location.latitude);
        float Longitude = Float.parseFloat(location.longitude);
        String Country = location.country;
        String City = location.city;

        //Bride
        brideEvent.setAssociatedUsername(wife.getAssociatedUsername());
        brideEvent.setEventID(UUID.randomUUID().toString());
        brideEvent.setPersonID(wife.getPersonID());
        brideEvent.setLatitude(Latitude);
        brideEvent.setLongitude(Longitude);
        brideEvent.setCountry(Country);
        brideEvent.setCity(City);
        brideEvent.setEventType("marriage");
        brideEvent.setYear(year + 17);
        wife.setSpouseID(husband.getPersonID());


        Event groomEvent = new Event(null, null, null, 0,
                0, null, null, null, 0);

        //Groom
        groomEvent.setAssociatedUsername(husband.getAssociatedUsername());
        groomEvent.setEventID(UUID.randomUUID().toString());
        groomEvent.setPersonID(husband.getPersonID());
        groomEvent.setLatitude(Latitude);
        groomEvent.setLongitude(Longitude);
        groomEvent.setCountry(Country);
        groomEvent.setCity(City);
        groomEvent.setEventType("marriage");
        groomEvent.setYear(year + 17);
        husband.setSpouseID(wife.getPersonID());

        weddingDay.add(brideEvent);
        weddingDay.add(groomEvent);

        return weddingDay;

    }

    public Event deathEvent(Person person, int year) throws FileNotFoundException {

        LocationData locationData = locationData();

        Location location = locationList(locationData);

        float Latitude = Float.parseFloat(location.latitude);
        float Longitude = Float.parseFloat(location.longitude);
        String Country = location.country;
        String City = location.city;

        Event deathEvent = new Event(null,null,null,0,
                0,null,null, null, 0);


        deathEvent.setAssociatedUsername(person.getAssociatedUsername());
        deathEvent.setEventID(UUID.randomUUID().toString());
        deathEvent.setPersonID(person.getPersonID());
        deathEvent.setLatitude(Latitude);
        deathEvent.setLongitude(Longitude);
        deathEvent.setCountry(Country);
        deathEvent.setCity(City);
        deathEvent.setEventType("death");
        //Maybe add multiplier here?
        deathEvent.setYear(birthEvent(person, year).getYear() + 77);

        return deathEvent;

    }

    public ArrayList<Person> Persons() {
        return Persons;
    }

    public ArrayList<Event> Events() {
        return Events;
    }

    public LocationData locationData() throws FileNotFoundException {

        Gson gson = new Gson();

        String locationPath = "json/locations.json";

        File myFile = new File(locationPath);

        BufferedReader buffReader = null;
        try {
            buffReader = new BufferedReader(new FileReader(myFile));

        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        }

        LocationData locationData = gson.fromJson(buffReader, LocationData.class);

        return locationData;

    }

    public Location locationList(LocationData locationData) {

        Location[] locations = locationData.getData();

        int randomInt = randomInt(0, locations.length - 1);

        return locations[randomInt];

    }

}