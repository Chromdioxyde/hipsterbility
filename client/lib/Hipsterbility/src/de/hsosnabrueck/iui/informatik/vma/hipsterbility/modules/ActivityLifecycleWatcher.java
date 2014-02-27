package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;

import java.util.List;

/**
 * Created by Albert Hoffmann on 21.02.14.
 * Sources: http://qtcstation.com/2011/01/getting-info-about-your-currently-running-activities/
 */
public class ActivityLifecycleWatcher implements Application.ActivityLifecycleCallbacks {

    private Session session;
    private Application app;
    private ActivityManager activityManager;

    public ActivityLifecycleWatcher(Session session, Application app) {
        this.session = session;
        this.app = app;
        this.app.registerActivityLifecycleCallbacks(this);
        activityManager = (ActivityManager) app.getSystemService(Context.ACTIVITY_SERVICE);
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    private Activity getCurrentActivity(){
        // get the info from the currently running task
        List<ActivityManager.RunningTaskInfo> taskInfo = activityManager.getRunningTasks(1);

        Log.d("topActivity", "CURRENT Activity ::"
                + taskInfo.get(0).topActivity.getClassName());

        ComponentName componentInfo = taskInfo.get(0).topActivity;
        componentInfo.getPackageName();
        return null;
    }
}
