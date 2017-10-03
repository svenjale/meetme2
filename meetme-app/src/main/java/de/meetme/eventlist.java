package de.meetme;

import android.app.ListActivity;
import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.firebase.client.Firebase;
//import com.firebase.client.ValueEventListener;



import com.google.android.gms.tasks.OnCompleteListener;

import java.util.Random;


public class eventlist extends ListActivity {

    // TODO: change this to your own Firebase URL
    private static final String FIREBASE_URL = "https://smap-dhbw2.firebaseio.com/";

    private String mEventname;
    private DatabaseReference mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private EventListAdapter mEventListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventlist);

        // Make sure we have a mUsername
       // setupUsername();

      //  setTitle("Chatting as " + mEventname);

        // Setup our Firebase mFirebaseRef
        mFirebaseRef = FirebaseDatabase.getInstance().getReference("events");

/*
        // Setup our input methods. Enter key on the keyboard or pushing the send button
        EditText inputText = (EditText) findViewById(R.id.messageInput);
        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    sendMessage();
                }
                return true;
            }
        });

        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
*/
    }

    @Override
    public void onStart() {

        super.onStart();
        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes
        //final ListView listView = getListView();
        final ListView listView = getListView();
        // Tell our list adapter that we only want 50 messages at a time
        //mEventListAdapter = new EventListAdapter(mFirebaseRef.limit(50), this, R.layout.activity_eventinfos, mEventname);
        //mEventListAdapter = new EventListAdapter(mFirebaseRef, this, R.layout.activity_eventinfos, mEventname);
        listView.setAdapter(mEventListAdapter);
        mEventListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(mEventListAdapter.getCount() - 1);
            }
        });


        /*
        // Finally, a little indication of connection status
        mConnectedListener = mFirebaseRef.getRoot().child(".events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    Toast.makeText(eventlist.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(eventlist.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                //No-op
            }

        });
        */
    }
/*
    @Override
    public void onStop() {
        super.onStop();
        mFirebaseRef.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
        mEventListAdapter.cleanup();
    }

    private void setupUsername() {
        SharedPreferences prefs = getApplication().getSharedPreferences("ChatPrefs", 0);
        mUsername = prefs.getString("username", null);
        if (mUsername == null) {
            Random r = new Random();
            // Assign a random user name if we don't have one saved.
            mUsername = "JavaUser" + r.nextInt(100000);
            prefs.edit().putString("username", mUsername).commit();
        }
    }

    private void sendMessage() {
        EditText inputText = (EditText) findViewById(R.id.messageInput);
        String input = inputText.getText().toString();
        if (!input.equals("")) {
            // Create our 'model', a Chat object
            Chat chat = new Chat(input, mUsername);
            // Create a new, auto-generated child of that chat location, and save our chat data there
            mFirebaseRef.push().setValue(chat);
            inputText.setText("");
        }
    }
    */
}














/* alte Eventlist Klasse

public class eventlist extends Activity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseEventteilnehmer;
    private DatabaseReference databaseProfiles;
    private DatabaseReference databaseEvents;

    private Button button2;
    private Button button7;
    private Button button3;
    private Button button6;
    private Button button13;

    private TextView textView38;
    private TextView textView39;
    private TextView textView40;
    private TextView textView41;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventlist);

        databaseEventteilnehmer = FirebaseDatabase.getInstance().getReference("eventteilnehmer");
        databaseEvents = FirebaseDatabase.getInstance().getReference("events");
        databaseProfiles = FirebaseDatabase.getInstance().getReference("profiles");

        button7 = (Button) findViewById(R.id.button7);
        button3 = (Button) findViewById(R.id.button3);
        button6 = (Button) findViewById(R.id.button6);
        button2 = (Button) findViewById(R.id.button2);
        button13 = (Button) findViewById(R.id.button13);

        textView38 = (TextView) findViewById(R.id.textView38);
        textView39 = (TextView) findViewById(R.id.textView39);
        textView40 = (TextView) findViewById(R.id.textView40);
        textView41 = (TextView) findViewById(R.id.textView41);

        button7.setOnClickListener(this);
        button3.setOnClickListener(this);
        button6.setOnClickListener(this);
        button2.setOnClickListener(this);
        button13.setOnClickListener(this);
        textView38.setOnClickListener(this);
        textView39.setOnClickListener(this);
        textView40.setOnClickListener(this);
        textView41.setOnClickListener(this);

        eventlisteAnzeigen();

    }

    @Override
    public void onClick(View view) {
        if (view == button2) {
            Intent Profil = new Intent(eventlist.this, profilansicht.class);
            startActivity(Profil);
        }
        if (view == button7) {
            Intent Walk = new Intent(eventlist.this, eventlist.class);
            startActivity(Walk);
        }
        if (view == button3) {
            Intent Map = new Intent(eventlist.this, map.class);
            startActivity(Map);
        }
        if (view == button6) {
            Intent Kontakte = new Intent(eventlist.this, kontakte.class);
            startActivity(Kontakte);
        }
        if (view == button13) {
             Intent Plus = new Intent(eventlist.this, createevent.class);            //Plus = Hinzufügen Button
            startActivity(Plus);
        }
        if (view == textView38) {
            Event.aktuelleEventID="-Kv6lECK3OPqzlTWeHbl";
            Intent switchregisintent = new Intent(eventlist.this, eventinfos.class);
            startActivity(switchregisintent);
        }
        if (view == textView39) {
            Event.aktuelleEventID="-Kv6lwML4oJXzqfRZhft";
            Intent switchregisintent = new Intent(eventlist.this, eventinfos.class);
            startActivity(switchregisintent);
        }
        if (view == textView40) {
            Event.aktuelleEventID="-Kv6mPzkyxY0ihX5pBAc";
            Intent switchregisintent = new Intent(eventlist.this, eventinfos.class);
            startActivity(switchregisintent);
        }
        if (view == textView41) {
            Intent switchregisintent = new Intent(eventlist.this, eventinfos.class);
            startActivity(switchregisintent);
        } // hier drüber löschen!! nur demmo fake
    }

    public void eventlisteAnzeigen (){

// statisches Beispiel mit drei Events

        DatabaseReference event1 = databaseEvents.child("-Kv6lECK3OPqzlTWeHbl").child("eventname");
        event1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                textView38.setText(name);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        DatabaseReference event2 = databaseEvents.child("-Kv6lwML4oJXzqfRZhft").child("eventname");
        event2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                textView39.setText(name);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        DatabaseReference event3 = databaseEvents.child("-Kv6mPzkyxY0ihX5pBAc").child("eventname");
        event3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                textView40.setText(name);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        DatabaseReference demo = databaseEvents.child(Event.aktuelleEventID).child("eventname");
        demo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                textView41.setText(name);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}

*/
