package de.meetme;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;




public class teilnehmerlist extends ListActivity implements View.OnClickListener{


    private static final String FIREBASE_URL = "https://smap-dhbw2.firebaseio.com";

    private FirebaseAuth firebaseAuth;
    private Firebase databaseEventteilnehmer;
    private Firebase databaseProfiles;

    private ValueEventListener mConnectedListener;
    private TeilnehmerListAdapter mTeilnehmerListAdapter;
    private Button button2;
    private Button button7;
    private Button button3;
    private Button button6;

    private String uebergebeneID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teilnehmerlist);

        Intent intent = getIntent();
        uebergebeneID = intent.getStringExtra("ID");

        databaseEventteilnehmer = new Firebase(FIREBASE_URL).child("eventteilnehmer").child(uebergebeneID);
        databaseProfiles = new Firebase(FIREBASE_URL).child("profiles");

        button7 = (Button) findViewById(R.id.button7);
        button3 = (Button) findViewById(R.id.button3);
        button6 = (Button) findViewById(R.id.button6);
        button2 = (Button) findViewById(R.id.button2);

        button7.setOnClickListener(this);
        button3.setOnClickListener(this);
        button6.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    public void onStart() {
        super.onStart();
        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes
        final ListView listView = getListView();
        // Tell our list adapter that we only want 50 messages at a time
        mTeilnehmerListAdapter = new TeilnehmerListAdapter(databaseEventteilnehmer.limit(50), this, R.layout.activity_teilnehmerlist_element);
        listView.setAdapter(mTeilnehmerListAdapter);
        mTeilnehmerListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(mTeilnehmerListAdapter.getCount() - 1);
            }
        });

        // Finally, a little indication of connection status
        mConnectedListener = databaseEventteilnehmer.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    //Toast.makeText(Eventliste_activity.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(teilnehmerlist.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // No-op
            }
        });

/*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                Event item = (Event) parent.getItemAtPosition(position);
                Intent details = new Intent (teilnehmerlist.this, .class);
                details.putExtra("ID", item.getEventID());
                startActivity(details);
            }
        });*/

    }

    @Override
    public void onStop() {
        super.onStop();
        //databaseEventteilnehmer.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
       // mTeilnehmerListAdapter.cleanup();
    }



        //teilnehmerlisteAnzeigen();

/*
    public void teilnehmerlisteAnzeigen (){

        DatabaseReference orgateilnehmer = databaseEventteilnehmer.child(Event.aktuelleEventID).child("organisator");
        orgateilnehmer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                textView46.setText(name);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

*/


// folgende Methode noch unverwendet und noch nicht fertig
/*
    public String getName (String teilnehmerID){

        DatabaseReference vornameWert = databaseProfiles.child(teilnehmerID).child("vorname");
        vornameWert.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String vorname = dataSnapshot.getValue(String.class);
                teilnehmerlist.vollerTeilnehmerName = teilnehmerlist.vollerTeilnehmerName+vorname;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        DatabaseReference nachnameWert = databaseProfiles.child(teilnehmerID).child("name");
        nachnameWert.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                teilnehmerlist.vollerTeilnehmerName= teilnehmerlist.vollerTeilnehmerName+" "+name;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return  teilnehmerlist.vollerTeilnehmerName;

    }*/

    @Override
    public void onClick(View view) {
        if (view == button2) {
            Intent Profil = new Intent(teilnehmerlist.this, profilansicht.class);
            startActivity(Profil);
        }
        if (view == button7) {
            Intent Walk = new Intent(teilnehmerlist.this, Eventliste_activity.class);
            startActivity(Walk);
        }
        if (view == button3) {
            Intent Map = new Intent(teilnehmerlist.this, map.class);
            startActivity(Map);
        }
        if (view == button6) {
            Intent Kontakte = new Intent(teilnehmerlist.this, kontakte.class);
            startActivity(Kontakte);
        }

    }
}
