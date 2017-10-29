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
public class EventReferenzListAdapter extends FirebaseListAdapter<String> {

    private static final String FIREBASE_URL = "https://smap-dhbw2.firebaseio.com";
    private Firebase databaseEvents;
    public static Event aktuellesEvent;

    public EventReferenzListAdapter(Query ref, Activity activity, int layout) {
        super(ref, String.class, layout, activity);
    }

    /**
     * Bind an instance of the <code>Chat</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>Chat</code> instance that represents the current data to bind.
     *
     * @param view A view instance corresponding to the layout we passed to the constructor.
     * @param event An instance representing the current state of a chat message
     */
    @Override
    protected void populateView(final View view, String event) {
        // Map a Person object to an entry in our listview
        databaseEvents = new Firebase(FIREBASE_URL).child("events").child(event);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                aktuellesEvent = dataSnapshot.getValue(Event.class);//Toast.makeText(ProfilAnsichtEigenesProfil.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                ((TextView) view.findViewById(R.id.eventname)).setText(aktuellesEvent.getEventname());
                ((TextView) view.findViewById(R.id.eventort)).setText(aktuellesEvent.getDatum());
                ((TextView) view.findViewById(R.id.eventdatum)).setText(aktuellesEvent.getOrt());}
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
        databaseEvents.addValueEventListener(eventListener);

    }
}