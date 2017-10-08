package de.meetme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by kortsch on 07.10.2017.
 */

    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Intent service1 = new Intent(context, MyAlarmService.class);
            String s=intent.getStringExtra("ID");
            service1.putExtra("ID",s);
            service1.putExtra("name",intent.getStringExtra("name"));
            service1.putExtra("uhrzeit",intent.getStringExtra("uhrzeit"));
            context.startService(service1);

        }
    }
