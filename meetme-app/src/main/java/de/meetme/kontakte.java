package de.meetme;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import de.meetme.R;

public class kontakte extends Activity implements View.OnClickListener {

    private Button button14;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontakte);

        button14 = (Button) findViewById(R.id.button14);

        button14.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view == button14) {
            Intent gohelp = new Intent(kontakte.this, help.class); //switch zur Registrierung
            startActivity(gohelp);
        }
    }
}
