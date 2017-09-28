package de.meetme;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import de.meetme.R;

public class map extends Activity implements View.OnClickListener{

    private Button button2;
    private Button button7;
    private Button button3;
    private Button button6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        button7 = (Button) findViewById(R.id.button7);
        button3 = (Button) findViewById(R.id.button3);
        button6 = (Button) findViewById(R.id.button6);
        button2 = (Button) findViewById(R.id.button2);



        button7.setOnClickListener(this);
        button3.setOnClickListener(this);
        button6.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == button2) {
            Intent Profil = new Intent(map.this, profilansicht.class);
            startActivity(Profil);
        }
        if (view == button7) {

            Intent Walk = new Intent(map.this, eventlist.class);
            startActivity(Walk);
        }
        if (view == button3) {

            Intent Map = new Intent(map.this, map.class);
            startActivity(Map);
        }
        if (view == button6) {

            Intent Kontakte = new Intent(map.this, kontakte.class);
            startActivity(Kontakte);

        }

    }
}
