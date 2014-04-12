package de.hsosnabrueck.iui.informatik.vma.hipsterbility;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.lifecycle.ActivityLifecycleWatcher;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.services.HipsterbilityService;

/**
 * The Hipsterbility class is a monolithic wrapper for the Hipsterbility-library and implements all public methods which
 * a user can use in own application, following the facade design pattern.
 */
public class Hipsterbility {


    private static Hipsterbility instance;

    private Application application;
    private Activity activity;
    private Class startActivityClass;

    private boolean rootFeaturesEnabled;

    /**
     * Private constructor for singleton
     */
    private Hipsterbility() {
    }

    /**
     * Lazy static singleton
     *
     * @return Hipsterbility object
     */
    public static Hipsterbility getInstance() {
        if (instance == null) instance = new Hipsterbility();
        return instance;
    }

    /**
     * This enables usability testing for the calling application
     *
     * @param activity starting activity from calling application
     */
    public void enableTesting(Activity activity) {
        this.application = activity.getApplication();
        this.startActivityClass = activity.getClass();
        this.activity = activity;
        startService();
        ActivityLifecycleWatcher.getInstance().setApp(application);
    }

    private void startService() {
        Context context = application.getApplicationContext();
        assert (context != null);
        Intent i = new Intent(context, HipsterbilityService.class);
        context.startService(i);
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public boolean isRootFeaturesEnabled() {
        return rootFeaturesEnabled;
    }

    public void setRootFeaturesEnabled(boolean rootFeaturesEnabled) {
        this.rootFeaturesEnabled = rootFeaturesEnabled;
    }

    public Class getStartActivityClass() {
        return startActivityClass;
    }

    /**
     * Small enumeration to store which features should be enabled.
     * Features are disabled by default.
     */
    public static enum MODULE {
        AUDIO, VIDEO, SCREEN, TOUCH, LIFECYCLE;
        public boolean enabled = false;
    }
}
