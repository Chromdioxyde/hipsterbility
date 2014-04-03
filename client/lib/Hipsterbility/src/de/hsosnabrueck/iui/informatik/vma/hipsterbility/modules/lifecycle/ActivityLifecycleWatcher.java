package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.CaptureModule;

import java.util.ArrayList;

/**
 * Created by Albert Hoffmann on 21.02.14.
 * Sources: http://qtcstation.com/2011/01/getting-info-about-your-currently-running-activities/
 */
public class ActivityLifecycleWatcher implements Application.ActivityLifecycleCallbacks, CaptureModule {

    private final static String TAG = ActivityLifecycleWatcher.class.getName();
    private final static int TIMEOUT_MS = 3000;
    private static ActivityLifecycleWatcher instance;
    private ArrayList<ActivityLifecycleListener> listeners = new ArrayList<ActivityLifecycleListener>();
    private Thread timeoutThread;
    
    private Application app;
//    private ActivityManager activityManager = (ActivityManager) app.getSystemService(Context.ACTIVITY_SERVICE);
    private boolean capturing = false;

    private ActivityLifecycleWatcher(){}

    public static ActivityLifecycleWatcher getInstance(){
        if(instance == null) instance = new ActivityLifecycleWatcher();
        return instance;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        Log.v(TAG, "Activity created: " + activity);
        notifyActivityEvent(new ActivityLifecycleEvent(this, activity), EVENT_TYPE.CREATED);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.v(TAG, "Activity started: " + activity);
        notifyActivityEvent(new ActivityLifecycleEvent(this, activity), EVENT_TYPE.STARTED);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.v(TAG, "Activity resumed: " + activity);
        notifyActivityEvent(new ActivityLifecycleEvent(this, activity), EVENT_TYPE.RESUMED);
        cancelBackgroundTimeout();
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.v(TAG, "Activity paused: " + activity);
        notifyActivityEvent(new ActivityLifecycleEvent(this, activity), EVENT_TYPE.PAUSED);
        startBackgroundTimeout();
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.v(TAG, "Activity stopped: " + activity);
        notifyActivityEvent(new ActivityLifecycleEvent(this, activity), EVENT_TYPE.STOPPED);
        startBackgroundTimeout();
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        Log.v(TAG, "Activity saved instance state: " + activity);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.v(TAG, "Activity destroyed: " + activity);
        // This event will probably not be needed, but it is added for completeness.
        notifyActivityEvent(new ActivityLifecycleEvent(this, activity), EVENT_TYPE.DESTROYED);
    }

    private void startBackgroundTimeout(){
        Log.d(TAG, "Starting timeout thread: " + TIMEOUT_MS);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                    try {
                        Thread.sleep(TIMEOUT_MS);
                        if(!timeoutThread.isInterrupted()){
                            Log.d(TAG, "Stopping capture after timeout");
                            stopCaptureTimeout();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        };
        if(timeoutThread != null){
            timeoutThread.interrupt();
        }
        timeoutThread = new Thread(r);
    }

    private void stopCaptureTimeout() {
        Intent i = new Intent();
        i.setAction(app.getString(R.string.intent_action_stop_capture));
        LocalBroadcastManager.getInstance(this.app).sendBroadcast(i);
    }

    private void cancelBackgroundTimeout() {
        if(this.timeoutThread != null && timeoutThread.isAlive()){
            timeoutThread.interrupt();
        }
    }


/*
    private Activity getCurrentActivity() {
        // get the info from the currently running task
        List<ActivityManager.RunningTaskInfo> taskInfo = activityManager.getRunningTasks(1);

        Log.d("topActivity", "CURRENT Activity ::"
                + taskInfo.get(0).topActivity.getClassName());

        ComponentName componentInfo = taskInfo.get(0).topActivity;
        componentInfo.getPackageName();
        return null;
    }*/

    public Application getApp() {
        return app;
    }

    public void setApp(Application app) {
        this.app = app;
    }

    @Override
    public void startCapture() {
        if(app != null && !capturing) {
            this.app.registerActivityLifecycleCallbacks(this);
            capturing = true;
        }
    }

    @Override
    public void stopCapture() {
        if(app != null){
            this.app.unregisterActivityLifecycleCallbacks(this);
            capturing = false;
        }
    }

    @Override
    public void pauseCapture() {
        stopCapture();
    }

    @Override
    public void resumeCapture() {
        startCapture();
    }

    @Override
    public boolean isCapturing() {
        return capturing;
    }

    @Override
    public void init() {
//        TODO: implement
    }

    public void addActivityLifecycleListener(ActivityLifecycleListener listener){
        this.listeners.add(listener);
    }

    public void removeActivityLifecycleListener(ActivityLifecycleListener listener){
        this.listeners.add(listener);
    }

    protected synchronized void notifyActivityEvent(ActivityLifecycleEvent event, EVENT_TYPE type ){
        for(ActivityLifecycleListener l : listeners){
            switch (type){
                case CREATED:   l.activityCreated(event);   break;
                case STARTED:   l.activityStarted(event);   break;
                case STOPPED:   l.activityStopped(event);   break;
                case RESUMED:   l.activityResumed(event);   break;
                case PAUSED:    l.activityPaused(event);    break;
                case DESTROYED: l.activityDestroyed(event); break;
            }
        }
    }

    private static enum EVENT_TYPE{
        CREATED, STARTED, STOPPED, PAUSED, RESUMED, DESTROYED
    }


}
