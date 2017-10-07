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
            context.startService(service1);

        }
    }
