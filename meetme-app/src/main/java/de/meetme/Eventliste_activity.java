package de.meetme;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Random;

public class Eventliste_activity extends ListActivity implements View.OnClickListener { // implements AdapterView.OnItemClickListener

    // TODO: change this to your own Firebase URL
    private static final String FIREBASE_URL = "https://smap-dhbw2.firebaseio.com";

    private String mUsername;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private EventListAdapter mEventListAdapter;

    private Button button2;
    private Button button7;
    private Button button3;
    private Button button6;
    private Button button13;
    private ImageButton button14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventlist);

        mFirebaseRef = new Firebase(FIREBASE_URL).child("events");

        button7 = (Button) findViewById(R.id.button7);
        button3 = (Button) findViewById(R.id.button3);
        button6 = (Button) findViewById(R.id.button6);
        button2 = (Button) findViewById(R.id.button2);
        button13 = (Button) findViewById(R.id.button13);
        button14 = findViewById(R.id.button14);

        button14.setOnClickListener(this);
        button7.setOnClickListener(this);
        button3.setOnClickListener(this);
        button6.setOnClickListener(this);
        button2.setOnClickListener(this);
        button13.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes
        final ListView listView = getListView();
        // Tell our list adapter that we only want 50 messages at a time
        mEventListAdapter = new EventListAdapter(mFirebaseRef.limit(50), this, R.layout.activity_eventlist_element);
        listView.setAdapter(mEventListAdapter);
        mEventListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(mEventListAdapter.getCount() - 1);
            }
        });

        // Finally, a little indication of connection status
        mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    //Toast.makeText(Eventliste_activity.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(Eventliste_activity.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // No-op
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                Event item = (Event) parent.getItemAtPosition(position);
                Intent details = new Intent (Eventliste_activity.this, eventinfos.class);
                details.putExtra("ID", item.getEventID());
                startActivity(details);
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        mFirebaseRef.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
        mEventListAdapter.cleanup();
    }

    public void onClick(View view) {
       if (view == button14) {
           Intent help = new Intent (Eventliste_activity.this, helpevent.class);
           startActivity(help);
       }

        if (view == button2) {
            Intent Profil = new Intent(Eventliste_activity.this, profilansicht.class);
            startActivity(Profil);
        }
        if (view == button7) {
            Intent Walk = new Intent(Eventliste_activity.this, Eventliste_activity.class);
            startActivity(Walk);
        }
        if (view == button3) {
            Intent Map = new Intent(Eventliste_activity.this, MapsActivity.class);
            startActivity(Map);
        }
        if (view == button6) {
            Intent Kontakte = new Intent(Eventliste_activity.this, kontakte.class);
            startActivity(Kontakte);
        }
        if (view == button13) {
            Intent Plus = new Intent(Eventliste_activity.this, createevent.class);            //Plus = Hinzufügen Button
            startActivity(Plus);
        }
    }


}
