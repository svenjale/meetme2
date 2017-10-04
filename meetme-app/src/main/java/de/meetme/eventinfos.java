package de.meetme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;


public class eventinfos extends Activity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private Firebase databaseEvents;
    private Firebase databaseEventteilnehmer;
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
    private ValueEventListener eventListener;

    private static final String FIREBASE_URL = "https://smap-dhbw2.firebaseio.com";

    private String uebergebeneID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventinfos);

        Intent intent = getIntent();
        uebergebeneID = intent.getStringExtra("ID");

        databaseEvents = new Firebase(FIREBASE_URL).child("events").child(uebergebeneID);
        databaseEventteilnehmer = new Firebase(FIREBASE_URL).child("eventteilnehmer");

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

    }

    @Override
    public void onStart(){
        super.onStart();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Event event = dataSnapshot.getValue(Event.class);
                Toast.makeText(eventinfos.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                ((TextView) findViewById(R.id.textView7)).setText(event.getEventname());
                ((TextView) findViewById(R.id.textView31)).setText(event.getBeschreibung());
                ((TextView) findViewById(R.id.textView24)).setText(event.getOrt());
                ((TextView) findViewById(R.id.textView19)).setText(event.getDatum());
                ((TextView) findViewById(R.id.textView21)).setText(event.getUhrzeit());
                ((TextView) findViewById(R.id.textView17)).setText(event.getOrganisatorID());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(eventinfos.this, "Fehler beim Laden der Eventdetails.", Toast.LENGTH_SHORT).show();
            }
        };
        databaseEvents.addValueEventListener(eventListener);

        // Hilfe: https://github.com/firebase/quickstart-android/blob/master/database/app/src/main/java/com/google/firebase/quickstart/database/PostDetailActivity.java#L84-L106

    }
    //teilnehmen überarbeiten

    public void onClick(View view) {
        if (view == button9) {
           if (teilnehmen(uebergebeneID)==true){
                Toast.makeText(this, "Erfolgreich angemeldet. See you soon!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Da hat etwas nicht geklappt.", Toast.LENGTH_LONG).show();
            }
        }
        if (view == button2) {
            Intent Profil = new Intent(eventinfos.this, profilansicht.class);
            startActivity(Profil);
        }
        if (view == button7) {
            Intent Walk = new Intent(eventinfos.this, Eventliste_activity.class);
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
            Teilnehmerliste.putExtra("ID", uebergebeneID);
            startActivity(Teilnehmerliste);
        }
    }

    public boolean teilnehmen (String eventID){
        boolean erfolg = false;
        String teilnehmerID = firebaseAuth.getInstance().getCurrentUser().getUid();
        databaseEventteilnehmer.child(eventID).child("teilnehmer").setValue(teilnehmerID);
        // in der obigen Zeile: Funktion für mehrere Teinehmer einbinden
        return true; // hier return erfolg
    }
    //Notiz Johann: https://stackoverflow.com/questions/37031222/firebase-add-new-child-with-specified-name
    // if Abfrage prüft nicht wirklich den Erfolg, Code einfügen!


   // public String getProfilName (String organisatorID){
    //}

/*
    public String getOrganisatorName (String organisatorID){

        DatabaseReference vornameWert = databaseProfiles.child(organisatorID).child("vorname");
        vornameWert.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String vorname = dataSnapshot.getValue(String.class);
                eventinfos.vollerName =eventinfos.vollerName+vorname;
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
                eventinfos.vollerName=eventinfos.vollerName+" "+name;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return eventinfos.vollerName;
    }
    */
}
