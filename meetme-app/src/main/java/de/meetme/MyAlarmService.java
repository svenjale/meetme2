package de.meetme;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.media.AudioAttributes;
import android.support.v4.app.NotificationCompat;

import static android.media.AudioAttributes.USAGE_NOTIFICATION_EVENT;

/**
 * Created by kortsch on 07.10.2017.
 */

public class MyAlarmService extends Service {

    private NotificationManager mManager;

    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onCreate() { super.onCreate(); }

    @Override
    public void onDestroy() { super.onDestroy(); }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        super.onStartCommand(intent, flags, startId);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(), eventinfos.class);
        intent1.putExtra("ID",intent.getStringExtra("ID"));
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationCompat.Builder notification;
            String id = "my_channel_01";
            // The user-visible name of the channel.
            CharSequence name = "Photowalk beginnt!";
            // The user-visible description of the channel.
            String description = intent.getStringExtra("name")+" startet um "+intent.getStringExtra("uhrzeit")+" Uhr";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // Configure the notification channel.
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setLockscreenVisibility(1);
            //mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setImportance(4);

            AudioAttributes.Builder audio = new AudioAttributes.Builder();
            audio.setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN);
            audio.setUsage(USAGE_NOTIFICATION_EVENT);
            mChannel.setSound(soundUri, audio.build() );
            mChannel.canShowBadge();
            mManager.createNotificationChannel(mChannel);

            //Notification.Action action = new Notification.Action( 0, "Details anzeigen",pendingNotificationIntent);
            NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.smapicons, "Details anzeigen", pendingNotificationIntent).build();


            notification = new NotificationCompat.Builder(this.getApplicationContext(), id)
                    .setContentTitle("Photowalk beginnt!")
                    .setContentText(intent.getStringExtra("name")+" startet um "+intent.getStringExtra("uhrzeit")+" Uhr")
                    .setSmallIcon(R.drawable.smapicons)
                    .addAction(action)
                    .setContentIntent(pendingNotificationIntent);




            intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //notification.flags |= Notification.FLAG_AUTO_CANCEL;

            mManager.notify(0, notification.build());
            return startId;

        }else{         //-> hier evelt NotificationCompat??
            Notification notification;
            notification = new NotificationCompat.Builder(this.getApplicationContext() )
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentTitle("Photowalk beginnt!")
                    .setContentText(intent.getStringExtra("name")+" startet um "+intent.getStringExtra("uhrzeit")+" Uhr")
                    .setSmallIcon(R.drawable.smapicons)
                    //.setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setSound(soundUri)
                    .setPriority(4)
                    .addAction(0, "Details anzeigen", pendingNotificationIntent)
                    .setContentIntent(pendingNotificationIntent)
                    .build();

            intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            notification.flags |= Notification.FLAG_AUTO_CANCEL;

            mManager.notify(0, notification);
            return startId;

        }


    }

}
