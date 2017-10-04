package de.meetme;

/**
 * Created by kortsch on 04.10.2017.
 */

public class Person {
    String vorname;
    String kontakt;
    String rolle;
    String name;
   /* boolean model;
    boolean fotograf;
    boolean organisator;
    boolean visagist;*/

   public Person(){
   }

    public Person (String name, String vorname, String rolle, String kontakt){
        this.vorname = vorname;
        this.kontakt=kontakt;
        this.rolle=rolle;
        this.name = name;
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
}
