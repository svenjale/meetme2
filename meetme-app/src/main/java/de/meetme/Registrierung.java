package de.meetme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class Registrierung extends Activity implements View.OnClickListener {

    private static final String TAG = "Registrierung";
    private static final String HOSTNAME = "<here your IP or hostname>";
    private static final int PORT = 8087;
    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private ImageButton button14;
    private Animation animfadelangs;
    private ImageView imageView2;
    private String email = "";
    private String password = "";

    private static final String FIREBASE_URL = "https://smap-dhbw2.firebaseio.com";

    private FirebaseAuth firebaseAuth;
    private Firebase databaseProfiles;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrieren);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseProfiles = new Firebase(FIREBASE_URL).child("profiles");


        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        imageView2 =findViewById(R.id.imageView2);
        animfadelangs = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_langs);
// load the animation

        imageView2.setVisibility(View.VISIBLE);
        imageView2.setAnimation(animfadelangs);

        editTextEmail.setGravity(Gravity.CENTER);
        editTextPassword.setGravity(Gravity.CENTER);
        button14 = findViewById(R.id.button14);
        button14.setOnClickListener(this);


        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

        try {
            Intent intentregis = getIntent();
            email = intentregis.getStringExtra("email").toString();
            password = intentregis.getStringExtra("password").toString();
            editTextEmail.setText(email);
            editTextPassword.setText(password);
        } catch (NullPointerException exception) {

        }


        Log.e(TAG, "run client");

        RestClient httpclient = new RestClient("http://" + HOSTNAME + ":" + PORT);
        try {
            Person p = httpclient.getApiService().getPerson(1);
            Log.e(TAG, p.toString());

        } catch (Exception e) {
            Log.e(TAG, "Error: " + e);
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        TextView textView = (TextView) findViewById(R.id.textViewSignin);

    }


    private void registerUser() {
        email = editTextEmail.getText().toString().trim();        //Email wird aus Editfeld geholt
        password = editTextPassword.getText().toString().trim();  //Passwort wird aus Editfeld geholt

        if (TextUtils.isEmpty(email)) {                                    //Email Textfeld ist leer
            Toast.makeText(this, "Bitte Email eintragen", Toast.LENGTH_SHORT).show();     //wenn Feld leer ist, wird Ausführung unterbrochen
            return;
        }

        if (TextUtils.isEmpty(password)) {                                  //Passwort Textfeld ist leer
            Toast.makeText(this, "Bitte Passwort eintragen", Toast.LENGTH_SHORT).show();    //wenn Feld leer ist, wird Ausführung unterbrochen
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(Registrierung.this, "Bitte gebe ein Passwort mit mindestens 6 Zeichen ein", Toast.LENGTH_SHORT).show();
            return;
        }


        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Person profil = new Person("", "", "", "", firebaseAuth.getInstance().getCurrentUser().getUid());
                        databaseProfiles.child(firebaseAuth.getInstance().getCurrentUser().getUid()).setValue(profil);
                        ProfilAnsichtEigenesProfil.aktuellerUser = profil;
                        if (task.isSuccessful()) {
                            Toast.makeText(Registrierung.this, "Erfolgreich registriert", Toast.LENGTH_SHORT).show();
                            //Benutzer ist erfolgreich mit Email und Passwort registriert und eingeloggt, öffne Profil Aktivität
                            if (firebaseAuth.getCurrentUser() != null) { //Profilseite kann geöffnet werden
                                finish();
                                Toast.makeText(Registrierung.this, "Erfolgreich eingeloggt", Toast.LENGTH_SHORT).show();
                                Intent regisintent = new Intent(Registrierung.this, ProfilAktualisieren.class);
                                startActivity(regisintent);

                            } else {
                                Toast.makeText(Registrierung.this, "Benutzer konnte nicht registriert werden", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

   /* private void login (){
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {   //starte Profilseite
                            Toast.makeText(Registrierung.this, "Erfolgreich eingeloggt", Toast.LENGTH_SHORT).show();
                            Intent regisintent = new Intent(Registrierung.this, ProfilAktualisieren.class);
                            startActivity(regisintent);

                        } else {
                            Toast.makeText(Registrierung.this, "Es scheint etwas schief gelaufen zu sein.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    } */

    @Override
    public void onClick(View view) {
        if (view == buttonRegister) {
            registerUser();
        }
        if (view == button14) {
            Intent gohelp = new Intent(Registrierung.this, HelpRegistrierung.class); //switch zur Registrierung
            startActivity(gohelp);

        }
        if (view == textViewSignin) {
            Intent switchregisintent = new Intent(Registrierung.this, Login.class); //switch zur Registrierung
            switchregisintent.putExtra("email", editTextEmail.getText().toString());
            switchregisintent.putExtra("password", editTextPassword.getText().toString());
            startActivity(switchregisintent);
        }


    }
}



