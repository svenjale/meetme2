package de.meetme;

import android.app.Activity;
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

public class eventinfos extends Activity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseEventteilnehmer;
    private Button button9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventinfos);
        databaseEventteilnehmer = FirebaseDatabase.getInstance().getReference("eventteilnehmer");
        button9 = (Button) findViewById(R.id.button8);
        button9.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view == button9) {
           /*if (teilnehmen(hierEventIDübergeben)==true){
                Toast.makeText(this, "Erfolgreich angemeldet. See you soon!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Ups. Da hat etwas nicht geklappt.", Toast.LENGTH_LONG).show();
            }*/
        }
    }

    public boolean teilnehmen (String eventID){
        boolean erfolg = false;
        String teilnehmerID = firebaseAuth.getInstance().getCurrentUser().getUid();
        databaseEventteilnehmer.child(eventID).child(teilnehmerID);
        return erfolg;
    }
    //Notiz Johann: https://stackoverflow.com/questions/37031222/firebase-add-new-child-with-specified-name
    // if Abfrage prüft nicht wirklich den Erfolg, Code einfügen!

}
