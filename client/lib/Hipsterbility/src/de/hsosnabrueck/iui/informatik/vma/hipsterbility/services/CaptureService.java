package de.hsosnabrueck.iui.informatik.vma.hipsterbility.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.CaptureModule;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.CaptureModuleFactory;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.lifecycle.ActivityLifecycleWatcher;

import java.util.ArrayList;

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

//    private BroadcastReceiver startCaptureReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
////            TODO: start capture
//        }
//    };
//    private BroadcastReceiver stopCaptureReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
////            TODO: stop capture
//            stopCapture();
//        }
//    };
//    private BroadcastReceiver pauseCaptureReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
////            TODO: pauseCapture
//        }
//    };
//    private BroadcastReceiver resumeCaptureReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
////            TODO: resume capture
//        }
//    };


    public CaptureService() {
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
        createNotification(false);
//        registerLocalBroadcastReceivers();
        createCaptureModules();
        startCapture();
    }

    private void startCapture() {
        ActivityLifecycleWatcher.getInstance().startCapture();
        if(modules == null) {
            Log.e(TAG, "Module list is empty");
            return;
        }
        for(CaptureModule m :modules){
            Log.d(TAG, "Starting module: " + m);
            m.init();
            m.startCapture();
        }
    }

    private void stopCapture() {
        for(CaptureModule m :modules){
            m.stopCapture();
        }
        dismissNotification();
        Intent i = new Intent(this, HipsterbilityService.class);
        i.setAction(getString(R.string.action_upload_notification));
        startService(i);
        stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        Log.d(TAG, "Recieved Intent: " + intent + " Action: "+ action);
        if(action != null){
            if(action.equals(getString(R.string.intent_action_stop_capture))){
                stopCapture();
            }
        }

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

    private void createNotification(boolean paused) {
        Intent stopIntent = new Intent(this, this.getClass());
        stopIntent.setAction(getString(R.string.intent_action_stop_capture));
        PendingIntent pIntentStop = PendingIntent.getService(this, 0, stopIntent, 0);
        PendingIntent pIntentPauseResume;
        Intent pauseResumeIntent = new Intent(this, this.getClass());
        int icon;
        if(paused){
            icon = android.R.drawable.ic_media_play;
            pauseResumeIntent.setAction(getString(R.string.intent_action_resume_capture));
        } else {
            icon = android.R.drawable.ic_media_pause;
            pauseResumeIntent.setAction(getString(R.string.intent_action_pause_capture));
        }
        pIntentPauseResume = PendingIntent.getService(this, 0, pauseResumeIntent, 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("Background Video Recorder")
                .setContentText("")
                .setSmallIcon(R.drawable.ic_notification_recording)
                .addAction(R.drawable.ic_action_stop_recording, "Stop", pIntentStop)
                .addAction(icon, "Pause", pIntentPauseResume)
                .setPriority(Notification.FLAG_FOREGROUND_SERVICE)
                .build();
        startForeground(NOTIFICATION_ID, notification);
    }

    private void dismissNotification(){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
    }

    private void createCaptureModules() {
        this.modules = CaptureModuleFactory.getCaptureModules();
    }

//    private void registerLocalBroadcastReceivers() {
//        registerLocalBroadcastReceiver(getString(R.string.intent_action_start_capture), startCaptureReceiver);
//        registerLocalBroadcastReceiver(getString(R.string.intent_action_stop_capture), stopCaptureReceiver);
//        registerLocalBroadcastReceiver(getString(R.string.intent_action_pause_capture), pauseCaptureReceiver);
//        registerLocalBroadcastReceiver(getString(R.string.intent_action_resume_capture), resumeCaptureReceiver);
//    }
//
//    private void registerLocalBroadcastReceiver(String intentFilter, BroadcastReceiver receiver) {
//        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,
//                new IntentFilter(intentFilter)
//        );
//    }
}
