package de.meetme;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import de.meetme.Help_Pages.helplogin;

import static android.widget.Toast.LENGTH_SHORT;

public class Login_Activity extends Activity implements View.OnClickListener {

    private static final String FIREBASE_URL = "https://smap-dhbw2.firebaseio.com";

    private Firebase databaseProfiles;

    private Button buttonLogIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewRegister;
    private String ErrorMessage;
    private ImageButton button14;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);       //???

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextEmail.setGravity(Gravity.CENTER);
        editTextPassword.setGravity(Gravity.CENTER);
        buttonLogIn = (Button) findViewById(R.id.buttonLogIn);
        textViewRegister = (TextView) findViewById(R.id.textViewRegister);
        button14 = findViewById(R.id.button14);

        buttonLogIn.setOnClickListener(this);
        textViewRegister.setOnClickListener(this);
        button14.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {//Profilseite kann geöffnet werden

            // finish();
            // startActivity(new Intent(getApplicationContext(), Profile_Activity.class));
        }

    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {                                                      //Email Textfeld ist leer
            Toast.makeText(this, "Bitte Email eintragen", LENGTH_SHORT).show();     //wenn Feld leer ist, wird Ausführung unterbrochen
            return;
        }

        if (TextUtils.isEmpty(password)) {                                                  //Passwort Textfeld ist leer
            Toast.makeText(this, "Bitte Passwort eintragen", LENGTH_SHORT).show();    //wenn Feld leer ist, wird Ausführung unterbrochen
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {   //starte Profilseite
                            finish();
                            //Intent ErfolgRegis = new Intent(Login_Activity.this, Profile_Activity.class);
                            //startActivity(ErfolgRegis);
                            Intent loginintent = new Intent(Login_Activity.this, Eventliste_activity.class); //switch zur Eventliste
                            startActivity(loginintent);
                            Toast.makeText(Login_Activity.this, "Erfolgreich eingeloggt", Toast.LENGTH_SHORT).show();

                            profilansicht.aktuelleUserID = firebaseAuth.getInstance().getCurrentUser().getUid();
                            databaseProfiles = new Firebase(FIREBASE_URL).child("profiles").child(profilansicht.aktuelleUserID);

                            ValueEventListener profilListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                                    profilansicht.aktuellerUser = dataSnapshot.getValue(Person.class);
                                }
                                @Override
                                public void onCancelled(FirebaseError firebaseError) {
                                }
                            };
                            databaseProfiles.addValueEventListener(profilListener);

                        } else {
                            ErrorMessage = task.getException().getLocalizedMessage();
                            if (ErrorMessage.equals("There is no user record corresponding to this identifier. The user may have been deleted.")){
                                Toast.makeText(Login_Activity.this, "Dieser Account ist uns nicht bekannt. Bitte wechsle zur Registrierung..", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if (ErrorMessage.equals("The password is invalid or the user does not have a password.")) {
                                    Toast.makeText(Login_Activity.this, "Passwort falsch - überprüfe deine Eingabe.", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (ErrorMessage.equals("We have blocked all requests from this device due to unusual activity. Try again later.")) {
                                        Toast.makeText(Login_Activity.this, "Passwort zu oft falsch eingegeben. Das Gerät wurde vorübergehend gesperrt. Versuche es später erneut.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Login_Activity.this, ErrorMessage, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == buttonLogIn) {
            userLogin();
        }
        if (view == textViewRegister) {
            Intent switchregisintent = new Intent(Login_Activity.this, HelloActivity.class); //switch zur Registrierung
            switchregisintent.putExtra("email", editTextEmail.getText().toString());
            switchregisintent.putExtra("password", editTextPassword.getText().toString());
            startActivity(switchregisintent);
        }
        if(view == button14) {
            Intent LoginHelp = new Intent(Login_Activity.this, helplogin.class);
            startActivity(LoginHelp);
        }
    }

    /**
     * Created by lebenhag on 26.09.2017.
     */

    public static class Event {

        String eventname;
        String beschreibung;
        String ort;
        String datum;
        String uhrzeit;
        String organisatorID;
        String eventID;
       // public int teilnehmerAnzahl=0;

        public Event (){
        }
        public Event(String eventID, String eventname, String beschreibung, String ort, String datum, String uhrzeit, String organisatorID) {
            this.eventname = eventname;
            this.beschreibung = beschreibung;
            this.ort = ort;
            this.datum = datum;
            this.uhrzeit = uhrzeit;
            this.organisatorID = organisatorID;
            this.eventID=eventID;
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

        public String getEventID() {return eventID;}

      //  public int getTeilnehmerAnzahl () {return teilnehmerAnzahl;}
    }
}
