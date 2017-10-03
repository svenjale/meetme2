package de.meetme;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;



public class welcome extends Activity implements View.OnClickListener {

    private Button buttonLogin2;
    private Button buttonRegis2;
    private Button button14;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

      buttonLogin2 = (Button) findViewById(R.id.buttonLogin2);
      buttonRegis2 = (Button) findViewById(R.id.buttonRegis2);
        button14 = (Button) findViewById(R.id.button14);

        button14.setOnClickListener(this);

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
        if (view == button14) {
            Intent gohelp2 = new Intent(welcome.this, help.class); //switch zur Registrierung
            startActivity(gohelp2);
        }
    }
}
