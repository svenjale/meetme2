package de.meetme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;


public class Willkommen extends Activity implements View.OnClickListener {

    private static final String TAG = Willkommen.class.getSimpleName();
    private static final String FIREBASE_URL = "https://smap-dhbw2.firebaseio.com";

    private Firebase myFirebaseRef;
    private FirebaseAuth firebaseAuth;
    private Firebase databaseProfiles;
    private Firebase databaseProfilbilder;
    private Button buttonLogin2;
    private Button buttonRegis2;
    private Button button14;
    private Animation animfadelangs;
    private ImageView imageView;
    Person profil = new Person ("","","","","");

    private LoginButton mFacebookLoginButton;
    /* The callback manager for Facebook */
    private CallbackManager callbackManager;
    /* Used to track user logging in/out off Facebook */
    private AccessTokenTracker mFacebookAccessTokenTracker;


    protected void onCreate(Bundle savedInstanceState) {

        myFirebaseRef = new Firebase (FIREBASE_URL);
        databaseProfiles = new Firebase(FIREBASE_URL).child("profiles");
        databaseProfilbilder = new Firebase(FIREBASE_URL).child("profilbilder");
        firebaseAuth = FirebaseAuth.getInstance();

        profil = new Person("", "", "", "", "");


        Firebase.setAndroidContext(this);
        FacebookSdk.sdkInitialize(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.willkommen);

        buttonLogin2 = (Button) findViewById(R.id.buttonLogin2);
        buttonRegis2 = (Button) findViewById(R.id.buttonRegis2);
        button14 = (Button) findViewById(R.id.button14);
        imageView = findViewById(R.id.imageView);
        mFacebookLoginButton = findViewById(R.id.login_button);
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
        mFacebookLoginButton.setAnimation(animfadelangs);


        button14.setOnClickListener(this);
        buttonLogin2.setOnClickListener(this);
        buttonRegis2.setOnClickListener(this);

        callbackManager = CallbackManager.Factory.create();
        mFacebookLoginButton = (LoginButton) findViewById(R.id.login_button);
        mFacebookLoginButton.setReadPermissions("email");


        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                signInWithFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        ProfilAnsichtEigenesProfil.aktuellerUser=new Person("","","","","");
        ProfilAnsichtEigenesProfil.aktuelleUserID="";

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void onFacebookLogInClicked( View view ){
        LoginManager
                .getInstance()
                .logInWithReadPermissions(
                        this,
                        Arrays.asList("public_profile", "user_friends", "email")
                );
    }


    private void signInWithFacebook(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(Willkommen.this, "Facebook Anmeldung fehlgeschlagen.",
                                    Toast.LENGTH_SHORT).show();
                        }else{

                                final String uid=task.getResult().getUser().getUid();
                                profil.setPersonID(uid);

                                try {
                                String kontakt = task.getResult().getUser().getPhoneNumber();
                                if(kontakt != null && !kontakt.isEmpty()) {
                                    profil.setKontakt(kontakt);
                                }
                                }catch (NullPointerException np2){
                                }

                                String name=task.getResult().getUser().getDisplayName();
                                int start = name.indexOf(' ');
                                int end = name.lastIndexOf(' ');
                                String firstName = "";
                                String middleName = "";
                                String lastName = "";
                                if (start >= 0) {
                                    firstName = name.substring(0, start);
                                    if (end > start)
                                        middleName = name.substring(start + 1, end);
                                    lastName = name.substring(end + 1, name.length());
                                }
                                profil.setName(lastName);
                                profil.setVorname(firstName+" "+middleName.trim());


                            String image =  task.getResult().getUser().getProviderData().get(0).getPhotoUrl().toString();
                            //String image=task.getResult().getUser().getPhotoUrl().toString();
                            databaseProfilbilder.child(task.getResult().getUser().getUid()).setValue(image);

                            databaseProfiles.child(task.getResult().getUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    try {
                                        Person person = dataSnapshot.getValue(Person.class);
                                        profil.setKontakt(person.getKontakt());
                                        profil.setRolle(person.getRolle());
                                        profil.setVorname(person.getVorname());
                                        profil.setName(person.getName());
                                        databaseProfiles.child(uid).setValue(profil);

                                        Intent intent = new Intent(getApplicationContext(), EventListe.class);
                                        intent.putExtra("ID", uid);
                                        //intent.putExtra("profile_picture",image);
                                        startActivity(intent);
                                        finish();

                                        // hier ebenfalls ggf. Zugriff auf Facebook Infos, die sich geändert haben könnten, z.B. Bild URL, hinzufügen


                                    } catch (NullPointerException np) {
                                        ProfilAnsichtEigenesProfil.aktuellerUser=profil;
                                        databaseProfiles.child(uid).setValue(profil);
                                        Intent intent = new Intent(getApplicationContext(), ProfilAktualisieren.class);
                                        //intent.putExtra("ID", uid);
                                        //intent.putExtra("profile_picture",image);
                                        startActivity(intent);
                                        finish();

                                    }
                                }
                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });

                        }
                        }

                });

    }



    @Override
    public void onBackPressed() {
        // verhindert Back nach Logout
    }


    public void onClick(View view) {

        if (view == buttonLogin2) {
            Intent Login2 = new Intent(Willkommen.this, Login.class);
            startActivity(Login2);
        }
        if (view == buttonRegis2) {
            Intent Regis2 = new Intent(Willkommen.this, Registrierung.class);            //Plus = Hinzufügen Button
            startActivity(Regis2);
        }
        if (view == button14) {
            Intent gohelp2 = new Intent(Willkommen.this, Help.class); //switch zur Registrierung
            startActivity(gohelp2);
        }
    }
}
