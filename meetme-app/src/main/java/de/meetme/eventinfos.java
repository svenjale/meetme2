package de.meetme;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;


public class eventinfos extends Activity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private Firebase databaseEvents;
    private Firebase databaseEventteilnehmer;
    private Firebase databaseEventanwesende;
    private Firebase databaseTeilnahmen;
    private Firebase databaseProfiles;
    private Button button9;
    private Button button5;
    private TextView textView7;
    private TextView textView31;
    private TextView textView24;
    private TextView textView19;
    private TextView textView17;
    private TextView textView21;
    private Button button2;
    private Button button7;
    private Button button3;
    private Button button6;
    private Button button11;
    private Button button10;
    private Button button21;
    private Button button23;

    private ValueEventListener eventListener;

    private static final String FIREBASE_URL = "https://smap-dhbw2.firebaseio.com";

    private String uebergebeneID;
    //private String uebergebenerName;
    public static String orgaID = "";

    String whatsappName="";
    String whatsappOrt="";
    String whatsappDatum="";
    String whatsappUhrzeit="";
    String whatsappBeschreibung="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventinfos);

        Intent intent = getIntent();
        uebergebeneID = intent.getStringExtra("ID");


        databaseEvents = new Firebase(FIREBASE_URL).child("events").child(uebergebeneID);
        databaseEventteilnehmer = new Firebase(FIREBASE_URL).child("eventteilnehmer");
        databaseEventanwesende = new Firebase(FIREBASE_URL).child("eventanwesende");
        databaseTeilnahmen = new Firebase(FIREBASE_URL).child("teilnahmen");

        button9 = (Button) findViewById(R.id.button9);
        button11 = (Button) findViewById(R.id.button11);
        button10 = (Button) findViewById(R.id.button10);
        button7 = (Button) findViewById(R.id.button7);
        button3 = (Button) findViewById(R.id.button3);
        button6 = (Button) findViewById(R.id.button6);
        button2 = (Button) findViewById(R.id.button2);
        button5 = (Button) findViewById(R.id.button5);
        button21 = (Button) findViewById(R.id.button21);
        button23 = (Button) findViewById(R.id.button23);

        textView7 = (TextView) findViewById(R.id.textView7);
        textView31 = (TextView) findViewById(R.id.textView31);
        textView24 = (TextView) findViewById(R.id.textView24);
        textView19 = (TextView) findViewById(R.id.textView19);
        textView17 = (TextView) findViewById(R.id.textView17);
        textView21 = (TextView) findViewById(R.id.textView21);

        textView17.setOnClickListener(this);

        button11.setOnClickListener(this);
        button7.setOnClickListener(this);
        button3.setOnClickListener(this);
        button6.setOnClickListener(this);
        button2.setOnClickListener(this);
        button9.setOnClickListener(this);
        button10.setOnClickListener(this);
        button5.setOnClickListener(this);
        button21.setOnClickListener(this);
        button23.setOnClickListener(this);


    }

    @Override
    public void onStart(){
        super.onStart();
        //Event Laden
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Event event = dataSnapshot.getValue(Event.class);
                if (event != null) {
                    ((TextView) findViewById(R.id.textView7)).setText(event.getEventname());
                    whatsappName=event.getEventname();
                    ((TextView) findViewById(R.id.textView31)).setText(event.getBeschreibung());
                    whatsappBeschreibung=event.getBeschreibung();
                    ((TextView) findViewById(R.id.textView24)).setText(event.getOrt());
                    whatsappOrt=event.getOrt();
                    ((TextView) findViewById(R.id.textView19)).setText(event.getDatum());
                    whatsappDatum=event.getDatum();
                    ((TextView) findViewById(R.id.textView21)).setText(event.getUhrzeit());
                    whatsappUhrzeit=event.getUhrzeit();
                    orgaID = event.getOrganisatorID();
                    //uebergebenerName=event.getEventname();
                    // Profil laden
                    databaseProfiles = new Firebase(FIREBASE_URL).child("profiles").child(orgaID);
                    ValueEventListener organisatorListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                            Person organisator = dataSnapshot.getValue(Person.class);
                            String organisatorName=organisator.getVorname()+" "+organisator.getName()+" ("+organisator.getRolle()+")";
                            ((TextView) findViewById(R.id.textView17)).setText(organisatorName);
                        }
                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            Toast.makeText(eventinfos.this, "Fehler beim Laden der Eventdetails.", Toast.LENGTH_SHORT).show();
                        }
                    };
                    databaseProfiles.addValueEventListener(organisatorListener);
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(eventinfos.this, "Fehler beim Laden der Eventdetails.", Toast.LENGTH_SHORT).show();
            }
        };
        databaseEvents.addValueEventListener(eventListener);
    }
    // Hilfe: https://github.com/firebase/quickstart-android/blob/master/database/app/src/main/java/com/google/firebase/quickstart/database/PostDetailActivity.java#L84-L106


    public void onClick(View view) {
        if (view == button9) {
            databaseEventteilnehmer.child(uebergebeneID).child(firebaseAuth.getInstance().getCurrentUser().getUid()).setValue(firebaseAuth.getInstance().getCurrentUser().getUid());
            databaseTeilnahmen.child(firebaseAuth.getInstance().getCurrentUser().getUid()).child(uebergebeneID).setValue(uebergebeneID);
            Toast.makeText(this, "Erfolgreich angemeldet. Bis bald!", Toast.LENGTH_LONG).show();
        }
        if (view == button5) {
            databaseEventanwesende.child(uebergebeneID).child(firebaseAuth.getInstance().getCurrentUser().getUid()).setValue(firebaseAuth.getInstance().getCurrentUser().getUid());
            databaseEventteilnehmer.child(uebergebeneID).child(firebaseAuth.getInstance().getCurrentUser().getUid()).setValue(firebaseAuth.getInstance().getCurrentUser().getUid());
            databaseTeilnahmen.child(firebaseAuth.getInstance().getCurrentUser().getUid()).child(uebergebeneID).setValue(uebergebeneID);
            // wenn anwesend, dann auch Teilnehmer
            Toast.makeText(this, "Check-In erfolgreich. Viel Spaß beim Event!", Toast.LENGTH_LONG).show();
        }
        if (view == button2) {
            Intent Profil = new Intent(eventinfos.this, profilansicht.class);
            startActivity(Profil);
        }
        if (view == button7) {
            Intent Walk = new Intent(eventinfos.this, Eventliste_activity.class);
            startActivity(Walk);
        }
        if (view == button3) {
            Intent Map = new Intent(eventinfos.this, MapsActivity.class);
            startActivity(Map);
        }
        if (view == button6) {
            Intent Kontakte = new Intent(eventinfos.this, kontakte.class);
            startActivity(Kontakte);
        }
        if (view == button11) {
            Intent Teilnehmerliste = new Intent(eventinfos.this, teilnehmerlist.class);
            Teilnehmerliste.putExtra("ID", uebergebeneID);
            Teilnehmerliste.putExtra("Name", whatsappName);
            startActivity(Teilnehmerliste);
        }
        if (view == button21) {
            mitWhatsAppTeilen(button21);
        }
        if (view == button10) {
            Intent Anwesendeliste = new Intent(eventinfos.this, anwesendelist.class);
            Anwesendeliste.putExtra("ID", uebergebeneID);
            Anwesendeliste.putExtra("Name", whatsappName);

            startActivity(Anwesendeliste);
    }
        if (view == textView17) {
            if (orgaID.equals(profilansicht.aktuellerUser.getPersonID())){
                Intent Profil = new Intent(eventinfos.this, profilansicht.class);
                startActivity(Profil);
            }else {
                Intent profile = new Intent(eventinfos.this, profilansichtAndererUser.class);
                profile.putExtra("ID", orgaID);
                startActivity(profile);
            }
        }
        if (view == button23) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                alarmStellen();
            }else{
                Toast.makeText(this, "Leider unterstützt deine Android Version diese Funktion nicht.", Toast.LENGTH_SHORT).show();
            }
        }
    }



    public void mitWhatsAppTeilen(View view) {

        PackageManager pm = getPackageManager();
        try {
            Intent waIntent = new Intent(Intent.ACTION_SEND); // hier sendto eingefügt
            waIntent.setType("text/plain");
            String text = "Hi! Ich habe diesen Photowalk auf Smap gefunden. Komm doch auch vorbei!"+"\n\n"+whatsappName+"\n"+"am "+whatsappDatum+" um "+whatsappUhrzeit+" Uhr\n"+"hier: "+whatsappOrt+"\n\n"+"Beschreibung: "+whatsappBeschreibung+"\n\n"+"Viele Grüße"+"\n"+profilansicht.aktuellerUser.getVorname()+" "+profilansicht.aktuellerUser.getName();
            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");
            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp nicht installiert", Toast.LENGTH_SHORT).show();
            Uri uri = Uri.parse("smsto:");
            Intent it = new Intent(Intent.ACTION_SENDTO, uri);
            String text = "Hi! Ich habe diesen Photowalk auf Smap gefunden. Komm doch auch vorbei!"+"\n\n"+whatsappName+"\n"+"am "+whatsappDatum+" um "+whatsappUhrzeit+" Uhr\n"+"hier: "+whatsappOrt+"\n\n"+"Beschreibung: "+whatsappBeschreibung+"\n\n"+"Viele Grüße"+"\n"+profilansicht.aktuellerUser.getVorname()+" "+profilansicht.aktuellerUser.getName();
            it.putExtra("sms_body",text);
            startActivity(it);
        }

    }


    //Notiz Johann: https://stackoverflow.com/questions/37031222/firebase-add-new-child-with-specified-name
    // evtl if Abfrage für Erfolg?!


   // public String getProfilName (String organisatorID){
    //}

@RequiresApi(api = Build.VERSION_CODES.N)
public void alarmStellen (){
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MONTH, 9);
    calendar.set(Calendar.YEAR, 2017);
    calendar.set(Calendar.DAY_OF_MONTH, 07);

    calendar.set(Calendar.HOUR_OF_DAY, 17);
    calendar.set(Calendar.MINUTE, 45);
    calendar.set(Calendar.SECOND, 00);

    calendar.set(Calendar.AM_PM, Calendar.PM);

    Intent myIntent = new Intent(eventinfos.this, MyReceiver.class);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(eventinfos.this, 0,myIntent, 0);

    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
}
}
