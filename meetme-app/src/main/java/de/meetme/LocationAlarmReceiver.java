package de.meetme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by kortsch on 22.10.2017.
 */

public class LocationAlarmReceiver extends BroadcastReceiver {

    private Firebase databaseLocations;
    private static final String FIREBASE_URL = "https://smap-dhbw2.firebaseio.com";

    /*
    protected void onCreate (Bundle savedInstanceState){
        databaseLocations = new Firebase(FIREBASE_URL).child("locations");
    }*/

    @Override
    public void onReceive(Context context, Intent intent) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        databaseLocations = new Firebase(FIREBASE_URL).child("locations");

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(context, "Permission Error", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            UserLocation loc = new UserLocation(latitude, longitude, FirebaseAuth.getInstance().getCurrentUser().getUid());
            //databaseLocations.child(uebergebeneID).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(loc);
            databaseLocations.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(loc);
            Toast.makeText(context, "Network Location Update Test erfolgreich", Toast.LENGTH_LONG).show();
        }catch (NullPointerException np){
            try {
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                UserLocation loc = new UserLocation(latitude, longitude, FirebaseAuth.getInstance().getCurrentUser().getUid());
                //databaseLocations.child(uebergebeneID).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(loc);
                databaseLocations.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(loc);
                Toast.makeText(context, "GPS Location Update Test erfolgreich", Toast.LENGTH_LONG).show();
            }catch (NullPointerException np2){
                Toast.makeText(context, "Deine Location ist nicht verfügbar", Toast.LENGTH_LONG).show();
                return;
            }
        }

        /* hier mit Listener und for Schleife die Location mit allen anderen Locations vergleichen
        Problem: Location vor und nach Update? 2 oder mehr Location Tabellen? --> kompliziert

        zunächst einfach in der for schleife des listeners: wenn location +5m Radius, dann zu Kontaktvorschlaege Tabelle hinzufügen

        */

    }
}
