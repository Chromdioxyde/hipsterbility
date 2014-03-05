package de.hsosnabrueck.iui.informatik.vma.hipsterbility.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import de.hsosnabrueck.iui.informatik.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.Hipsterbility;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.activities.SessionActivity;

/**
 * Created by Albert Hoffmann on 17.02.14.
 * Service class for managing usability testing.
 */
public class HipsterbilityService extends Service {

    Context context;
    SharedPreferences prefs;
    SharedPreferences sharedPref ;

    public HipsterbilityService() {
        context = Hipsterbility.getInstance().getContext();
//        prefs = context.getSharedPreferences(Hipsterbility.PREFS_NAME,MODE_PRIVATE);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(Hipsterbility.getInstance().getContext());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //TODO: remove after testing
//        startActivity(intent);
        createNotification();
    }



    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO: shudown Service by intent
        if(intent.getBooleanExtra("shutdown", false)){
            this.stopSelf();
        }
        createNotification();
        return super.onStartCommand(intent, flags, startId);
    }

    public void createNotification(){
        Intent intent = new Intent(this, SessionActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Intent stopServiceIntent = new Intent(this, HipsterbilityService.class);
        stopServiceIntent.putExtra("shutdown", true);
        PendingIntent pStopIntent = PendingIntent.getActivity(this, 0, stopServiceIntent, 0);
        Notification n = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Hipsterbility")
                .setContentText("Usability testing enabled")
                        //TODO: add custom icon
                .addAction(android.R.drawable.ic_media_play, "Start testing", pIntent)
                .addAction(android.R.drawable.ic_delete, "Dismiss", pStopIntent)
                .build();
        startForeground(1234, n);
    }



}
