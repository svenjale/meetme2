package de.meetme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Help extends Activity implements View.OnClickListener {

    private Button button17;

    private TextView textViewHelpLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        button17 = (Button) findViewById(R.id.button17);
        button17.setOnClickListener(this);

        textViewHelpLogin = (TextView) findViewById(R.id.textViewHelpLogin);

        textViewHelpLogin.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onClick(View view) {
        if (view == button17) {
            Intent back = new Intent(Help.this, Willkommen.class); //switch zur Registrierung
            startActivity(back);
        }
    }
}
