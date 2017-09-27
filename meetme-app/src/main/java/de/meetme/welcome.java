package de.meetme;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class welcome extends Activity implements View.OnClickListener {

    private Button buttonLogin2;
    private Button buttonRegis2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

      buttonLogin2 = (Button) findViewById(R.id.buttonLogin2);
      buttonRegis2 = (Button) findViewById(R.id.buttonRegis2);


      buttonLogin2.setOnClickListener(this);
      buttonRegis2.setOnClickListener(this);
    }


    public void onClick(View view) {

        if (view == buttonLogin2) {
            Intent Login2 = new Intent(welcome.this, Login_Activity.class);
            startActivity(Login2);
        }
        if (view == buttonRegis2) {
            Intent Regis2 = new Intent(welcome.this, HelloActivity.class);            //Plus = Hinzufügen Button
            startActivity(Regis2);
        }

    }
}
