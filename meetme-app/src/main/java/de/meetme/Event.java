package de.meetme;

/**
 * Created by lebenhag on 26.09.2017.
 */

public class Event {

    String eventname;
    String beschreibung;
    String ort;
    String datum;
    String uhrzeit;

    public Event (){
    }

    public Event(String id, String eventname, String beschreibung, String ort, String datum, String uhrzeit) {
        this.eventname = eventname;
        this.beschreibung = beschreibung;
        this.ort = ort;
        this.datum = datum;
        this.uhrzeit = uhrzeit;
    }

    public String getEventname() {
        return eventname;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public String getOrt() {
        return ort;
    }

    public String getDatum() {
        return datum;
    }

    public String getUhrzeit() {
        return uhrzeit;
    }
}
