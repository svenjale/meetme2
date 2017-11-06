package de.meetme;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;


public class ProfilAktualisieren extends Activity implements View.OnClickListener {

    private static final String FIREBASE_URL = "https://smap-dhbw2.firebaseio.com";

    private FirebaseAuth firebaseAuth;
    private Firebase databaseProfiles;
    private Firebase databaseEventteilnehmer;
    private Firebase databaseProfilbilder;

    private TextView textViewUserEmail;
    private Button buttonLogOut;
    private Button button2;
    private Button button7;
    private Button button3;
    private Button button6;
    private Button button4;


    private EditText editText;             //Name
    private EditText editText2;            //Vorname
    private EditText editText3;            //Kontakt
    private CheckBox checkBox;              // vier Rollen
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;
    private Animation animfadein;

    private ImageView ProfilBild;
    private Button change;
    private Button upload;
    private File mCurrentPhoto;
    private static final int SELECT_PHOTO = 100;
    Uri selectedImage;
    FirebaseStorage storage;
    StorageReference storageRef,imageRef;
    ProgressDialog progressDialog;
    UploadTask uploadTask;
    public static String aktuelleUserID;

    private String url ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilaktualisieren);

        databaseProfiles = new Firebase(FIREBASE_URL).child("profiles");
        databaseEventteilnehmer = new Firebase(FIREBASE_URL).child("eventteilnehmer");
        databaseProfilbilder = new Firebase(FIREBASE_URL).child("profilbilder");

        aktuelleUserID = firebaseAuth.getInstance().getCurrentUser().getUid();

        button7 = (Button) findViewById(R.id.button7);
        button3 = (Button) findViewById(R.id.button3);
        button6 = (Button) findViewById(R.id.button6);
        button2 = (Button) findViewById(R.id.button2);
        button4 = (Button) findViewById(R.id.button4);


        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);

        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) findViewById(R.id.checkBox4);

        change = (Button)findViewById(R.id.change);
        ProfilBild = (ImageView) findViewById(R.id.ProfilBild);
        upload = (Button)findViewById(R.id.upload);

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

        button7.setOnClickListener(this);
        button3.setOnClickListener(this);
        button6.setOnClickListener(this);
        button2.setOnClickListener(this);
        button4.setOnClickListener(this);

        change.setOnClickListener(this);
        upload.setOnClickListener(this);


    }

    public void onStart (){
        super.onStart();
        editText.setText(ProfilAnsichtEigenesProfil.aktuellerUser.getName());
        editText2.setText(ProfilAnsichtEigenesProfil.aktuellerUser.getVorname());
        editText3.setText(ProfilAnsichtEigenesProfil.aktuellerUser.getKontakt());
        if (ProfilAnsichtEigenesProfil.aktuellerUser.getRolle().contains("Model")) checkBox.setChecked(true);
        if (ProfilAnsichtEigenesProfil.aktuellerUser.getRolle().contains("Fotograf")) checkBox2.setChecked(true);
        if (ProfilAnsichtEigenesProfil.aktuellerUser.getRolle().contains("Organisator")) checkBox3.setChecked(true);
        if (ProfilAnsichtEigenesProfil.aktuellerUser.getRolle().contains("Visagist")) checkBox4.setChecked(true);
    }

    public void saveprofile(){
        String name = editText.getText().toString().trim();
        String vorname = editText2.getText().toString().trim();

        String rolle ="";
        if(checkBox.isChecked()) rolle = rolle+"Model";
        if(checkBox2.isChecked()) rolle = rolle+" Fotograf";
        if(checkBox3.isChecked()) rolle = rolle+" Organisator";
        if(checkBox4.isChecked()) rolle = rolle+" Visagist";
        rolle=rolle.trim();

        String kontakt = editText3.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {                                                          //Eventname Textfeld ist leer
            Toast.makeText(this, "Bitte Name eintragen", Toast.LENGTH_SHORT).show();     //wenn Feld leer ist, wird Ausführung unterbrochen
            return;
        }
        if (TextUtils.isEmpty(vorname)) {                                                          //Eventname Textfeld ist leer
            Toast.makeText(this, "Bitte Vorname eintragen", Toast.LENGTH_SHORT).show();     //wenn Feld leer ist, wird Ausführung unterbrochen
            return;
        }
        if (!checkBox.isChecked()&&!checkBox2.isChecked()&&!checkBox3.isChecked()&&!checkBox4.isChecked()) {                                                          //Eventname Textfeld ist leer
            Toast.makeText(this, "Wähle bitte mindestens eine Rolle", Toast.LENGTH_SHORT).show();     //wenn keine Rolle, wird Ausführung unterbrochen
            return;
        }

        if ((!TextUtils.isEmpty(vorname))&&(!TextUtils.isEmpty(name))&&(checkBox.isChecked()||checkBox2.isChecked()||checkBox3.isChecked()||checkBox4.isChecked())){
            Person profil = new Person (name, vorname, rolle, kontakt,firebaseAuth.getInstance().getCurrentUser().getUid());
            databaseProfiles.child(firebaseAuth.getInstance().getCurrentUser().getUid()).setValue(profil);
            Toast.makeText(this, "Profil wurde aktualisiert", Toast.LENGTH_LONG).show();
            Intent Profil = new Intent(ProfilAktualisieren.this, ProfilAnsichtEigenesProfil.class);
            startActivity(Profil);
            //databaseEventteilnehmer.orderByChild("personID").equalTo(firebaseAuth.getInstance().getCurrentUser().getUid()).
        }
    }

    @Override
    public void onClick(View view) {
        if (view == button2) {
            Intent Profil = new Intent(ProfilAktualisieren.this, ProfilAnsichtEigenesProfil.class);
            startActivity(Profil);
        }
        if (view == button7) {

            Intent Walk = new Intent(ProfilAktualisieren.this, EventListe.class);
            startActivity(Walk);
        }
        if (view == button3) {

            Intent Map = new Intent(ProfilAktualisieren.this, de.meetme.map.class);
            startActivity(Map);
        }
        if (view == button6) {

            Intent Kontakte = new Intent(ProfilAktualisieren.this, KontakteListe.class);
            startActivity(Kontakte);
        }
        if (view == button4) {
            saveprofile();

        }
        if (view == change) {
            //Toast.makeText(Profile_Activity.this, "Button wurde geklickt", Toast.LENGTH_SHORT).show();

            //accessing the firebase storage
            storage = FirebaseStorage.getInstance();

            //creates a storage reference
            storageRef = storage.getReference();

            //Toast.makeText(Profile_Activity.this, "Button wurde geklickt", Toast.LENGTH_SHORT);
            selectImage(view);
            //Toast.makeText(Profile_Activity.this, "nach select Image", Toast.LENGTH_SHORT).show();
            //displayPicture();
        }

        if (view == upload) {
            Toast.makeText(ProfilAktualisieren.this, "Button wurde geklickt", Toast.LENGTH_SHORT).show();

            uploadImage(view);
        }
    }

    public void selectImage(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(ProfilAktualisieren.this,"Bild ausgewählt, bitte upload drücken",Toast.LENGTH_SHORT).show();
                    selectedImage = imageReturnedIntent.getData();
                    // uploadTask = imageRef.putFile(selectedImage); fehler hier
                    Picasso.with(ProfilAktualisieren.this).load(selectedImage).into(ProfilBild);
                }
        }
    }

    public void uploadImage(View view) {
        //create reference to images folder and assing a name to the file that will be uploaded
        StorageReference storageRef = storage.getReference();
        imageRef = storageRef.child("images/" + selectedImage.getLastPathSegment());

        //creating and showing progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Uploading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        progressDialog.setCancelable(false);

        //starting upload
        uploadTask = imageRef.putFile(selectedImage);

        // Observe state change events such as progress, pause, and resume
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                //sets and increments value of progressbar
                progressDialog.incrementProgressBy((int) progress);
            }
        });
        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(ProfilAktualisieren.this, "Error in uploading!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                //String s = downloadUrl.toString();
                databaseProfilbilder.child(ProfilAktualisieren.aktuelleUserID).setValue(downloadUrl.toString());
                //url=downloadUrl.toString();
                Toast.makeText(ProfilAktualisieren.this, "Upload successful", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }

        });
    }
}

