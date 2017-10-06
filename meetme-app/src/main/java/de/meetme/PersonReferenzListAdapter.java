package de.meetme;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

/**
 * @author greg
 * @since 6/21/13
 *
 * This class is an example of how to use FirebaseListAdapter. It uses the <code>Chat</code> class to encapsulate the
 * data for each individual chat message
 */
public class PersonReferenzListAdapter extends FirebaseListAdapter<String> {

    private static final String FIREBASE_URL = "https://smap-dhbw2.firebaseio.com";
    private Firebase databaseProfiles;
    public static Person aktuellerUser2;

    public PersonReferenzListAdapter(Query ref, Activity activity, int layout) {
        super(ref, String.class, layout, activity);
    }

    /**
     * Bind an instance of the <code>Chat</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>Chat</code> instance that represents the current data to bind.
     *
     * @param view A view instance corresponding to the layout we passed to the constructor.
     * @param teilnehmer An instance representing the current state of a chat message
     */
    @Override
    protected void populateView(final View view, String teilnehmer) {
        // Map a Person object to an entry in our listview
        databaseProfiles = new Firebase(FIREBASE_URL).child("profiles").child(teilnehmer);
        ValueEventListener profilListener = new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                aktuellerUser2 = dataSnapshot.getValue(Person.class);//Toast.makeText(profilansicht.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                ((TextView) view.findViewById(R.id.teilnehmervorname)).setText(aktuellerUser2.getVorname());
                ((TextView) view.findViewById(R.id.teilnehmername)).setText(aktuellerUser2.getName());
                ((TextView) view.findViewById(R.id.teilnehmerrolle)).setText(aktuellerUser2.getRolle());}
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
        databaseProfiles.addValueEventListener(profilListener);

    }
}