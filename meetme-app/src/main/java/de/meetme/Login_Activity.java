package de.meetme;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.widget.Toast.LENGTH_SHORT;

public class Login_Activity extends Activity implements View.OnClickListener {

    private Button buttonLogIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewRegister;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);       //???

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogIn = (Button) findViewById(R.id.buttonLogIn);
        textViewRegister = (TextView) findViewById(R.id.textViewRegister);

        buttonLogIn.setOnClickListener(this);
        textViewRegister.setOnClickListener(this);

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
                            Toast.makeText(Login_Activity.this, "Erfolgreich eingeloggt", Toast.LENGTH_SHORT).show();
                            Intent loginintent = new Intent(Login_Activity.this, Profile_Activity.class); //switch zum Regis
                            startActivity(loginintent);
                        } else {
                            Toast.makeText(Login_Activity.this, "Es scheint etwas schief gelaufen zu sein.", Toast.LENGTH_SHORT).show();


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
            startActivity(switchregisintent);
        }
    }
}