package de.meetme;


import android.app.Activity;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


import de.meetme.R;


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

    public static String aktuelleEventID ="";

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

    public static void eventinfosAnzeigen (String eventID){

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
