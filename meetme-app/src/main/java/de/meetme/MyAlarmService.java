package de.meetme;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;

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
        mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(), eventinfos.class);
        Notification notification = new Notification.Builder(this.getApplicationContext()).
                setContentTitle("Texter").setContentText("OK").setSmallIcon(R.drawable.camera).build();
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        mManager.notify(0, notification);

        return startId;

    }

}
