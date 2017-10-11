package de.meetme;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import android.app.Activity;
import android.widget.Toast;

import de.meetme.R;

public class profilansichtAndererUser extends Activity implements View.OnClickListener {

    private static final String FIREBASE_URL = "https://smap-dhbw2.firebaseio.com";
    private String uebergebeneID;
    String whatsappKontakt = "";
    String aktuellerName="";

    private FirebaseAuth firebaseAuth;
    private Firebase databaseProfiles;
    private Firebase databaseKontakte;
    private TextView textView33;
    private TextView textView37;
    private TextView textView34;
    private TextView textView39;
    private TextView textView21;
    private ImageButton button15;
    private Button button2;
    private Button button7;
    private Button button3;
    private Button button6;
    private Button button4;
    private ImageButton button18;
    private Button button19;
    private ImageButton button14;
    private ImageButton imageButton3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilansicht_anderer_user);

        Intent intent = getIntent();
        uebergebeneID = intent.getStringExtra("ID");

        imageButton3 = findViewById(R.id.imageButton3);
        textView33 = (TextView) findViewById(R.id.textView33);
        textView37 = (TextView) findViewById(R.id.textView37);
        textView34 = (TextView) findViewById(R.id.textView34);
        textView39 = (TextView) findViewById(R.id.textView39);
        textView21 = (TextView) findViewById(R.id.textView21);


        button14 = findViewById(R.id.button14);



        button15 = (ImageButton) findViewById(R.id.button15);
        button7 = (Button) findViewById(R.id.button7);
        button3 = (Button) findViewById(R.id.button3);
        button6 = (Button) findViewById(R.id.button6);
        button2 = (Button) findViewById(R.id.button2);

        button18 = (ImageButton) findViewById(R.id.button18);
        button19 = (Button) findViewById(R.id.button19);

        imageButton3.setOnClickListener(this);
        button15.setOnClickListener(this);
        button7.setOnClickListener(this);
        button3.setOnClickListener(this);
        button6.setOnClickListener(this);
        button2.setOnClickListener(this);
        button14.setOnClickListener(this);

        textView34.setOnClickListener(this);
        button18.setOnClickListener(this);
        button19.setOnClickListener(this);


        databaseProfiles = new Firebase(FIREBASE_URL).child("profiles").child(uebergebeneID);
        databaseKontakte = new Firebase(FIREBASE_URL).child("kontakte").child(firebaseAuth.getInstance().getCurrentUser().getUid());

        //Test: Toast.makeText(profilansicht.this, databaseProfiles.getKey(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        ValueEventListener profilListener = new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Person ansicht = dataSnapshot.getValue(Person.class);//Toast.makeText(profilansicht.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                //textView33.setText(dataSnapshot.child("name").getValue().toString());
                //textView37.setText(dataSnapshot.child("vorname").getValue().toString());
                //textView39.setText(dataSnapshot.child("rolle").getValue().toString().trim());
                //textView34.setText(dataSnapshot.child("kontakt").getValue().toString());
                ((TextView) findViewById(R.id.textView33)).setText(ansicht.getName());
                ((TextView) findViewById(R.id.textView37)).setText(ansicht.getVorname());
                ((TextView) findViewById(R.id.textView39)).setText(ansicht.getRolle().trim());
                ((TextView) findViewById(R.id.textView34)).setText(ansicht.getKontakt());
                ((TextView) findViewById(R.id.textView21)).setText("Profil von "+ansicht.getVorname());
                whatsappKontakt = ansicht.getKontakt();
                aktuellerName=ansicht.getVorname()+" "+ansicht.getName();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
        databaseProfiles.addValueEventListener(profilListener);
    }

    @Override
    public void onClick(View view)  {
        if (view == button2) {
            Intent Profil = new Intent(profilansichtAndererUser.this, profilansicht.class);
            startActivity(Profil);
        }
        if (view == button7) {
            Intent Walk = new Intent(profilansichtAndererUser.this, Eventliste_activity.class);
            startActivity(Walk);
        }
        if (view == button3) {

            Intent Map = new Intent(profilansichtAndererUser.this, MapsActivity.class);
            startActivity(Map);
        }
        if (view == button6) {
            Intent Kontakte = new Intent(profilansichtAndererUser.this, kontakte.class);
            startActivity(Kontakte);
        }
        if (view == button14) {
            Intent gohelp = new Intent(profilansichtAndererUser.this, helpevent.class);
            startActivity(gohelp);
        }

        if (view == button18) {
            mitWhatsAppTeilen(button18);
        }
        if (view == button19) {
            try {
                Intent teilnahmen = new Intent(profilansichtAndererUser.this, teilnahmenlist.class);
                teilnahmen.putExtra("Name", aktuellerName);
                teilnahmen.putExtra("ID", uebergebeneID);
                startActivity(teilnahmen);
            }catch (NullPointerException exception) {
                Toast.makeText(profilansichtAndererUser.this, aktuellerName+" hat bisher an keinen Photowalks teilgenommen.", Toast.LENGTH_SHORT).show();
            }

        }

        if (view == imageButton3) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+whatsappKontakt));
            startActivity(intent);
            // hier evtl TELEFONANRUF implementieren
        }
        if (view==button15){
            databaseKontakte.child(uebergebeneID).setValue(uebergebeneID);
            Toast.makeText(profilansichtAndererUser.this, ""+aktuellerName+" wurde zu deinen Kontakten hinzugefügt.", Toast.LENGTH_SHORT).show();

        }
    }

    public void mitWhatsAppTeilen(View view) {

        PackageManager pm = getPackageManager();
        try {

            Uri uri = Uri.parse("smsto:" + whatsappKontakt);
            Intent waIntent = new Intent(Intent.ACTION_SENDTO, uri); // hier sendto eingefügt
            //waIntent.setType("text/plain");
            String text = "Gib hier deine Nachricht an"+aktuellerName+" ein.";
            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");
            waIntent.putExtra("sms_body", text);
            startActivity(Intent.createChooser(waIntent, uri.toString()));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp nicht installiert", Toast.LENGTH_SHORT).show();
            Uri uri = Uri.parse("smsto:"+whatsappKontakt);
            Intent it = new Intent(Intent.ACTION_SENDTO, uri);
            it.putExtra("sms_body", "Gib hier deine Nachricht an "+aktuellerName+" ein.");
            startActivity(it);
        }

    }
}
