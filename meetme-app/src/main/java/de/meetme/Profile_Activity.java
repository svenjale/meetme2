package de.meetme;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


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

import de.meetme.R;

public class Profile_Activity extends Activity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseProfiles;

    private TextView textViewUserEmail;
    private Button buttonLogOut;
    private Button button2;
    private Button button7;
    private Button button3;
    private Button button6;
    private Button button4;
    private Button button12;

    private EditText editText;             //Name
    private EditText editText2;            //Vorname
    private EditText editText3;            //Kontakt
    private CheckBox checkBox;              // vier Rollen
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        databaseProfiles = FirebaseDatabase.getInstance().getReference("profiles");

        button7 = (Button) findViewById(R.id.button7);
        button3 = (Button) findViewById(R.id.button3);
        button6 = (Button) findViewById(R.id.button6);
        button2 = (Button) findViewById(R.id.button2);
        button4 = (Button) findViewById(R.id.button4);
        button12 = (Button) findViewById(R.id.button12);

        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);

        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) findViewById(R.id.checkBox4);

        button7.setOnClickListener(this);
        button3.setOnClickListener(this);
        button6.setOnClickListener(this);
        button2.setOnClickListener(this);
        button4.setOnClickListener(this);
        button12.setOnClickListener(this);


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


    public void saveprofile(){
        String name = editText.getText().toString().trim();
        String vorname = editText2.getText().toString().trim();

        boolean model = false;
        if(checkBox.isChecked()) model = true;
        boolean fotograf = false;
        if(checkBox2.isChecked()) fotograf = true;
        boolean organisator = false;
        if(checkBox3.isChecked()) organisator = true;
        boolean visagist = false;
        if(checkBox4.isChecked()) visagist = true;

        String kontakt = editText3.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {                                                          //Eventname Textfeld ist leer
            Toast.makeText(this, "Bitte Name eintragen", Toast.LENGTH_SHORT).show();     //wenn Feld leer ist, wird Ausführung unterbrochen
            return;
        }
        if (TextUtils.isEmpty(vorname)) {                                                          //Eventname Textfeld ist leer
            Toast.makeText(this, "Bitte Vorname eintragen", Toast.LENGTH_SHORT).show();     //wenn Feld leer ist, wird Ausführung unterbrochen
            return;
        }
        if (!TextUtils.isEmpty(kontakt)&&!(TextUtils.isEmpty(vorname))){
            Person profil = new Person (name, vorname, model, fotograf, organisator, visagist, kontakt);
            databaseProfiles.child(firebaseAuth.getInstance().getCurrentUser().getUid()).setValue(profil);
            Toast.makeText(this, "Profil wurde aktualisiert", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == button2) {
            Intent Profil = new Intent(Profile_Activity.this, profilansicht.class);
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
        if (view == button4) {
            saveprofile();
            Intent Profil = new Intent(Profile_Activity.this, profilansicht.class);
            startActivity(Profil);
        }

        if (view == button12) {
            Intent helpintent = new Intent (Profile_Activity.this, help.class);
            startActivity(helpintent);
        }
    }
}
