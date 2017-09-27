package de.meetme;

public class Person {
    String name;
    String vorname;
    boolean model = false;
    boolean fotograf = false;
    boolean organisator = false;
    boolean visagist = false;
    String kontakt;


    public Person (String name, String vorname, boolean model, boolean fotograf, boolean organisator, boolean visagist, String kontakt){
        this.name = name;
        this.vorname = vorname;
        this.model=model;
        this.fotograf=fotograf;
        this.organisator=organisator;
        this.visagist=visagist;
        this.kontakt=kontakt;
    }
}
