package de.hsosnabrueck.iui.informatik.vma.hipsterbility;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.ScreenRecorder;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.ScreenshotTaker;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.services.CaptureService;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.services.HipsterbilityService;

/**
 * The Hipsterbility class is a monolithic wrapper for the Hipsterbility-library and implements all public methods which
 * a user can use in own application, similar to the facade pattern.
 */
public class Hipsterbility extends Application {



//    Activity activity; // Calling activity to get context for Service TODO: check if needed later on
    private static Hipsterbility instance = new Hipsterbility();
    //Base dir for stored files on SD-Card
    public static final String BASE_DIR = "hipsterbility";

    //TODO: get this from server, somehow
    public static final int USER_ID = 1;

    private Context context;
    private Activity activity;
    private SharedPreferences sharedPreferences;
    private Class startActivity;

    /**
     * The default constructor to create a Hipsterbility object.
     * It uses default values.
     */

    private Hipsterbility(){
        //TODO: remove after testing
    }



    public static Hipsterbility getInstance(){
        return instance;
    }

    public String test() {
       // TODO: delete method, just for dev testing purposes
       return "hello Hipsterbility";
    }


    //TODO: delete me after testing
    public void testCapture() {

        // use this to start and trigger a service

        Session session = new Session(1);
        Intent i= new Intent(context, CaptureService.class);
        i.putExtra("session_id", session.getId());
//        context.startService(i);
        ScreenRecorder.getInstance().startRecording(session.getId());
//        testScreenshot(activity);
    }

    public void stopCapture() {
        context.stopService(new Intent(context, CaptureService.class));
        ScreenRecorder.getInstance().stopRecording();
    }


    public Hipsterbility enableTesting(Activity activity) {
        //TODO usefull stuff
        this.activity = activity;
        this.context = activity.getApplicationContext();
        //TODO remove after testing
//        testScreenshot(activity);
//        activity.startActivity(new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
        //startService();
//        testCapture();
        ScreenshotTaker st = new ScreenshotTaker(new Session(1), activity);
        return instance;
    }


    private void startService(){
        Intent i= new Intent(context, HipsterbilityService.class);
        context.startService(i);
    }


    private void testScreenshot(Activity activity) {
        Session session = new Session(124);
        ScreenshotTaker s = new ScreenshotTaker(session, activity);
//        s.takeScreenshot(activity);
//        s.takeScreenshotRoot();
        s.takeContinuousScreenshots(activity, 10, 100, false);
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void startSession() {
        startService();
    }

    public void setStartActivityClass(Class startActivity) {
        this.startActivity = startActivity;
    }

    public Class getStartActivityClass() {
        return startActivity;
    }
}
