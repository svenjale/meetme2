package de.meetme;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.meetme.R;

public class Profile_Activity extends Activity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    private Button buttonLogOut;
    private Button button2;
    private Button button7;
    private Button button3;
    private Button button6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        button7 = (Button) findViewById(R.id.button7);
        button3 = (Button) findViewById(R.id.button3);
        button6 = (Button) findViewById(R.id.button6);
        button2 = (Button) findViewById(R.id.button2);

        button7.setOnClickListener(this);
        button3.setOnClickListener(this);
        button6.setOnClickListener(this);
        button2.setOnClickListener(this);

       /* firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, Login_Activity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();


        // textViewUserEmail= (TextView) findViewById(R.id.textViewUserEmail);
        // textViewUserEmail ist nicht vorhanden
       // textViewUserEmail.setText("Willkommen"+user.getEmail());


*/
    }

    @Override
    public void onClick(View view) {
        if (view == button2) {
            Intent Profil = new Intent(Profile_Activity.this, Profile_Activity.class);
            startActivity(Profil);
        }
        if (view == button7) {

            Intent Walk = new Intent(Profile_Activity.this, eventlist.class);
            startActivity(Walk);
        }
        if (view == button3) {

            Intent Map = new Intent(Profile_Activity.this, map.class);
            startActivity(Map);
        }
        if (view == button6) {

            Intent Kontakte = new Intent(Profile_Activity.this, kontakte.class);
            startActivity(Kontakte);
        }
    }
}
