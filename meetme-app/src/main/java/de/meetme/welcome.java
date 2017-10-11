package de.meetme;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import static de.meetme.R.id.image;
import static de.meetme.R.id.imageView;


public class welcome extends Activity implements View.OnClickListener {

    private Button buttonLogin2;
    private Button buttonRegis2;
    private Button button14;
    private Animation animfadelangs;
    private ImageView imageView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

      buttonLogin2 = (Button) findViewById(R.id.buttonLogin2);
      buttonRegis2 = (Button) findViewById(R.id.buttonRegis2);
        button14 = (Button) findViewById(R.id.button14);
        imageView = findViewById(R.id.imageView);
        animfadelangs = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_langs);
// load the animation

        buttonLogin2.setVisibility(View.VISIBLE);
        buttonLogin2.setAnimation(animfadelangs);
        buttonRegis2.setVisibility(View.VISIBLE);
        buttonRegis2.setAnimation(animfadelangs);
        button14.setVisibility(View.VISIBLE);
        button14.setAnimation(animfadelangs);
        imageView.setVisibility(View.VISIBLE);
        imageView.setAnimation(animfadelangs);



        button14.setOnClickListener(this);

      buttonLogin2.setOnClickListener(this);
      buttonRegis2.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        // verhindert Back nach Logout
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
