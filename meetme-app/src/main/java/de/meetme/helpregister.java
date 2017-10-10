package de.meetme;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;


public class helpregister extends help {

    private TextView textViewHelpLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpregister);

        textViewHelpLogin = (TextView) findViewById(R.id.textViewHelpLogin);

        textViewHelpLogin.setMovementMethod(new ScrollingMovementMethod());
    }
}
