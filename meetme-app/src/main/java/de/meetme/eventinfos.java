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

import de.meetme.R;

public class eventinfos extends Activity implements View.OnClickListener{

    private Button button9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventinfos);

        button9 = (Button) findViewById(R.id.button8);
        button9.setOnClickListener(this);
    }


    public void onClick(View view) {
        if (view == button9) {
           /* if (teilnehmen(hierEventIDeinfügen, firebaseAuth.getInstance().getCurrentUser().getUid())==true){
                Toast.makeText(this, "Erfolgreich angemeldet. See you soon!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Ups. Da hat etwas nicht geklappt.", Toast.LENGTH_LONG).show();
            }*/
        }
    }

    public boolean teilnehmen (String eventID, String userID){
        boolean erfolg = false;

        return erfolg;
    }
}
