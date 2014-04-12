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
 * Created on 17.02.14.
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
        createTestingNotification();
    }


    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO: shutdown Service by intent
        String action = intent.getAction();
        if (action == null) {
            createTestingNotification();
        } else {
            if (action.equals(getString(R.string.action_stop_service))) {
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.cancel(NOTIFICATION_ID);
                this.stopSelf();
            } else if (action.equals(getString(R.string.action_upload_notification))) {
                startService(new Intent(this, UploadService.class));
            }
        }
        Log.d(TAG, "Received intent: " + intent + " flags: " + flags);
        return super.onStartCommand(intent, flags, startId);
    }

    public void createTestingNotification() {
        Intent intent = new Intent(this, SessionActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Intent stopServiceIntent = new Intent(this, HipsterbilityService.class);
        stopServiceIntent.setAction(getString(R.string.action_stop_service));
        PendingIntent pStopIntent = PendingIntent.getService(this, 0, stopServiceIntent, 0);
        notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_main)
                .setContentTitle(getString(R.string.hipsterbility))
                .setContentText(getString(R.string.click_for_testing))
                .setContentIntent(pIntent)
                .addAction(R.drawable.ic_stat_dismiss, getString(R.string.notification_dismiss), pStopIntent)
                .build();
        startForeground(NOTIFICATION_ID, notification);
    }

}
