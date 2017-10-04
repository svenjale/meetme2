package de.meetme;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;

/**
 * @author greg
 * @since 6/21/13
 *
 * This class is an example of how to use FirebaseListAdapter. It uses the <code>Chat</code> class to encapsulate the
 * data for each individual chat message
 */
public class TeilnehmerListAdapter extends FirebaseListAdapter<Person> {

    public TeilnehmerListAdapter(Query ref, Activity activity, int layout) {
        super(ref, Person.class, layout, activity);
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
    protected void populateView(View view, Person teilnehmer) {
        // Map a Person object to an entry in our listview
        ((TextView) view.findViewById(R.id.teilnehmervorname)).setText(teilnehmer.getVorname());
        ((TextView) view.findViewById(R.id.teilnehmername)).setText(teilnehmer.getName());
        ((TextView) view.findViewById(R.id.teilnehmerrolle)).setText(teilnehmer.getRolle());
    }
}