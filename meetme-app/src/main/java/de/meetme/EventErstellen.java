package de.meetme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;


public class EventErstellen extends FragmentActivity implements View.OnClickListener  {
// implements View.OnClickListener wieder einfügen
    private Button button8;
    private EditText editText5;              //Eventname
    private EditText editText8;             //Beschreibung
    private EditText editText10;            //Ort
    private EditText editText11;            //Datum
    private EditText editText13;//Uhrzeit

    private TextView textView12;
    private TextView textView11;
    private TextView textView18;

    private FirebaseAuth firebaseAuth;
    private Firebase databaseEvents;
    private Firebase databaseEventteilnehmer;
    private Firebase databaseEventanwesende;
    private Firebase databaseTeilnahmen;

    private static final String FIREBASE_URL = "https://smap-dhbw2.firebaseio.com";

    private String IDuebergabe ="";
    private String bearbeitenID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventerstellen);


      databaseEvents = new Firebase(FIREBASE_URL).child("events");
        databaseEventteilnehmer = new Firebase(FIREBASE_URL).child("eventteilnehmer");
        databaseEventanwesende = new Firebase(FIREBASE_URL).child("eventanwesende");
        databaseTeilnahmen = new Firebase(FIREBASE_URL).child("teilnahmen");




        editText5 = (EditText) findViewById(R.id.editText5);
        editText8 = (EditText) findViewById(R.id.editText8);
        editText10 = (EditText) findViewById(R.id.editText10);
        editText11 = (EditText) findViewById(R.id.editText11);
        editText13 = (EditText) findViewById(R.id.editText13);
        button8 = (Button) findViewById(R.id.button8);

        textView12=(TextView) findViewById(R.id.textView12);
        textView11=(TextView) findViewById(R.id.textView11);
        textView18=(TextView) findViewById(R.id.textView18);

        /*editText13.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    showTimePickerDialog(editText13);
            }
        });
        editText11.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePickerDialog(editText11);
            }
        });*/
        editText13.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showTimePickerDialog(editText13);
                }
            }
        });
        editText11.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickerDialog(editText11);
                }
            }
        });



        button8.setOnClickListener(this);

        textView12.setOnClickListener(this);
        textView11.setOnClickListener(this);


    }

    public void onStart(){
        super.onStart();
        try{
            Intent intent = getIntent();
            bearbeitenID = intent.getStringExtra("eventID");
            textView18.setText("Event bearbeiten");
            databaseEvents.child(bearbeitenID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Event event = dataSnapshot.getValue(Event.class);
                    if (event != null) {
                        ((TextView) findViewById(R.id.editText5)).setText(event.getEventname());
                        ((TextView) findViewById(R.id.editText8)).setText(event.getBeschreibung());
                        ((TextView) findViewById(R.id.editText10)).setText(event.getOrt());
                        ((TextView) findViewById(R.id.editText11)).setText(event.getDatum());
                        ((TextView) findViewById(R.id.editText13)).setText(event.getUhrzeit());
                    }
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });
        }catch (NullPointerException np){}

    }


    private void createevent()  {
        String eventname = editText5.getText().toString().trim();               //Eventname wird aus Editfeld geholt
        String beschreibung = editText8.getText().toString().trim();            //Beschreibung wird aus Editfeld geholt
        String ort = editText10.getText().toString().trim();                    //Ort wird aus Editfeld geholt
        String datum = editText11.getText().toString().trim();                  //Datum wird aus Editfeld geholt
        String uhrzeit = editText13.getText().toString().trim();               //Uhrzeit wird aus Editfeld geholt
        String organisatorID = firebaseAuth.getInstance().getCurrentUser().getUid(); // ID des aktuell eingeloggten Users wird als Organisator gespeichert;

        /*String d = datum+" "+uhrzeit;
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date = new Date();
        try{
            date = df.parse(d);
        }catch (ParseException pe){
            pe.printStackTrace();
        }*/

        if (TextUtils.isEmpty(eventname)) {                                                          //Eventname Textfeld ist leer
            Toast.makeText(this, "Bitte Name des Events eintragen", Toast.LENGTH_SHORT).show();     //wenn Feld leer ist, wird Ausführung unterbrochen
            return;
        }

        if (TextUtils.isEmpty(beschreibung)) {                                                              //Beschreibung Textfeld ist leer
            Toast.makeText(this, "Bitte Beschreibung des Events eintragen", Toast.LENGTH_SHORT).show();     //wenn Feld leer ist, wird Ausführung unterbrochen
            return;
        }
        if (TextUtils.isEmpty(ort)) {                                                               //Ort Textfeld ist leer
            Toast.makeText(this, "Bitte Ort des Events eintragen", Toast.LENGTH_SHORT).show();     //wenn Feld leer ist, wird Ausführung unterbrochen
            return;
        }
        if (TextUtils.isEmpty(datum)) {                                                              //Datum Textfeld ist leer
            Toast.makeText(this, "Bitte Datum des Events eintragen", Toast.LENGTH_SHORT).show();     //wenn Feld leer ist, wird Ausführung unterbrochen
            return;
        }
        if (TextUtils.isEmpty(uhrzeit)) {                                                                //Uhrzeit Textfeld ist leer
            Toast.makeText(this, "Bitte Uhrzeit des Events eintragen", Toast.LENGTH_SHORT).show();     //wenn Feld leer ist, wird Ausführung unterbrochen
            return;
        }

        if (!TextUtils.isEmpty(eventname)&&!TextUtils.isEmpty(beschreibung)&&!TextUtils.isEmpty(ort)&&!TextUtils.isEmpty(datum)&&!TextUtils.isEmpty(uhrzeit)){


            String id = databaseEvents.push().getKey().toString();

            // Abfrage, falls ein Event bearbeitet werden soll, statt neu erstellen

            /*try {
                Intent intent2 = getIntent();
                id = intent2.getStringExtra("eventID");
            }
            catch (NullPointerException np) {
            }*/

            if (bearbeitenID!=null){
                id=bearbeitenID;
            }

            /*if(!bearbeitenID.equals("")){
                id=bearbeitenID;
            }*/

            Event event = new Event(id, eventname, beschreibung, ort, datum, uhrzeit, organisatorID);
            databaseEvents.child(id).setValue(event);
            databaseEventteilnehmer.child(id).child(organisatorID).setValue(organisatorID); // Johann: wahrscheinlich Problem, sobald mehrere Teilnehmer --> prüfen, sobald Eventinfos getestet werden kann
            //databaseEventanwesende.child(id).child(organisatorID).setValue(organisatorID);
            databaseTeilnahmen.child(organisatorID).child(id).setValue(id);

            Toast.makeText(this, "Dein Event wurde gespeichert", Toast.LENGTH_LONG).show();
            IDuebergabe=id;
            
            Intent infointent = new Intent(EventErstellen.this, EventInfos.class);
            infointent.putExtra("ID", IDuebergabe);
            startActivity(infointent);

        }else return;
    }


    @Override
    public void onClick(View view) {

        if (view == button8) {
            createevent();

        }
        if (view==textView12){
            showTimePickerDialog(textView12);
        }
        if (view==textView11){
            showDatePickerDialog(textView11);
        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
