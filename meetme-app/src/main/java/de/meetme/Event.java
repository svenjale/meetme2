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
    String organisatorID;

    public Event (){
    }

    public Event(String id, String eventname, String beschreibung, String ort, String datum, String uhrzeit, String organisatorID) {
        this.eventname = eventname;
        this.beschreibung = beschreibung;
        this.ort = ort;
        this.datum = datum;
        this.uhrzeit = uhrzeit;
        this.organisatorID = organisatorID;
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

    public String getOrganisatorID() {return organisatorID;}
}
