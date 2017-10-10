package de.meetme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class kontakte extends Activity implements View.OnClickListener {


    private Button button2;
    private Button button7;
    private Button button3;
    private Button button6;
    private ImageButton button14;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontakte);

        button14 = (ImageButton) findViewById(R.id.button14);

        button7 = (Button) findViewById(R.id.button7);
        button3 = (Button) findViewById(R.id.button3);
        button6 = (Button) findViewById(R.id.button6);
        button2 = (Button) findViewById(R.id.button2);



        button7.setOnClickListener(this);
        button3.setOnClickListener(this);
        button6.setOnClickListener(this);
        button2.setOnClickListener(this);
        button14.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view == button14) {
            Intent gohelp = new Intent(kontakte.this, helpkontakte.class); //switch zur Registrierung
            startActivity(gohelp);


            }
            if (view == button2) {
                Intent Profil = new Intent(kontakte.this, profilansicht.class);
                startActivity(Profil);
            }
            if (view == button7) {

                Intent Walk = new Intent(kontakte.this, Eventliste_activity.class);
                startActivity(Walk);
            }
            if (view == button3) {

                Intent Map = new Intent(kontakte.this, map.class);
                startActivity(Map);
            }
            if (view == button6) {

                Intent Kontakte = new Intent(kontakte.this, kontakte.class);
                startActivity(Kontakte);

            }
        }
    }

