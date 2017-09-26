package de.meetme;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.meetme.R;

public class Profile_Activity extends Activity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    private Button buttonLogOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

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

    }
}
