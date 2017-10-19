package de.meetme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;


public class profilansicht extends Activity implements View.OnClickListener {

    private static final String FIREBASE_URL = "https://smap-dhbw2.firebaseio.com";

    private FirebaseAuth firebaseAuth;
    private Firebase databaseProfiles;
    private TextView textView33;
    private TextView textView37;
    private TextView textView34;
    private TextView textView39;
    private Button button15;
    private Button button2;
    private Button button7;
    private Button button3;
    private Button button6;
    private Button button4;
    private ImageButton button22;
    private Button button20;
    private ImageButton button14;
    private Animation animfadein;


    public static Person aktuellerUser;
    public static String aktuelleUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilansicht);

        aktuelleUserID = firebaseAuth.getInstance().getCurrentUser().getUid();

        textView33 = (TextView) findViewById(R.id.textView33);
        textView37 = (TextView) findViewById(R.id.textView37);
        textView34 = (TextView) findViewById(R.id.textView34);
        textView39 = (TextView) findViewById(R.id.textView39);

        button15 = (Button) findViewById(R.id.button16);
        button7 = (Button) findViewById(R.id.button7);
        button3 = (Button) findViewById(R.id.button3);
        button6 = (Button) findViewById(R.id.button6);
        button2 = (Button) findViewById(R.id.button2);

        button22 = (ImageButton) findViewById(R.id.button22);
        button20 = (Button) findViewById(R.id.button20);

        button14 = findViewById(R.id.button14);
        button14.setOnClickListener(this);

        animfadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
// load the animation

        button2.setVisibility(View.VISIBLE);
        button2.setAnimation(animfadein);
        button3.setVisibility(View.VISIBLE);
        button3.setAnimation(animfadein);
        button6.setVisibility(View.VISIBLE);
        button6.setAnimation(animfadein);
        button7.setVisibility(View.VISIBLE);
        button7.setAnimation(animfadein);
        
        button15.setOnClickListener(this);
        button7.setOnClickListener(this);
        button3.setOnClickListener(this);
        button6.setOnClickListener(this);
        button2.setOnClickListener(this);

        button22.setOnClickListener(this);
        button20.setOnClickListener(this);


        databaseProfiles = new Firebase(FIREBASE_URL).child("profiles").child(aktuelleUserID);
        //Test: Toast.makeText(profilansicht.this, databaseProfiles.getKey(), Toast.LENGTH_SHORT).show();
    }

    @Override
        public void onStart (){
        super.onStart();
        ValueEventListener profilListener = new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                aktuellerUser = dataSnapshot.getValue(Person.class);//Toast.makeText(profilansicht.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                //textView33.setText(dataSnapshot.child("name").getValue().toString());
                //textView37.setText(dataSnapshot.child("vorname").getValue().toString());
                //textView39.setText(dataSnapshot.child("rolle").getValue().toString().trim());
                //textView34.setText(dataSnapshot.child("kontakt").getValue().toString());
                ((TextView) findViewById(R.id.textView33)).setText(aktuellerUser.getName());
                ((TextView) findViewById(R.id.textView37)).setText(aktuellerUser.getVorname());
                ((TextView) findViewById(R.id.textView39)).setText(aktuellerUser.getRolle().trim());
                ((TextView) findViewById(R.id.textView34)).setText(aktuellerUser.getKontakt());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
        databaseProfiles.addValueEventListener(profilListener);
    }

    @Override
    public void onClick(View view) {

        if (view == button14) {
            Intent gohelp = new Intent(profilansicht.this, helpmain.class);
            startActivity(gohelp);
        }

        if (view == button15) {
            Intent switchregisintent = new Intent(profilansicht.this, Profile_Activity.class); //switch zur Registrierung
            startActivity(switchregisintent);
        }
        if (view == button2) {
            Intent Profil = new Intent(profilansicht.this, profilansicht.class);
            startActivity(Profil);
        }
        if (view == button7) {
            Intent Walk = new Intent(profilansicht.this, Eventliste_activity.class);
            startActivity(Walk);
        }
        if (view == button3) {

            Intent Map = new Intent(profilansicht.this, MapsActivity.class);
            startActivity(Map);
        }
        if (view == button6) {
            Intent Kontakte = new Intent(profilansicht.this, kontakte.class);
            startActivity(Kontakte);
        }

        if (view == button22) {
            Intent logout = new Intent(profilansicht.this, welcome.class);
            FirebaseAuth.getInstance().signOut();
            aktuellerUser=new Person("","","","","");
            aktuelleUserID="";
            startActivity(logout);
        }
        if (view == button20) {
            try {
                Intent teilnahmen = new Intent(profilansicht.this, teilnahmenlist.class);
                teilnahmen.putExtra("Name", aktuellerUser.getVorname()+" "+aktuellerUser.getName());
                teilnahmen.putExtra("ID", aktuelleUserID);
                startActivity(teilnahmen);
            }catch (NullPointerException exception) {
                Toast.makeText(profilansicht.this, "Du hast bisher an keinen Photowalks teilgenommen.", Toast.LENGTH_SHORT).show();
            }

        }
    }
}