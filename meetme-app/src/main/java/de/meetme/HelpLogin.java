package de.meetme;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class HelpLogin extends Help {

    private TextView textViewHelpLogin;

    private Button button17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helplogin);

        textViewHelpLogin = (TextView) findViewById(R.id.textViewHelpLogin);
        button17 = (Button) findViewById(R.id.button17);
        button17.setOnClickListener(this);


        textViewHelpLogin.setMovementMethod(new ScrollingMovementMethod());
    }
    public void onClick(View view) {
        if (view == button17) {
            Intent back = new Intent(HelpLogin.this, Login.class); //switch zur Registrierung
            startActivity(back);
        }
    }
}
