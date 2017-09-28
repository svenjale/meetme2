package de.meetme;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.meetme.R;

public class profilansicht extends Activity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseProfiles;
    private TextView textView33;
    private TextView textView37;
    private TextView textView34;
    private Button button15;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilansicht);

        textView33 = (TextView) findViewById(R.id.textView33);
        textView37 = (TextView) findViewById(R.id.textView37);
        textView34 = (TextView) findViewById(R.id.textView34);
        button15 = (Button) findViewById(R.id.button15);

        button15.setOnClickListener(this);


        databaseProfiles = FirebaseDatabase.getInstance().getReference("profiles");

        DatabaseReference nameWert = databaseProfiles.child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("name");
        nameWert.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                textView33.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        DatabaseReference vornameWert = databaseProfiles.child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("vorname");
        vornameWert.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String vorname = dataSnapshot.getValue(String.class);
                textView37.setText(vorname);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        DatabaseReference kontaktWert = databaseProfiles.child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("kontakt");
        kontaktWert.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String kontakt = dataSnapshot.getValue(String.class);
                textView34.setText(kontakt);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view == button15) {
            Intent switchregisintent = new Intent(profilansicht.this, Profile_Activity.class); //switch zur Registrierung
            startActivity(switchregisintent);

            }
        }
    }
//}
