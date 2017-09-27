package de.meetme;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.meetme.R;

public class profilansicht extends Activity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseProfiles;
    private TextView textView28;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilansicht);

        textView28 = (TextView) findViewById(R.id.textView28);

        databaseProfiles = FirebaseDatabase.getInstance().getReference("profiles");

        DatabaseReference mostafa = databaseProfiles.child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("name");

        mostafa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                textView28.setText(name);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
