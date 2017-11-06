package de.meetme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;


public class ProfilAnsichtEigenesProfil extends Activity implements View.OnClickListener {

    private static final String FIREBASE_URL = "https://smap-dhbw2.firebaseio.com";

    private FirebaseAuth firebaseAuth;
    private Firebase databaseProfiles;
    private Firebase databaseProfilbilder;

    private TextView textView33;
    private TextView textView37;
    private TextView textView34;
    private TextView textView39;
    private Button button15;
    private Button button2;
    private Button button7;
    private Button button3;
    private Button button6;
    private Button button4;
    private ImageButton button22;
    private Button button20;
    private ImageButton button14;
    private Animation animfadein;

    private ImageView EigenesProfilBild;
    private ImageButton imageZoom;
    private StorageReference storageReference;
    private Uri downloadUrl;
    private Bitmap Profilbild;
    private Bitmap bitmap;
    FirebaseStorage storage;

    public static Person aktuellerUser;
    public static String aktuelleUserID;

    public ProfilAnsichtEigenesProfil() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilansicht_eigenesprofil);

        //try {
          //  Intent intent = getIntent();
          //  aktuelleUserID=intent.getStringExtra("ID");
       // }catch (NullPointerException npp)        {
            aktuelleUserID = firebaseAuth.getInstance().getCurrentUser().getUid();
      //  }

        textView33 = (TextView) findViewById(R.id.textView33);
        textView37 = (TextView) findViewById(R.id.textView37);
        textView34 = (TextView) findViewById(R.id.textView34);
        textView39 = (TextView) findViewById(R.id.textView39);

        button15 = (Button) findViewById(R.id.button16);
        button7 = (Button) findViewById(R.id.button7);
        button3 = (Button) findViewById(R.id.button3);
        button6 = (Button) findViewById(R.id.button6);
        button2 = (Button) findViewById(R.id.button2);

        button22 = (ImageButton) findViewById(R.id.button22);
        button20 = (Button) findViewById(R.id.button20);

        button14 = findViewById(R.id.button14);
        button14.setOnClickListener(this);

        EigenesProfilBild = (ImageView) findViewById(R.id.EigenesProfilBild);
        imageZoom = (ImageButton)findViewById(R.id.imageZoom);

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
        
        button15.setOnClickListener(this);
        button7.setOnClickListener(this);
        button3.setOnClickListener(this);
        button6.setOnClickListener(this);
        button2.setOnClickListener(this);

        button22.setOnClickListener(this);
        button20.setOnClickListener(this);

        imageZoom.setOnClickListener(this);


        databaseProfiles = new Firebase(FIREBASE_URL).child("profiles").child(aktuelleUserID);
        databaseProfilbilder = new Firebase(FIREBASE_URL).child("profilbilder");

        //Test: Toast.makeText(ProfilAnsichtEigenesProfil.this, databaseProfiles.getKey(), Toast.LENGTH_SHORT).show();

    }

    @Override
        public void onStart (){
        super.onStart();
        ValueEventListener profilListener = new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                aktuellerUser = dataSnapshot.getValue(Person.class);//Toast.makeText(ProfilAnsichtEigenesProfil.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                //textView33.setText(dataSnapshot.child("name").getValue().toString());
                //textView37.setText(dataSnapshot.child("vorname").getValue().toString());
                //textView39.setText(dataSnapshot.child("rolle").getValue().toString().trim());
                //textView34.setText(dataSnapshot.child("kontakt").getValue().toString());
                ((TextView) findViewById(R.id.textView33)).setText(aktuellerUser.getName());
                ((TextView) findViewById(R.id.textView37)).setText(aktuellerUser.getVorname());
                ((TextView) findViewById(R.id.textView39)).setText(aktuellerUser.getRolle().trim());
                ((TextView) findViewById(R.id.textView34)).setText(aktuellerUser.getKontakt());

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
        databaseProfiles.addValueEventListener(profilListener);
        //loadImage();
        //displayEigenesProfilbild();
    }

    @Override
    public void onClick(View view) {

        if (view == button14) {
            Intent gohelp = new Intent(ProfilAnsichtEigenesProfil.this, HelpMain.class);
            startActivity(gohelp);
        }

        if (view == button15) {
            Intent switchregisintent = new Intent(ProfilAnsichtEigenesProfil.this, ProfilAktualisieren.class); //switch zur Registrierung
            startActivity(switchregisintent);
        }
        if (view == button2) {
            Intent Profil = new Intent(ProfilAnsichtEigenesProfil.this, ProfilAnsichtEigenesProfil.class);
            startActivity(Profil);
        }
        if (view == button7) {
            Intent Walk = new Intent(ProfilAnsichtEigenesProfil.this, EventListe.class);
            startActivity(Walk);
        }
        if (view == button3) {

            Intent Map = new Intent(ProfilAnsichtEigenesProfil.this, de.meetme.Map.class);
            startActivity(Map);
        }
        if (view == button6) {
            Intent Kontakte = new Intent(ProfilAnsichtEigenesProfil.this, KontakteListe.class);
            startActivity(Kontakte);
        }

        if (view == button22) {
            Intent logout = new Intent(ProfilAnsichtEigenesProfil.this, Willkommen.class);
            FirebaseAuth.getInstance().signOut();
            //LoginManager.getInstance().logOut();
            aktuellerUser=new Person("","","","","");
            aktuelleUserID="";
            startActivity(logout);
        }
        if (view == button20) {
            try {
                Intent teilnahmen = new Intent(ProfilAnsichtEigenesProfil.this, EventteilnahmenListe.class);
                teilnahmen.putExtra("Name", aktuellerUser.getVorname()+" "+aktuellerUser.getName());
                teilnahmen.putExtra("ID", aktuelleUserID);
                startActivity(teilnahmen);
            }catch (NullPointerException exception) {
                Toast.makeText(ProfilAnsichtEigenesProfil.this, "Du hast bisher an keinen Photowalks teilgenommen.", Toast.LENGTH_SHORT).show();
            }

        }

        if (view == imageZoom) {

            PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);

           // photoView.
            Picasso.with(ProfilAnsichtEigenesProfil.this).load(String.valueOf(bitmap)).into(EigenesProfilBild);

        }
    }

    public void displayEigenesProfilbild () {

        aktuelleUserID = firebaseAuth.getInstance().getCurrentUser().getUid();

        databaseProfilbilder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                downloadUrl = dataSnapshot.getValue(Profilbild.class).downloadUrl;
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(String.valueOf(downloadUrl)).child("android.jpg");

        try {
            final File localFile = File.createTempFile("images", "jpg");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    EigenesProfilBild.setImageBitmap(bitmap);
                    bitmap = Profilbild;

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                    Toast.makeText(ProfilAnsichtEigenesProfil.this, "Profilbild kann nicht angezeigt werden", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadImage(){ /*

        ValueEventListener bildListener = new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String url = dataSnapshot.getValue(String.class);



// Create a reference from an HTTPS URL
// Note that in the URL, characters are URL escaped!
                storage = FirebaseStorage.getInstance();
                storageReference = storage.getReferenceFromUrl(url);

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(ProfilAnsichtEigenesProfil.this, "Fehler beim Laden des Bildes.", Toast.LENGTH_SHORT).show();
            }

        };
        databaseProfilbilder.addValueEventListener(bildListener); */





        Glide.with(this)
          .using(new FirebaseImageLoader())
              .load(storageReference)
                .into(EigenesProfilBild);
    }
}