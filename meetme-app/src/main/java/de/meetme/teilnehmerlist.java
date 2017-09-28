package de.meetme;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.meetme.R;



public class teilnehmerlist extends Activity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseEventteilnehmer;
    private DatabaseReference databaseProfiles;
    private DatabaseReference databaseEvents;

    private TextView textView46;
    private TextView textView43;
    private TextView textView44;
    private TextView textView45;
    private TextView textView49;

    public static String vollerTeilnehmerName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teilnehmerlist);

        databaseEventteilnehmer = FirebaseDatabase.getInstance().getReference("eventteilnehmer");
        databaseEvents = FirebaseDatabase.getInstance().getReference("events");
        databaseProfiles = FirebaseDatabase.getInstance().getReference("profiles");

        textView46 = (TextView) findViewById(R.id.textView46);
        textView43 = (TextView) findViewById(R.id.textView43);
        textView44 = (TextView) findViewById(R.id.textView44);
        textView45 = (TextView) findViewById(R.id.textView45);
        textView49 = (TextView) findViewById(R.id.textView49);


        teilnehmerlisteAnzeigen();
    }

    public void teilnehmerlisteAnzeigen (){

        DatabaseReference orgateilnehmer = databaseEventteilnehmer.child(Event.aktuelleEventID).child("organisator");
        orgateilnehmer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                textView46.setText(name);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        DatabaseReference teilnehmer1 = databaseEventteilnehmer.child(Event.aktuelleEventID).child("teilnehmer1");
        teilnehmer1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                textView43.setText(name);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        DatabaseReference teilnehmer2 = databaseEventteilnehmer.child(Event.aktuelleEventID).child("teilnehmer2");
        teilnehmer2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                textView44.setText(name);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        DatabaseReference teilnehmer3 = databaseEventteilnehmer.child(Event.aktuelleEventID).child("teilnehmer3");
        teilnehmer3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                textView45.setText(name);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        DatabaseReference teilnehmerDEMO = databaseEventteilnehmer.child(Event.aktuelleEventID).child("teilnehmer");
        teilnehmerDEMO.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                textView49.setText(name);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    public String getName (String teilnehmerID){

        DatabaseReference vornameWert = databaseProfiles.child(teilnehmerID).child("vorname");
        vornameWert.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String vorname = dataSnapshot.getValue(String.class);
                teilnehmerlist.vollerTeilnehmerName = teilnehmerlist.vollerTeilnehmerName+vorname;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        DatabaseReference nachnameWert = databaseProfiles.child(teilnehmerID).child("name");
        nachnameWert.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                teilnehmerlist.vollerTeilnehmerName= teilnehmerlist.vollerTeilnehmerName+" "+name;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return  teilnehmerlist.vollerTeilnehmerName;

    }
}
