package de.meetme;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;


public class eventinfos extends Activity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private Firebase databaseEvents;
    private Firebase databaseEventteilnehmer;
    private Firebase databaseEventanwesende;
    private Firebase databaseTeilnahmen;
    private Firebase databaseProfiles;
    private Firebase databaseLocations;
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
    private ImageButton button21;
    private ImageButton button23;
    private ImageButton button14;
    private ImageButton button24;
    private ImageButton button12;
    private Button button26;
    private Animation animfadein;


    private ValueEventListener eventListener;

    private static final String FIREBASE_URL = "https://smap-dhbw2.firebaseio.com";

    private String uebergebeneID;
    //private String uebergebenerName;
    public static String orgaID = "";

    public String whatsappName="";
    String whatsappOrt="";
    String whatsappDatum="";
    public String whatsappUhrzeit="";
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
        databaseLocations = new Firebase(FIREBASE_URL).child("locations");

        button9 = findViewById(R.id.button9);
        button11 = findViewById(R.id.button11);
        button10 = findViewById(R.id.button10);
        button7 = findViewById(R.id.button7);
        button3 = findViewById(R.id.button3);
        button6 = findViewById(R.id.button6);
        button2 = findViewById(R.id.button2);
        button5 = findViewById(R.id.button5);
        button21 = findViewById(R.id.button21);
        button23 = findViewById(R.id.button23);
        button14 = findViewById(R.id.button14);
        button24 = findViewById(R.id.button24);
        button26 = findViewById(R.id.button26);
        button12 = findViewById(R.id.button12);

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

        textView7 = findViewById(R.id.textView7);
        textView31 = findViewById(R.id.textView31);
        textView24 = findViewById(R.id.textView24);
        textView19 = findViewById(R.id.textView19);
        textView17 = findViewById(R.id.textView17);
        //textView21 = (TextView) findViewById(R.id.textView21);

        textView17.setOnClickListener(this);

        button14.setOnClickListener(this);
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
        button24.setOnClickListener(this);
        button26.setOnClickListener(this);
        button12.setOnClickListener(this);
    }

    @Override
    public void onStart(){
        super.onStart();

        databaseEventteilnehmer.child(uebergebeneID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String teilnehmer = snapshot.getValue(String.class);
                    if (teilnehmer.equals(profilansicht.aktuelleUserID)) {
                        button26.setVisibility(View.VISIBLE);
                        button9.setAlpha(.5f);
                        button9.setClickable(false);
                        return;
                    }else{
                        button26.setVisibility(View.GONE);
                        button9.setAlpha(1.0f);
                        button9.setClickable(true);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        databaseEventanwesende.child(uebergebeneID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String teilnehmer = snapshot.getValue(String.class);
                    if (teilnehmer.equals(profilansicht.aktuelleUserID)) {
                        button26.setVisibility(View.VISIBLE);
                        button9.setAlpha(.5f);
                        button9.setClickable(false);
                        button5.setAlpha(.5f);
                        button5.setClickable(false);
                        return;
                    }else{
                        button26.setVisibility(View.GONE);
                        button9.setAlpha(1.0f);
                        button9.setClickable(true);
                        button5.setAlpha(1.0f);
                        button5.setClickable(true);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        if (databaseEventteilnehmer.child(uebergebeneID).child(profilansicht.aktuelleUserID)!=null ){
            button26.setVisibility(View.VISIBLE);
        }
        else {button26.setVisibility(View.GONE);}


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
                    ((TextView) findViewById(R.id.textView19)).setText("am "+event.getDatum()+" um "+event.getUhrzeit()+" Uhr");
                    whatsappDatum=event.getDatum();
                    //((TextView) findViewById(R.id.textView21)).setText(event.getUhrzeit());
                    whatsappUhrzeit=event.getUhrzeit();
                    orgaID = event.getOrganisatorID();
                    if (orgaID.equals(profilansicht.aktuelleUserID)) {
                        //button26.setVisibility(View.GONE);
                        button26.setText("Event bearbeiten");
                        button9.setAlpha(.5f);
                        button9.setClickable(false);
                    }

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
            databaseEventteilnehmer.child(uebergebeneID).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
            databaseTeilnahmen.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(uebergebeneID).setValue(uebergebeneID);
            Toast.makeText(this, "Erfolgreich angemeldet. Bis bald!", Toast.LENGTH_LONG).show();
            button26.setVisibility(View.VISIBLE);
            button9.setAlpha(0.5f);
            button9.setClickable(false);
        }
        if (view == button5) {
            databaseEventanwesende.child(uebergebeneID).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
            databaseEventteilnehmer.child(uebergebeneID).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
            databaseTeilnahmen.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(uebergebeneID).setValue(uebergebeneID);
            // wenn anwesend, dann auch Teilnehmer
            button26.setVisibility(View.VISIBLE);
            button9.setAlpha(0.5f);
            button9.setClickable(false);
            button5.setAlpha(0.5f);
            button5.setClickable(false);
            Toast.makeText(this, "Check-In erfolgreich. Viel Spaß beim Event!", Toast.LENGTH_LONG).show();
            databaseEventanwesende.child(uebergebeneID).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
            databaseEventteilnehmer.child(uebergebeneID).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
            databaseTeilnahmen.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(uebergebeneID).setValue(uebergebeneID);
            // wenn anwesend, dann auch Teilnehmer

            /*/AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, LocationAlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),3000, pendingIntent); //300000*/

            Intent myIntent = new Intent(eventinfos.this, LocationAlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(eventinfos.this, 0,myIntent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC,System.currentTimeMillis(), 1000, pendingIntent);
        }
        if (view == button2) {
            Intent Profil = new Intent(eventinfos.this, profilansicht.class);
            startActivity(Profil);
        }
        if (view == button7) {
            Intent Walk = new Intent(eventinfos.this, Eventliste_activity.class);
            startActivity(Walk);
        }
        if (view == button14) {
            Intent gohelp = new Intent(eventinfos.this, helpevent.class);
            startActivity(gohelp);

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
            alarmStellen();
            Toast.makeText(this, "Wir werden dich 15 Min. vor Beginn erinnern", Toast.LENGTH_SHORT).show();

        }
        if (view == button24) {
            Intent Map = new Intent(eventinfos.this, MapsActivity.class);
            Map.putExtra("eventID", uebergebeneID);
            Map.putExtra("Adresse", whatsappOrt);
            Map.putExtra("Eventname", whatsappName);

            startActivity(Map);}

        if (view==button26){
            if (orgaID.equals(profilansicht.aktuelleUserID)){
                Intent eventbearbeiten = new Intent (eventinfos.this, createevent.class);
                eventbearbeiten.putExtra("eventID", uebergebeneID);
                startActivity(eventbearbeiten);
            }else {
                databaseEventteilnehmer.child(uebergebeneID).child(profilansicht.aktuelleUserID).removeValue(); //löschen
                if (databaseEventanwesende.child(uebergebeneID).child(profilansicht.aktuelleUserID) != null) {
                    databaseEventanwesende.child(uebergebeneID).child(profilansicht.aktuelleUserID).removeValue();
                }
                databaseTeilnahmen.child(profilansicht.aktuelleUserID).child(uebergebeneID).removeValue();
                ;
                Toast.makeText(eventinfos.this, "Du wurdest vom Event abgemeldet.", Toast.LENGTH_SHORT).show();
                button26.setVisibility(View.GONE);
                button9.setAlpha(1.0f);
                button9.setClickable(true);
                button5.setAlpha(1.0f);
                button5.setClickable(true);
            }
        }
        if (view==button12){
            kalendereintragErstellen(button12);

        }

    }

    public void kalendereintragErstellen(View view){
        StringBuffer date = new StringBuffer(whatsappDatum);
        int d = Integer.parseInt(date.substring(0,2));
        int m = Integer.parseInt(date.substring(3,5));
        int y = Integer.parseInt(date.substring(6,10));
        StringBuffer time = new StringBuffer(whatsappUhrzeit);
        int h =  Integer.parseInt(time.substring(0,2));
        int mm =  Integer.parseInt(time.substring(3,5));

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(Calendar.MONTH, m-1);
        beginTime.set(Calendar.YEAR, y);
        beginTime.set(Calendar.DAY_OF_MONTH, d);
        beginTime.set(Calendar.HOUR_OF_DAY, h);
        beginTime.set(Calendar.MINUTE, mm);
        beginTime.set(Calendar.SECOND, 00);

        /*Calendar endTime = beginTime;
        endTime.add(Calendar.HOUR_OF_DAY, +3);*/

        beginTime.set(Calendar.AM_PM, Calendar.PM);
        //endTime.set(Calendar.AM_PM, Calendar.PM);

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, beginTime.getTimeInMillis()+10800000) // Standard Dauer 3 Stunden (in Milisekunden)
                .putExtra(CalendarContract.Events.TITLE, "Smap: "+whatsappName)
                .putExtra(CalendarContract.Events.DESCRIPTION, whatsappBeschreibung+"\n\n"+"Weitere Infos in deiner Smap App.")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, whatsappOrt)
                .putExtra(CalendarContract.Events.HAS_ALARM, 0)
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
        startActivity(intent);
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

public void alarmStellen (){
    StringBuffer date = new StringBuffer(whatsappDatum);
    int d = Integer.parseInt(date.substring(0,2));
    int m = Integer.parseInt(date.substring(3,5));
    int y = Integer.parseInt(date.substring(6,10));
    StringBuffer time = new StringBuffer(whatsappUhrzeit);
    int h =  Integer.parseInt(time.substring(0,2));
    int mm =  Integer.parseInt(time.substring(3,5));

    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MONTH, m-1);
    calendar.set(Calendar.YEAR, y);
    calendar.set(Calendar.DAY_OF_MONTH, d);


    calendar.set(Calendar.HOUR_OF_DAY, h);
    calendar.set(Calendar.MINUTE, mm);
    calendar.set(Calendar.SECOND, 00);

    calendar.add (Calendar.MINUTE, -15); // hier einstellen, wann Benachrichtigung erscheinen soll, z.B. 15 Min

    calendar.set(Calendar.AM_PM, Calendar.PM);

    Intent myIntent = new Intent(eventinfos.this, MyReceiver.class);
    myIntent.putExtra("ID", uebergebeneID);
    myIntent.putExtra("name", whatsappName);
    myIntent.putExtra("uhrzeit", whatsappUhrzeit);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(eventinfos.this, 0,myIntent, 0);

    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
}
}
