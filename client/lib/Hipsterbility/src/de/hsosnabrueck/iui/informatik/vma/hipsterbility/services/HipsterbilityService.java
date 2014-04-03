package de.hsosnabrueck.iui.informatik.vma.hipsterbility.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.activities.SessionActivity;

/**
 * Created by Albert Hoffmann on 17.02.14.
 * Service class for managing usability testing.
 */
public class HipsterbilityService extends Service {

    private static final String TAG = HipsterbilityService.class.getName();

    private static final int NOTIFICATION_ID = 1;

    private Context context;
    private SharedPreferences prefs;
    private Notification notification;

    public HipsterbilityService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //TODO: remove after testing
//        startActivity(intent);
        createNotification();
    }


    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO: shutdown Service by intent
        Log.d(TAG, "Received intent: " + intent + " flags: " + flags);
        if (intent.getBooleanExtra("shutdown", false)) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.cancel(NOTIFICATION_ID);
            this.stopSelf();
        } else {
            createNotification();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public void createNotification() {
        Intent intent = new Intent(this, SessionActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Intent stopServiceIntent = new Intent(this, HipsterbilityService.class);
        stopServiceIntent.putExtra("shutdown", true);
        PendingIntent pStopIntent = PendingIntent.getService(this, 0, stopServiceIntent, 0);
        notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_main)
                .setContentTitle("Hipsterbility")
                .setContentText("Usability testing enabled")
                .setContentIntent(pIntent)
//                .addAction(android.R.drawable.ic_media_play, "Start testing", pIntent)
                .addAction(R.drawable.ic_stat_dismiss, "Dismiss", pStopIntent)
                .build();
        startForeground(1234, notification);
    }

}
