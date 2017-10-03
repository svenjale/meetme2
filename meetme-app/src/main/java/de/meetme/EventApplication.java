package de.meetme;

import com.firebase.client.Firebase;

/**
 * @author Jenny Tong (mimming)
 * @since 12/5/14
 *
 * Initialize Firebase with the application context. This must happen before the client is used.
 */
public class EventApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
