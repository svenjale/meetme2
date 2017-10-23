package de.meetme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * Created by kortsch on 22.10.2017.
 */

public class KontaktVorschlagReceiver extends BroadcastReceiver {

    private Firebase databaseLocations;
    private FirebaseAuth firebaseAuth;
    private Firebase databaseKontaktvorschlaege;
    private Location eigeneLocation = new Location ("eigeneLocation");
    private Location andereLocation = new Location ("andere Location");
    private Float distance = 0F;

   // private static Map<Marker, String> moeglicheVorschlaege = new HashMap<Marker, String>(); // statt Marker Location oder Person?

    private static ArrayList <String> moeglicheKontakte = new ArrayList<String>();

    private static final String FIREBASE_URL = "https://smap-dhbw2.firebaseio.com";

    /*
    protected void onCreate (Bundle savedInstanceState){
        databaseLocations = new Firebase(FIREBASE_URL).child("locations");
    }*/

    @Override
    public void onReceive(Context context, Intent intent) {

        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        databaseLocations = new Firebase(FIREBASE_URL).child("locations");
        databaseKontaktvorschlaege = new Firebase(FIREBASE_URL).child("kontaktvorschlaege").child(firebaseAuth.getInstance().getCurrentUser().getUid());

        Toast.makeText(context, "Testpunkt 1" ,Toast.LENGTH_SHORT).show();


        databaseLocations.child(profilansicht.aktuelleUserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    UserLocation ich = dataSnapshot.getValue(UserLocation.class);
                    eigeneLocation.setLatitude(ich.getLat());
                    eigeneLocation.setLongitude(ich.getLng());

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        databaseLocations.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {
                        UserLocation du = snapshot.getValue(UserLocation.class);


                        if (du.getUserID().equals(profilansicht.aktuelleUserID)) {
                            return;
                        } else {
                            andereLocation.setLatitude(du.getLat());
                            andereLocation.setLongitude(du.getLng());
                            distance = eigeneLocation.distanceTo(andereLocation);
                            if (distance <= 5F) {
                                if (moeglicheKontakte.contains(du.getUserID())) {
                                    databaseKontaktvorschlaege.child(du.getUserID()).setValue(du.getUserID());
                                    // String zu Direbase, aus Hasmap entfernen, ENtfernen überhaupt nltig?
                                } else {
                                    moeglicheKontakte.add(du.getUserID());
                                }
                            } else {
                                if (moeglicheKontakte.contains(du.getUserID())) {
                                    moeglicheKontakte.remove(du.getUserID());
                                }
                            }

                        }
                    }catch (NullPointerException npp){

                    }
                }
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Toast.makeText(context, ""+distance ,Toast.LENGTH_SHORT).show();
        Toast.makeText(context, " "+andereLocation.getLatitude()+" "+andereLocation.getLongitude() ,Toast.LENGTH_SHORT).show();
        Toast.makeText(context, moeglicheKontakte.toString() ,Toast.LENGTH_SHORT).show();



    }
}
