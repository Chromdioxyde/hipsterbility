package de.hsosnabrueck.iui.informatik.vma.hipsterbility.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.Hipsterbility;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.HipsterbilityBroadcastReceiver;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.CaptureModule;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.CaptureModuleFactory;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Albert Hoffmann on 13.02.14.
 */
public class CaptureService extends Service {

    //<editor-fold desc="Constants">
    private static final String TAG = CaptureService.class.getName();
    private static final int NOTIFICATION_ID = 2;
    //</editor-fold>

    //<editor-fold desc="Private Properties">
    private ArrayList<CaptureModule> modules;
    private WindowManager windowManager;
    private ImageView overlayIcon;
    //</editor-fold>


    public CaptureService(){
        this.modules = new ArrayList<CaptureModule>();
    }

    @Override
    public void onCreate() {
        super.onCreate();

       /* Create an overlaying ImageView to signal recording in progress to the user */
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        overlayIcon = new ImageView(this);
        overlayIcon.setImageResource(R.drawable.ic_overlay_rec);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.alpha = 50;
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        params.x = 0;
        params.y = 100;

        windowManager.addView(overlayIcon, params);

        /* Start foreground service to avoid unexpected killing of the service */
        createNotification();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if (overlayIcon != null) windowManager.removeView(overlayIcon);
        super.onDestroy();
    }

    private void createNotification(){
        Intent stopIntent = new Intent();
        stopIntent.setAction(HipsterbilityBroadcastReceiver.ACTION_STOP_CAPTURE);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, stopIntent, 0);

        Intent pauseIntent = new Intent();
//        pauseIntent.setAction(HipsterbilityBroadcastReceiver.)
        Notification notification = new Notification.Builder(this)
                .setContentTitle("Background Video Recorder")
                .setContentText("")
                .setSmallIcon(R.drawable.ic_notification_recording)
                .addAction(R.drawable.ic_action_stop_recording, "Stop Recording", pIntent)
                .setPriority(Notification.FLAG_FOREGROUND_SERVICE)
                .build();
        startForeground(NOTIFICATION_ID, notification);
    }

    private void createCaptureModules(){
        this.modules = CaptureModuleFactory.getCaptureModules();
    }
}
