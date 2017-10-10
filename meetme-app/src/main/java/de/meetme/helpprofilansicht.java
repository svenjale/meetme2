package de.meetme;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import de.meetme.R;

public class helpprofilansicht extends help {

    private TextView textViewHelpLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpprofilansicht);


        textViewHelpLogin = (TextView) findViewById(R.id.textViewHelpLogin);

        textViewHelpLogin.setMovementMethod(new ScrollingMovementMethod());

    }
}
