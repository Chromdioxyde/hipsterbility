package de.hsosnabrueck.iui.informatik.vma.hipsterbility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.User;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.screencapture.ScreenRecorder;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.screencapture.ScreenshotModule;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.services.CaptureService;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.services.HipsterbilityService;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * The Hipsterbility class is a monolithic wrapper for the Hipsterbility-library and implements all public methods which
 * a user can use in own application, similar to the facade pattern.
 */
public class Hipsterbility {

    private static Hipsterbility instance;
    //Base dir for stored files on SD-Card
    public static final String BASE_DIR = "hipsterbility";

    private ArrayList<Class> enabledActivities;
    private HashSet<MODULE> enabledModules;

    private Context context;
    private Activity activity;
    private Class startActivity;
    private ScreenshotModule screenshotModule;
    private boolean rootFeaturesEnabled;

    public static enum MODULE {
        AUDIO, VIDEO, SCREEN, TOUCH, LIFECYCLE
    }

    //Modules

    /**
     * The default constructor to create a Hipsterbility object.
     * It uses default values.
     */

    private Hipsterbility(){
        enabledActivities = new ArrayList<Class>();
        enabledModules = new HashSet<MODULE>();
    }

    public void stopCapture() {
        context.stopService(new Intent(context, CaptureService.class));
        ScreenRecorder.getInstance().stopCapture();
        startService();
    }


    public Hipsterbility enableTesting(Activity activity) {
        //TODO usefull stuff
        enabledActivities.add(activity.getClass());
        this.activity = activity;
        this.context = activity.getApplicationContext();
        //TODO remove after testing
        startService();
//        startSession();
        return instance;
    }

    private void startService(){
        Intent i= new Intent(context, HipsterbilityService.class);
        context.startService(i);
    }

    public static Hipsterbility getInstance(){
        if(instance == null) instance = new Hipsterbility();
        return instance;
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
        i.setAction(HipsterbilityBroadcastReceiver.ACTION_START_CAPTURE);
        context.sendBroadcast(i);
//        UploadManager.getInstance().uploadSessionData(s);
    }

    public void setStartActivityClass(Class startActivity) {
        this.startActivity = startActivity;
    }

    public Class getStartActivityClass() {
        return startActivity;
    }

    public ScreenshotModule getScreenshotModule() {
        return screenshotModule;
    }

    public void setScreenshotModule(ScreenshotModule screenshotModule) {
        this.screenshotModule = screenshotModule;
    }

    protected ArrayList<Class> getEnabledActivities() {
        return enabledActivities;
    }

    protected void setEnabledActivities(ArrayList<Class> enabledActivities) {
        this.enabledActivities = enabledActivities;
    }

    public void enableModule(MODULE module){
        enabledModules.add(module);
    }

    public void disableModule(MODULE module){
        enabledModules.remove(module);
    }

    public HashSet<MODULE> getEnabledModules() {
        return enabledModules;
    }

    public Context getContext() {
        return context;
    }

    public boolean isRootFeaturesEnabled() {
        return rootFeaturesEnabled;
    }

    public void setRootFeaturesEnabled(boolean rootFeaturesEnabled) {
        this.rootFeaturesEnabled = rootFeaturesEnabled;
    }

}
