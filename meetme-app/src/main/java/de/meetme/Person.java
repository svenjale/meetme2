package de.meetme;

/**
 * Created by kortsch on 04.10.2017.
 */

public class Person {
    String vorname;
    String kontakt;
    String rolle;
    String name;
    String personID;
   /* boolean model;
    boolean fotograf;
    boolean organisator;
    boolean visagist;*/

   public Person(){
   }

    public Person (String name, String vorname, String rolle, String kontakt, String personID){
        this.vorname = vorname;
        this.kontakt=kontakt;
        this.rolle=rolle;
        this.name = name;
        this.personID = personID;
    }

    public String getVorname() {
        return vorname;
    }

    public String getKontakt() {
        return kontakt;
    }

    public String getRolle() {
        return rolle;
    }

    public String getName() {
        return name;
    }

    public String getPersonID() { return personID;}

    public static Person idPersonAbfrage (String id){
        Person person = new Person();
        // ***Code zur DB Abfrage***
        return person;
    }
}
