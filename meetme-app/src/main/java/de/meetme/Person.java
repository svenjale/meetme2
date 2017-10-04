package de.meetme;

public class Person {
    String vorname;
    String kontakt;
    String rolle;
    String name;
   /* boolean model;
    boolean fotograf;
    boolean organisator;
    boolean visagist;*/

    public Person (String name, String vorname, String rolle, String kontakt){
        this.vorname = vorname;
        this.kontakt=kontakt;
        this.rolle=rolle;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getVorname() {
        return this.vorname;
    }

    public String getKontakt() {
        return this.kontakt;
    }
    public String getRolle (){
        return this.rolle;
    }

}
