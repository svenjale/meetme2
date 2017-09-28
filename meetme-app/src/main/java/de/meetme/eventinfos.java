package de.meetme;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import de.meetme.*;

public class eventinfos extends Activity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseEventteilnehmer;
    private DatabaseReference databaseProfiles;
    private DatabaseReference databaseEvents;
    private Button button9;
    private TextView textView7;
    private TextView textView31;
    private TextView textView24;
    private TextView textView19;
    private TextView textView17;
    private TextView textView21;
    private Button button2;
    private Button button7;
    private Button button3;
    private Button button6;
    private Button button11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventinfos);
        databaseEventteilnehmer = FirebaseDatabase.getInstance().getReference("eventteilnehmer");
        databaseEvents = FirebaseDatabase.getInstance().getReference("events");
        databaseProfiles = FirebaseDatabase.getInstance().getReference("profiles");

        button9 = (Button) findViewById(R.id.button9);
        button11 = (Button) findViewById(R.id.button11);
        button7 = (Button) findViewById(R.id.button7);
        button3 = (Button) findViewById(R.id.button3);
        button6 = (Button) findViewById(R.id.button6);
        button2 = (Button) findViewById(R.id.button2);

        textView7 = (TextView) findViewById(R.id.textView7);
        textView31 = (TextView) findViewById(R.id.textView31);
        textView24 = (TextView) findViewById(R.id.textView24);
        textView19 = (TextView) findViewById(R.id.textView19);
        textView17 = (TextView) findViewById(R.id.textView17);
        textView21 = (TextView) findViewById(R.id.textView21);

        button11.setOnClickListener(this);
        button7.setOnClickListener(this);
        button3.setOnClickListener(this);
        button6.setOnClickListener(this);
        button2.setOnClickListener(this);
        button9.setOnClickListener(this);

        eventinfosAnzeigen(Event.aktuelleEventID);
    }

    public void onClick(View view) {
        /*if (view == button9) {
           if (teilnehmen(hierEventIDübergeben)==true){
                Toast.makeText(this, "Erfolgreich angemeldet. See you soon!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Ups. Da hat etwas nicht geklappt.", Toast.LENGTH_LONG).show();
            }
        }*/
        if (view == button2) {
            Intent Profil = new Intent(eventinfos.this, profilansicht.class);
            startActivity(Profil);
        }
        if (view == button7) {

            Intent Walk = new Intent(eventinfos.this, eventlist.class);
            startActivity(Walk);
        }
        if (view == button3) {

            Intent Map = new Intent(eventinfos.this, map.class);
            startActivity(Map);
        }
        if (view == button6) {

            Intent Kontakte = new Intent(eventinfos.this, kontakte.class);
            startActivity(Kontakte);

        }
        if (view == button11) {

            Intent Teilnehmerliste = new Intent(eventinfos.this, teilnehmerlist.class);
            startActivity(Teilnehmerliste);
        }
    }

    public boolean teilnehmen (String eventID){
        boolean erfolg = false;
        String teilnehmerID = firebaseAuth.getInstance().getCurrentUser().getUid();
        databaseEventteilnehmer.child(eventID).child("teilnehmer").setValue(teilnehmerID);
        return erfolg;
    }
    //Notiz Johann: https://stackoverflow.com/questions/37031222/firebase-add-new-child-with-specified-name
    // if Abfrage prüft nicht wirklich den Erfolg, Code einfügen!


    public void eventinfosAnzeigen(String eventID){

        DatabaseReference nameWert = databaseEvents.child(eventID).child("eventname");
        nameWert.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                textView7.setText(name);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        DatabaseReference beschreibungWert = databaseEvents.child(eventID).child("beschreibung");
        beschreibungWert.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String beschreibung = dataSnapshot.getValue(String.class);
                textView31.setText(beschreibung);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        DatabaseReference ortWert = databaseEvents.child(eventID).child("ort");
        ortWert.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ort = dataSnapshot.getValue(String.class);
                textView24.setText(ort);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        DatabaseReference datumWert = databaseEvents.child(eventID).child("datum");
        datumWert.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String datum = dataSnapshot.getValue(String.class);
                textView19.setText(datum);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
       DatabaseReference organisatorWert = databaseEvents.child(eventID).child("organisatorID");
        organisatorWert.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String organisatorID = dataSnapshot.getValue(String.class);
                //String nameorg = getOrganisatorName(organisatorID);
                //textView17.setText(nameorg);
                textView17.setText(organisatorID);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });




        DatabaseReference uhrzeitWert = databaseEvents.child(eventID).child("uhrzeit");
        uhrzeitWert.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String uhrzeit = dataSnapshot.getValue(String.class);
                textView21.setText(uhrzeit);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public String getOrganisatorName (String organisatorID){

        DatabaseReference vornameWert = databaseProfiles.child(organisatorID).child("vorname");
        vornameWert.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String vorname = dataSnapshot.getValue(String.class);
                Person.vollerName=Person.vollerName+vorname;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        DatabaseReference nachnameWert = databaseProfiles.child(organisatorID).child("name");
        nachnameWert.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                Person.vollerName=Person.vollerName+" "+name;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return Person.vollerName;

    }


}
