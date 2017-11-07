package de.meetme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
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
import com.facebook.login.LoginManager;
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
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


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
    private String downloadUrl ="";
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
        //imageZoom = (ImageButton)findViewById(R.id.imageZoom);

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

//        imageZoom.setOnClickListener(this);


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
                aktuellerUser = dataSnapshot.getValue(Person.class);

                ((TextView) findViewById(R.id.textView39)).setText(aktuellerUser.getVorname()+" "+aktuellerUser.getName());
               // ((TextView) findViewById(R.id.textView37)).setText(aktuellerUser.getVorname());
                ((TextView) findViewById(R.id.textView37)).setText(aktuellerUser.getRolle().trim());
                ((TextView) findViewById(R.id.textView34)).setText(aktuellerUser.getKontakt());

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
        databaseProfiles.addValueEventListener(profilListener);

        profilbildAnzeigen();

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
            LoginManager.getInstance().logOut();
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

        //if (view == imageZoom) {

            //PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);

           // photoView.
            Picasso.with(ProfilAnsichtEigenesProfil.this).load(String.valueOf(bitmap)).into(EigenesProfilBild);

       // }
    }

    public void profilbildAnzeigen (){

            databaseProfilbilder.child(aktuelleUserID).addValueEventListener(new ValueEventListener() {
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
                    if (downloadUrl.contains("scontent")){
                        try {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);

                            URL url = new URL(downloadUrl);
                            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                            connection.setDoInput(true);
                            connection.connect();
                            InputStream input = connection.getInputStream();
                            Bitmap profilePic = BitmapFactory.decodeStream(input);
                            EigenesProfilBild.setImageBitmap(profilePic);
                        } catch (IOException e) {
                        }
                    }
                    else {
                        StorageReference storageReference = storage.getInstance().getReferenceFromUrl(downloadUrl);
                        Glide.with(ProfilAnsichtEigenesProfil.this)
                                .using(new FirebaseImageLoader())
                                .load(storageReference)
                                .into(EigenesProfilBild);
                    }
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
    }


    // alte Ansätze wg. Profilbild anzeigen:


    public void displayEigenesProfilbild () {

        /*

        databaseProfilbilder.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                downloadUrl = dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        */


        downloadUrl = "https://firebasestorage.googleapis.com/v0/b/smap-dhbw2.appspot.com/o/images%2F37?alt=media&token=9d046aa6-71fa-41a1-9f9a-7b58577c3d71";

        //FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getInstance().getReferenceFromUrl(downloadUrl).child("android.jpg");

        //DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/smap-dhbw2.appspot.com/o/images");

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

}
