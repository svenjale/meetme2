package de.meetme;

import android.app.ListActivity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

public class EventListe extends ListActivity implements View.OnClickListener { // implements AdapterView.OnItemClickListener

    // TODO: change this to your own Firebase URL
    private static final String FIREBASE_URL = "https://smap-dhbw2.firebaseio.com";

    private String mUsername;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private EventListAdapter mEventListAdapter;

    private FirebaseAuth firebaseAuth;

    private Button button2;
    private Button button7;
    private Button button3;
    private Button button6;
    private Button button13;
    private ImageButton button14;
    private Animation animfadein;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventliste);

        mFirebaseRef = new Firebase(FIREBASE_URL).child("events");

        button7 = (Button) findViewById(R.id.button7);
        button3 = (Button) findViewById(R.id.button3);
        button6 = (Button) findViewById(R.id.button6);
        button2 = (Button) findViewById(R.id.button2);
        button13 = (Button) findViewById(R.id.button13);
        button14 = findViewById(R.id.button14);
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

        button14.setOnClickListener(this);
        button7.setOnClickListener(this);
        button3.setOnClickListener(this);
        button6.setOnClickListener(this);
        button2.setOnClickListener(this);
        button13.setOnClickListener(this);

        ProfilAnsichtEigenesProfil.aktuelleUserID = firebaseAuth.getInstance().getCurrentUser().getUid();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes
        final ListView listView = getListView();
        // Tell our list adapter that we only want 50 messages at a time
        mEventListAdapter = new EventListAdapter(mFirebaseRef.limit(50), this, R.layout.eventliste_element);
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
                    //Toast.makeText(EventListe.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(EventListe.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
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
                Intent details = new Intent (EventListe.this, EventInfos.class);
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
           Intent help = new Intent (EventListe.this, HelpEvent.class);
           startActivity(help);
       }

        if (view == button2) {
            Intent Profil = new Intent(EventListe.this, ProfilAnsichtEigenesProfil.class);
            startActivity(Profil);
        }
        if (view == button7) {
            Intent Walk = new Intent(EventListe.this, EventListe.class);
            startActivity(Walk);
        }
        if (view == button3) {
            Intent Map = new Intent(EventListe.this, de.meetme.Map.class);
            startActivity(Map);
        }
        if (view == button6) {
            Intent Kontakte = new Intent(EventListe.this, KontakteListe.class);
            startActivity(Kontakte);
        }
        if (view == button13) {
            Intent Plus = new Intent(EventListe.this, EventErstellen.class);            //Plus = Hinzufügen Button
            startActivity(Plus);
        }
    }


}
