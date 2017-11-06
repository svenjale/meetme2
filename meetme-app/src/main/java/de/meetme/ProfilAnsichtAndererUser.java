package de.meetme;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfilAnsichtAndererUser extends Activity implements View.OnClickListener {

    private static final String FIREBASE_URL = "https://smap-dhbw2.firebaseio.com";
    private String uebergebeneID;
    String whatsappKontakt = "";
    String aktuellerName = "";

    private FirebaseAuth firebaseAuth;
    private Firebase databaseProfiles;
    private Firebase databaseKontakte;
    private Firebase databaseProfilbilder;
    private TextView textView33;
    private TextView textView34;
    private TextView textView39;
    private TextView textView21;
    private Button button15;
    private Button button16;
    private Button button2;
    private Button button7;
    private Button button3;
    private Button button6;
    private Button button4;
    private ImageButton button18;
    private Button button19;
    private ImageButton button14;
    private ImageButton imageButton3;
    private Animation animfadein;
    String downloadUrl;
    FirebaseStorage storage;
    ImageView profilBild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilansicht_andereruser);

        Intent intent = getIntent();
        uebergebeneID = intent.getStringExtra("ID");

        imageButton3 = findViewById(R.id.imageButton3);
        textView33 = (TextView) findViewById(R.id.textView33);
        textView34 = (TextView) findViewById(R.id.textView34);
        textView39 = (TextView) findViewById(R.id.textView39);
        textView21 = (TextView) findViewById(R.id.textView21);
        profilBild = findViewById(R.id.profilBild);



        button14 = findViewById(R.id.button14);


        button15 = (Button) findViewById(R.id.button15);
        button16 = (Button) findViewById(R.id.button16);
        button7 = (Button) findViewById(R.id.button7);
        button3 = (Button) findViewById(R.id.button3);
        button6 = (Button) findViewById(R.id.button6);
        button2 = (Button) findViewById(R.id.button2);
        button18 = (ImageButton) findViewById(R.id.button18);
        button19 = (Button) findViewById(R.id.button19);
        animfadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
// load the animation
        button2.setVisibility(View.VISIBLE);
        button2.setAnimation(animfadein);
        button3.setVisibility(View.VISIBLE);
        button3.setAnimation(animfadein);
        button6.setVisibility(View.VISIBLE);
        button6.setAnimation(animfadein);
        button7.setVisibility(View.VISIBLE);
        button7.setAnimation(animfadein);

        imageButton3.setOnClickListener(this);
        button15.setOnClickListener(this);
        button16.setOnClickListener(this);
        button7.setOnClickListener(this);
        button3.setOnClickListener(this);
        button6.setOnClickListener(this);
        button2.setOnClickListener(this);
        button14.setOnClickListener(this);

        textView34.setOnClickListener(this);
        button18.setOnClickListener(this);
        button19.setOnClickListener(this);


        databaseProfiles = new Firebase(FIREBASE_URL).child("profiles").child(uebergebeneID);
        databaseKontakte = new Firebase(FIREBASE_URL).child("KontakteListe").child(firebaseAuth.getInstance().getCurrentUser().getUid());
        databaseProfilbilder = new Firebase(FIREBASE_URL).child("profilbilder");

        //Test: Toast.makeText(ProfilAnsichtEigenesProfil.this, databaseProfiles.getKey(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        ValueEventListener profilListener = new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                //button16.setVisibility(View.GONE);
                button16.setAlpha(.5f);
                button16.setClickable(false);
                Person ansicht = dataSnapshot.getValue(Person.class);//Toast.makeText(ProfilAnsichtEigenesProfil.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                //textView33.setText(dataSnapshot.child("name").getValue().toString());
                //textView37.setText(dataSnapshot.child("vorname").getValue().toString());
                //textView39.setText(dataSnapshot.child("rolle").getValue().toString().trim());
                //textView34.setText(dataSnapshot.child("kontakt").getValue().toString());
                ((TextView) findViewById(R.id.textView33)).setText(ansicht.getVorname()+" "+ansicht.getName());
                ((TextView) findViewById(R.id.textView39)).setText(ansicht.getRolle().trim());
                ((TextView) findViewById(R.id.textView34)).setText(ansicht.getKontakt());
                ((TextView) findViewById(R.id.textView21)).setText("Profil von " + ansicht.getName());
                whatsappKontakt = ansicht.getKontakt();
                aktuellerName = ansicht.getVorname() + " " + ansicht.getName();

                databaseKontakte.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            String kontakt = snapshot.getValue(String.class);
                            if (kontakt.equals(uebergebeneID)) {
                                //button16.setVisibility(View.VISIBLE);
                                //button15.setVisibility(View.GONE);
                                button16.setAlpha(1.0f);
                                button16.setClickable(true);
                                button15.setAlpha(.5f);
                                button15.setClickable(false);
                                return;
                            }else{
                                //button16.setVisibility(View.GONE);
                                //button15.setVisibility(View.VISIBLE);
                                button15.setAlpha(1.0f);
                                button15.setClickable(true);
                                button16.setAlpha(.5f);
                                button16.setClickable(false);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
        databaseProfiles.addValueEventListener(profilListener);

        profilbildAnzeigen();
    }

    @Override
    public void onClick(View view) {
        if (view == button2) {
            Intent Profil = new Intent(ProfilAnsichtAndererUser.this, ProfilAnsichtEigenesProfil.class);
            startActivity(Profil);
        }
        if (view == button7) {
            Intent Walk = new Intent(ProfilAnsichtAndererUser.this, EventListe.class);
            startActivity(Walk);
        }
        if (view == button3) {

            Intent Map = new Intent(ProfilAnsichtAndererUser.this, de.meetme.Map.class);
            startActivity(Map);
        }
        if (view == button6) {
            Intent Kontakte = new Intent(ProfilAnsichtAndererUser.this, KontakteListe.class);
            startActivity(Kontakte);
        }
        if (view == button14) {
            Intent gohelp = new Intent(ProfilAnsichtAndererUser.this, HelpMain.class);
            startActivity(gohelp);
        }

        if (view == button18) {
            mitWhatsAppTeilen(button18);
        }
        if (view == button19) {
            try {
                Intent teilnahmen = new Intent(ProfilAnsichtAndererUser.this, EventteilnahmenListe.class);
                teilnahmen.putExtra("Name", aktuellerName);
                teilnahmen.putExtra("ID", uebergebeneID);
                startActivity(teilnahmen);
            } catch (NullPointerException exception) {
                Toast.makeText(ProfilAnsichtAndererUser.this, aktuellerName + " hat bisher an keinen Photowalks teilgenommen.", Toast.LENGTH_SHORT).show();
            }

        }

        if (view == imageButton3) {
            if (whatsappKontakt.isEmpty()) {
                Toast.makeText(ProfilAnsichtAndererUser.this, "Dieser Nutzer hat keine Telefonnummer hinterlegt und kann nicht kontaktiert werden.",
                        Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + whatsappKontakt));
                startActivity(intent);
                // hier evtl TELEFONANRUF implementieren
            }
        }
        if (view == button15) {
            databaseKontakte.child(uebergebeneID).setValue(uebergebeneID);
            Toast.makeText(ProfilAnsichtAndererUser.this, "" + aktuellerName + " wurde zu deinen Kontakten hinzugefügt.", Toast.LENGTH_SHORT).show();
            //button16.setVisibility(View.VISIBLE);
            //button15.setVisibility(View.GONE);
            button16.setAlpha(1.0f);
            button16.setClickable(true);
            button15.setAlpha(.5f);
            button15.setClickable(false);
        }
        if (view == button16) {
            databaseKontakte.child(uebergebeneID).removeValue(); //löschen
            Toast.makeText(ProfilAnsichtAndererUser.this, "" + aktuellerName + " wurde aus deinen Kontakten entfernt.", Toast.LENGTH_SHORT).show();
            //button16.setVisibility(View.GONE);
            //button15.setVisibility(View.VISIBLE);
            button15.setAlpha(1.0f);
            button15.setClickable(true);
            button16.setAlpha(.5f);
            button16.setClickable(false);
        }
    }

    public void mitWhatsAppTeilen(View view) {
        if (whatsappKontakt.isEmpty()) {
            Toast.makeText(ProfilAnsichtAndererUser.this, "Dieser Nutzer hat keine Telefonnummer hinterlegt und kann nichtkontaktiert werden.",
                    Toast.LENGTH_LONG).show();
        } else {
            PackageManager pm = getPackageManager();
            try {

                Uri uri = Uri.parse("smsto:" + whatsappKontakt);
                Intent waIntent = new Intent(Intent.ACTION_SENDTO, uri); // hier sendto eingefügt
                //waIntent.setType("text/plain");
                String text = "Gib hier deine Nachricht an" + aktuellerName + " ein.";
                PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                //Check if package exists or not. If not then code
                //in catch block will be called
                waIntent.setPackage("com.whatsapp");
                waIntent.putExtra("sms_body", text);
                startActivity(Intent.createChooser(waIntent, uri.toString()));

            } catch (PackageManager.NameNotFoundException e) {
                Toast.makeText(this, "WhatsApp nicht installiert", Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse("smsto:" + whatsappKontakt);
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", "Gib hier deine Nachricht an " + aktuellerName + " ein.");
                startActivity(it);
            }

        }
    }

    public void profilbildAnzeigen () {

        databaseProfilbilder.child(uebergebeneID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                downloadUrl = dataSnapshot.getValue(String.class);
                try {
                    if (downloadUrl.isEmpty() || downloadUrl.equals("")) {
                        downloadUrl = "https://firebasestorage.googleapis.com/v0/b/smap-dhbw2.appspot.com/o/images%2F13547334191038217.png.13058c9590e23a9de3feeaa55d725724.png?alt=media&token=9b3886c8-deac-4b25-8f26-838a98e9dfb4";
                    }
                }catch (NullPointerException np){
                    downloadUrl = "https://firebasestorage.googleapis.com/v0/b/smap-dhbw2.appspot.com/o/images%2F13547334191038217.png.13058c9590e23a9de3feeaa55d725724.png?alt=media&token=9b3886c8-deac-4b25-8f26-838a98e9dfb4";
                }
                StorageReference storageReference = storage.getInstance().getReferenceFromUrl(downloadUrl);
                Glide.with(ProfilAnsichtAndererUser.this)
                        .using(new FirebaseImageLoader())
                        .load(storageReference)
                        .into(profilBild);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}

// getUserID anderer User

//download picture von anderem user
