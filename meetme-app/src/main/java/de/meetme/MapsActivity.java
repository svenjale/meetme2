package de.meetme;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.*;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.Manifest;
import android.support.annotation.NonNull;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Geocoder;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
//import com.google.android.gms.maps.OnMyLocationButtonClickListener;
//import com.google.android.gms.maps.OnMyLocationClickListener;


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
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

public class MapsActivity extends  AppCompatActivity
        implements
        OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMarkerClickListener {

    //String location =  "Johannesstr.90, 70176 Stuttgart";

    private static final int LOCATION_PERMISSION_REQUEST_CODE =1;
    private static final String FIREBASE_URL = "https://smap-dhbw2.firebaseio.com";

    private boolean mPermissionDenied = false;

    private GoogleMap mMap;

    private String eventID;
    private String adresse;
    private String eventname;
    private Double lat;
    private Double lon;

        //BitmapDescriptor personicon = BitmapDescriptorFactory.fromResource(R.drawable.ic_person_black_24dp);

        String Name;
        String Rolle;

    private Marker locationMarker;
    ChildEventListener locationlistener;
    Firebase databaseLocations;
        Firebase databaseLocations2;

        Firebase databaseProfiles;
        Firebase databaseEventanwesende;



        //LatLngBounds bounds = new LatLngBounds.Builder().build();
    //private FusedLocationProviderClient mFusedLocationClient;

    Geocoder geocoder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Intent intent = getIntent();
        eventID = intent.getStringExtra("eventID");
        adresse = intent.getStringExtra("Adresse");
        eventname = intent.getStringExtra("Eventname");

        databaseEventanwesende= new Firebase(FIREBASE_URL).child("eventanwesende").child(eventID);
        databaseLocations2= new Firebase(FIREBASE_URL).child("locations");


        setContentView(R.layout.activity_maps);
        SupportMapFragment supportmapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportmapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnMarkerClickListener(this);
        enableMyLocation();
        geoLocate(adresse);
        addUserMarkersToMap(mMap);

    }

    public void geoLocate (String location ) {

        geocoder = new Geocoder(this);
        if (geocoder.isPresent()){
            try {
            List<Address> list = geocoder.getFromLocationName(location, 1);
            lat = list.get(0).getLatitude(); //getting latitude
            lon = list.get(0).getLongitude();//getting longitude
            //Toast.makeText(MapsActivity.this, "jhgfhjkjhg" + lat + lon, Toast.LENGTH_SHORT).show();
            LatLng standort = new LatLng(lat, lon);
            locationMarker = mMap.addMarker(new MarkerOptions().position(standort).title(eventname + ": " + location));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationMarker.getPosition(), 14));
            } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MapsActivity.this, "Fehler beim Anzeigen des Event Standortes", Toast.LENGTH_SHORT).show();
            }
        }else {
            String geoLocatingUrl = "https://maps.googleapis.com/maps/api/geocode/json?address="+location+"&key=AIzaSyAgT2P8M0lPubru2ZbZjVepvYLsfbTsLn8";
            // get lat long by geo api google url
            Toast.makeText(MapsActivity.this, "Geocoder Service nicht verfügbar", Toast.LENGTH_SHORT).show();
        }
    }


    private void addUserMarkersToMap(GoogleMap map) {

        databaseEventanwesende.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String anwesendID = snapshot.getValue(String.class);
                    databaseLocations= new Firebase(FIREBASE_URL).child("locations").child(anwesendID);
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
                                    mMap.addMarker(new MarkerOptions().position(location).title(Name).snippet(Rolle).
                                            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
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
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

/*
        ValueEventListener anwesendeListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot da)
                Person anwesend = dataSnapshot.getValue(Person.class);
                String anwesendID = anwesend.getPersonID();

                databaseLocations= new Firebase(FIREBASE_URL).child("locations").child(anwesendID);
                ValueEventListener locationlistener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserLocation marker = dataSnapshot.getValue(UserLocation.class);
                        String id = marker.getUserID();
                        Double latitude = marker.getLat();
                        Double longitude = marker.getLng();
                        final LatLng location = new LatLng(latitude, longitude);

                        databaseProfiles = new Firebase(FIREBASE_URL).child("profiles").child(id);
                        ValueEventListener profilListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                                Person person = dataSnapshot.getValue(Person.class);
                                Name = person.getVorname() + " " + person.getName();
                                Rolle = person.getRolle();
                                mMap.addMarker(new MarkerOptions().position(location).title(Name).snippet(Rolle)); // hier Person Icon einfügen
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                            }
                        };
                        databaseProfiles.addValueEventListener(profilListener);

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                };
                databaseLocations.addValueEventListener(locationlistener);


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
        databaseEventanwesende.addValueEventListener(anwesendeListener);
    */
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
        Toast.makeText(this, profilansicht.aktuellerUser.getVorname()+" "+profilansicht.aktuellerUser.getName(), Toast.LENGTH_SHORT).show();

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
        return false;
    }
}