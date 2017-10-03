

// **** alte Klasse, nicht mehr in Benutzung ***


/*
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
