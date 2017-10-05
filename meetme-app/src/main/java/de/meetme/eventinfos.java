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
    private Firebase databaseEventanwesende;
    private Firebase databaseProfiles;
    private Button button9;
    private Button button5;
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
    private Button button10;

    private ValueEventListener eventListener;

    private static final String FIREBASE_URL = "https://smap-dhbw2.firebaseio.com";

    private String uebergebeneID;
    //private String uebergebenerName;
    public static String orgaID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventinfos);

        Intent intent = getIntent();
        uebergebeneID = intent.getStringExtra("ID");

        databaseEvents = new Firebase(FIREBASE_URL).child("events").child(uebergebeneID);
        databaseEventteilnehmer = new Firebase(FIREBASE_URL).child("eventteilnehmer");
        databaseEventanwesende = new Firebase(FIREBASE_URL).child("eventanwesende");

        button9 = (Button) findViewById(R.id.button9);
        button11 = (Button) findViewById(R.id.button11);
        button10 = (Button) findViewById(R.id.button10);
        button7 = (Button) findViewById(R.id.button7);
        button3 = (Button) findViewById(R.id.button3);
        button6 = (Button) findViewById(R.id.button6);
        button2 = (Button) findViewById(R.id.button2);
        button5 = (Button) findViewById(R.id.button5);

        textView7 = (TextView) findViewById(R.id.textView7);
        textView31 = (TextView) findViewById(R.id.textView31);
        textView24 = (TextView) findViewById(R.id.textView24);
        textView19 = (TextView) findViewById(R.id.textView19);
        textView17 = (TextView) findViewById(R.id.textView17);
        textView21 = (TextView) findViewById(R.id.textView21);

        textView17.setOnClickListener(this);

        button11.setOnClickListener(this);
        button7.setOnClickListener(this);
        button3.setOnClickListener(this);
        button6.setOnClickListener(this);
        button2.setOnClickListener(this);
        button9.setOnClickListener(this);
        button10.setOnClickListener(this);
        button5.setOnClickListener(this);

    }

    @Override
    public void onStart(){
        super.onStart();
        //Event Laden
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Event event = dataSnapshot.getValue(Event.class);
                if (event != null) {
                    ((TextView) findViewById(R.id.textView7)).setText(event.getEventname());
                    ((TextView) findViewById(R.id.textView31)).setText(event.getBeschreibung());
                    ((TextView) findViewById(R.id.textView24)).setText(event.getOrt());
                    ((TextView) findViewById(R.id.textView19)).setText(event.getDatum());
                    ((TextView) findViewById(R.id.textView21)).setText(event.getUhrzeit());
                    orgaID = event.getOrganisatorID();
                    //uebergebenerName=event.getEventname();
                    // Profil laden
                    databaseProfiles = new Firebase(FIREBASE_URL).child("profiles").child(orgaID);
                    ValueEventListener organisatorListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                            Person organisator = dataSnapshot.getValue(Person.class);
                            String organisatorName=organisator.getVorname()+" "+organisator.getName()+" ("+organisator.getRolle()+")";
                            ((TextView) findViewById(R.id.textView17)).setText(organisatorName);
                        }
                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            Toast.makeText(eventinfos.this, "Fehler beim Laden der Eventdetails.", Toast.LENGTH_SHORT).show();
                        }
                    };
                    databaseProfiles.addValueEventListener(organisatorListener);
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(eventinfos.this, "Fehler beim Laden der Eventdetails.", Toast.LENGTH_SHORT).show();
            }
        };
        databaseEvents.addValueEventListener(eventListener);
    }
    // Hilfe: https://github.com/firebase/quickstart-android/blob/master/database/app/src/main/java/com/google/firebase/quickstart/database/PostDetailActivity.java#L84-L106


    public void onClick(View view) {
        if (view == button9) {
            databaseEventteilnehmer.child(uebergebeneID).child(firebaseAuth.getInstance().getCurrentUser().getUid()).setValue(profilansicht.aktuellerUser);
            Toast.makeText(this, "Erfolgreich angemeldet. Bis bald!", Toast.LENGTH_LONG).show();
        }
        if (view == button5) {
            databaseEventanwesende.child(uebergebeneID).child(firebaseAuth.getInstance().getCurrentUser().getUid()).setValue(profilansicht.aktuellerUser);
            Toast.makeText(this, "Check-In erfolgreich. Viel Spaß beim Event!", Toast.LENGTH_LONG).show();
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
            Intent Map = new Intent(eventinfos.this, MapsActivity.class);
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
        if (view == button10) {
            Intent Anwesendeliste = new Intent(eventinfos.this, anwesendelist.class);
            Anwesendeliste.putExtra("ID", uebergebeneID);
            startActivity(Anwesendeliste);
        }
        if (view == textView17) {
            if (orgaID.equals(profilansicht.aktuellerUser.getPersonID())){
                Intent Profil = new Intent(eventinfos.this, profilansicht.class);
                startActivity(Profil);
            }else {
                Intent profile = new Intent(eventinfos.this, profilansichtAndererUser.class);
                profile.putExtra("ID", orgaID);
                startActivity(profile);
            }
        }
    }


    //Notiz Johann: https://stackoverflow.com/questions/37031222/firebase-add-new-child-with-specified-name
    // evtl if Abfrage für Erfolg?!


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
