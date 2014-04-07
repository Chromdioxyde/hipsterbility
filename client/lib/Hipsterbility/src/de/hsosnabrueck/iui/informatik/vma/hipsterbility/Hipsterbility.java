package de.hsosnabrueck.iui.informatik.vma.hipsterbility;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.User;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.lifecycle.ActivityLifecycleWatcher;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.screencapture.ScreenRecorder;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.screencapture.ScreenshotModule;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.services.CaptureService;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.services.HipsterbilityService;

import java.util.ArrayList;

/**
 * The Hipsterbility class is a monolithic wrapper for the Hipsterbility-library and implements all public methods which
 * a user can use in own application, similar to the facade pattern.
 */
public class Hipsterbility {

    //Base dir for stored files on SD-Card

    private static Hipsterbility instance;
    private ArrayList<Class> enabledActivities;

    private Context context;
    private Application application;
    private Activity activity;

    private boolean rootFeaturesEnabled;

    /**
     * The default constructor to create a Hipsterbility object.
     * It uses default values.
     */

    private Hipsterbility() {
        enabledActivities = new ArrayList<Class>();
    }

    //Modules

    public static Hipsterbility getInstance() {
        if (instance == null) instance = new Hipsterbility();
        return instance;
    }

    public void stopCapture() {
        context.stopService(new Intent(context, CaptureService.class));
        ScreenRecorder.getInstance().stopCapture();
        startService();
    }


    public Hipsterbility enableTesting(Activity activity) {
        //TODO usefull stuff
        this.application = activity.getApplication();
        enabledActivities.add(activity.getClass());
        this.activity = activity;
        this.context = activity.getApplicationContext();
        //TODO remove after testing
        startService();
//        startSession();
        ActivityLifecycleWatcher.getInstance().setApp(application);
//        TODO: remove after testing
        ActivityLifecycleWatcher.getInstance().startCapture();
        return instance;
    }

    private void startService() {
        Intent i = new Intent(context, HipsterbilityService.class);
        context.startService(i);
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void startSession() {
//        testCapture();
//        ScreenshotModule st = new ScreenshotModule(SessionManager.getInstace().getSessionInProgress(), activity);
        Session s = new Session(1);
        User u = new User(1, "", "");
        s.setUser(u);
        Intent i = new Intent();
        context.sendBroadcast(i);
//        UploadManager.getInstance().uploadSessionData(s);
    }

//    public Class getStartActivityClass() {
//        return startActivity;
//    }

//    public void setStartActivityClass(Class startActivity) {
//        this.startActivity = startActivity;
//    }

    public Context getContext() {
        return context;
    }

    public boolean isRootFeaturesEnabled() {
        return rootFeaturesEnabled;
    }

    public void setRootFeaturesEnabled(boolean rootFeaturesEnabled) {
        this.rootFeaturesEnabled = rootFeaturesEnabled;
    }

    public static enum MODULE {
        AUDIO, VIDEO, SCREEN, TOUCH, LIFECYCLE;
        public boolean enabled = false;
    }
}
