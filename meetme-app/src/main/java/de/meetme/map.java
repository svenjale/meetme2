package de.meetme;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
//import com.google.android.gms.maps.OnMyLocationButtonClickListener;
//import com.google.android.gms.maps.OnMyLocationClickListener;


    /**
     * Manipulates the Map once available.
     * This callback is triggered when the Map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
   /* @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-33.867, 151.206);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
*/

public class map extends  AppCompatActivity
        implements
        OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMarkerClickListener, View.OnClickListener {


    private static final int LOCATION_PERMISSION_REQUEST_CODE =1;
    private static final String FIREBASE_URL = "https://smap-dhbw2.firebaseio.com";

        private static java.util.Map allPersonMap = new HashMap<Marker, String>();
        private static java.util.Map allEventMap = new HashMap<Marker, String>();



        private boolean mPermissionDenied = false;

    private GoogleMap mMap;
    private String eventID;
    private String adresse;
    private String eventname;
    private Double lat;
    private Double lon;

        private TextView textView40;

        private Button button2;
        private Button button7;
        private Button button3;
        private Button button6;
        private ImageButton imageButton4;
        private Animation animfadein;
       // private Button button4;


        //BitmapDescriptor personicon = BitmapDescriptorFactory.fromResource(R.drawable.ic_person_black_24dp);

        String Name;
        String Rolle;

    private Marker locationMarker;
        private Marker locationMarker2;
        ChildEventListener locationlistener;
    Firebase databaseLocations;
        Firebase databaseLocations2;
        Firebase databaseEvents;
        Firebase databaseProfiles;
        Firebase databaseEventanwesende;



        //LatLngBounds bounds = new LatLngBounds.Builder().build();
    //private FusedLocationProviderClient mFusedLocationClient;

    Geocoder geocoder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        SupportMapFragment supportmapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportmapFragment.getMapAsync(this);
        //mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            Intent intent = getIntent();
            eventID = intent.getStringExtra("eventID");
            adresse = intent.getStringExtra("Adresse");
            eventname = intent.getStringExtra("Eventname");
        }catch (NullPointerException e){}

        databaseLocations2= new Firebase(FIREBASE_URL).child("locations");
        databaseEvents= new Firebase(FIREBASE_URL).child("events");

        textView40 = (TextView) findViewById(R.id.textView40); //WIESO findet er den nicht?? Null Pointer, Objekt nicht vorhanen???

        button3 = (Button) findViewById(R.id.button3);
        button6 = (Button) findViewById(R.id.button6);
        button2 = (Button) findViewById(R.id.button2);
        button7 = (Button) findViewById(R.id.button7);
       // button4 = (Button) findViewById(R.id.button4);
        imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
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
      //  button4.setOnClickListener(this);
        imageButton4.setOnClickListener(this);

        Intent myIntent = new Intent(map.this, LocationAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(map.this, 0,myIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC,System.currentTimeMillis(), 10000, pendingIntent);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnMarkerClickListener(this);
        enableMyLocation();
        LatLng stuttgart = new LatLng(48.7758459, 9.1829321);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stuttgart, 12));


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {
                if (allEventMap.get(arg0)!=null){
                    Intent event = new Intent (map.this, EventInfos.class);
                    event.putExtra("ID", allEventMap.get(arg0).toString());
                    startActivity(event);
                }
                if (allPersonMap.get(arg0)!=null){
                    Intent profile = new Intent(map.this, ProfilAnsichtAndererUser.class);
                    profile.putExtra("ID", allPersonMap.get(arg0).toString());
                    startActivity(profile);
                }

            }
        });

        if (eventID!=null){
            geoLocate(eventID);
            databaseEventanwesende= new Firebase(FIREBASE_URL).child("eventanwesende").child(eventID);
            addUserMarkersToMap(mMap);
            textView40.setText(eventname);
        }else{
            //Toast.makeText(Map.this, "XXX" + lat + lon, Toast.LENGTH_SHORT).show();
            textView40.setText("In deiner Nähe");
            databaseEvents.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot4 : dataSnapshot.getChildren()){
                        Event event = snapshot4.getValue(Event.class);
                        geoLocate(event.getEventID());
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
    }

    public void geoLocate (String eventID ) {

        geocoder = new Geocoder(this);

        if (Geocoder.isPresent()){
        databaseEvents.child(eventID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Event event2 = dataSnapshot.getValue(Event.class);
                String location = event2.getOrt();
                String name = event2.getEventname();
                String datum = event2.getDatum();
                String uhrzeit = event2.getUhrzeit();
                String id = event2.getEventID();
                try {
                    List<Address> list = geocoder.getFromLocationName(location, 1);
                    lat = list.get(0).getLatitude(); //getting latitude
                    lon = list.get(0).getLongitude();//getting longitude
                    //Toast.makeText(Map.this, "jhgfhjkjhg" + lat + lon, Toast.LENGTH_SHORT).show();
                    LatLng standort = new LatLng(lat, lon);
                    locationMarker = mMap.addMarker(new MarkerOptions().position(standort).title(name).snippet("Am "+datum+" um "+uhrzeit+" Uhr").icon(BitmapDescriptorFactory.fromResource(R.drawable.smapicons_klein)));
                    /*if(allEventMap.get(id)!=null){
                        allEventMap.remove(locationMarker);
                        Marker.remove
                    }*/  // HIER CODE FÜR MARKER VERÄNDERN, z.B. Blick in Hashmap, gibt es bereits einen Marker mit der ID, wenn ja, Marker von Map und Hashmap entfernen und neuen hinzufügen zu beidem
                    allEventMap.put(locationMarker, id);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(map.this, "Fehler beim Anzeigen des Event Standortes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        }else {
            // String geoLocatingUrl = "https://maps.googleapis.com/maps/api/geocode/json?address="+location+"&key=AIzaSyAgT2P8M0lPubru2ZbZjVepvYLsfbTsLn8";
            // get lat long by geo api google url
            Toast.makeText(map.this, "Geocoder Service nicht verfügbar", Toast.LENGTH_SHORT).show();
        }
    }


    private void addUserMarkersToMap(GoogleMap map) {

        databaseEventanwesende.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {
                        String anwesendID = snapshot.getValue(String.class);
                        databaseLocations = new Firebase(FIREBASE_URL).child("locations").child(anwesendID);
                        databaseLocations.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot2) {
                                UserLocation loc = dataSnapshot2.getValue(UserLocation.class);
                                String id = loc.getUserID();
                                Double latitude = loc.getLat();
                                Double longitude = loc.getLng();
                                final LatLng location = new LatLng(latitude, longitude);
                                databaseProfiles = new Firebase(FIREBASE_URL).child("profiles").child(id);
                                databaseProfiles.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot3) {
                                        Person profil = dataSnapshot3.getValue(Person.class);
                                        Name = profil.getVorname() + " " + profil.getName();
                                        Rolle = profil.getRolle();
                                        String id = profil.getPersonID();
                                        if (profil.getPersonID().equals(ProfilAnsichtEigenesProfil.aktuelleUserID)) {
                                            return;
                                        } else {
                                            locationMarker2 = mMap.addMarker(new MarkerOptions().position(location).title(Name).snippet(Rolle).
                                                    icon(BitmapDescriptorFactory.fromResource(R.drawable.faceicon)));
                                            allPersonMap.put(locationMarker2, id);
                                            // hier Code für Marker verschieben, z.B. Blick in Hashmap, gibt es bereits einen Marker mit der ID, wenn ja, Marker von Map und Hashmap entfernen und neuen hinzufügen zu beidem
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
                        });
                    }catch (NullPointerException npp){
                        // User Location nicht verfügbar
                    }
                }
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED) {
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap!= null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    public void standortInFirebaseAktualisieren(Location location){ // diese Methode an verschiedenen Stellen aufrufen oder evtl auch mit Listener für veränderten Standort
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        UserLocation loc = new UserLocation(latitude, longitude, FirebaseAuth.getInstance().getCurrentUser().getUid() );
        databaseLocations2.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(loc);

        /* databaseLocations.orderByChild("userID").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                dataSnapshot.getKey();
                databaseLocations.child(dataSnapshot.getKey()).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(loc);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        }); */
    }

    public void standortAbrufen (){


    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        standortInFirebaseAktualisieren(location);
        //Toast.makeText(this, "Jetziger Standort:\n"+ location, Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Das bist du, "+ ProfilAnsichtEigenesProfil.aktuellerUser.getVorname()+". :)", Toast.LENGTH_SHORT).show();

    }



    @Override
    public void onRequestPermissionsResult (int requestCode, @NonNull String [] permissions,
                                            @NonNull int [] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }
        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            enableMyLocation();
        } else {
            mPermissionDenied = true;
        }
    }

    @Override
    public void onResumeFragments(){
        super.onResumeFragments();
        if (mPermissionDenied) {
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog.newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        // hier onClick Profil öffnen mit Übergabe der ID an Intent Profilansicht anderer User

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {
                if (allPersonMap.get(arg0)!=null){
                    Intent profile = new Intent(map.this, ProfilAnsichtAndererUser.class);
                    profile.putExtra("ID", allPersonMap.get(arg0).toString());
                    startActivity(profile);
                }
                if (allEventMap.get(arg0)!=null){
                    Intent event = new Intent (map.this, EventInfos.class);
                    event.putExtra("ID", allEventMap.get(arg0).toString());
                    startActivity(event);
                }
            }
        });


        return false;
    }

        @Override
        public void onClick(View view) {
            if (view == button2) {
                Intent Profil = new Intent(map.this, ProfilAnsichtEigenesProfil.class);
                startActivity(Profil);
            }
            if (view == button7) {
                Intent Walk = new Intent(map.this, EventListe.class);
                startActivity(Walk);
            }
            if (view == button3) {

                Intent Map = new Intent(de.meetme.map.this, de.meetme.map.class);
                startActivity(Map);
            }
            if (view == button6) {
                Intent Kontakte = new Intent(map.this, KontakteListe.class);
                startActivity(Kontakte);
            }
            if (view == imageButton4){
                Intent Hilfe = new Intent(map.this, HelpMap2.class);
                startActivity(Hilfe);
            }
        }
    }