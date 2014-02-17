package de.hsosnabrueck.iui.informatik.olivererxleben;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.sessions.Session;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.CaptureService;
import org.json.JSONObject;

/**
 * The Hipsterbility class is a monolithic wrapper for the Hipsterbility-library and implements all public methods which
 * a user can use in own application.
 */
public class Hipsterbility extends Service{

    //================================================================================
    // Properties
    //================================================================================

    JSONObject conf; // TODO: check object doc
    Activity activity; // Calling activity to get context for Service TODO: check if needed later on
    Context context;

    //================================================================================
    // Constructors
    //================================================================================

    /**
     * The default constructor to create a Hipsterbility object.
     * It uses default values.
     */
    public Hipsterbility() {
        Notification.Builder mBuilder = new Notification.Builder(this);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public Hipsterbility(Activity activity){
        this.activity = activity;
        context = activity.getApplication().getApplicationContext();
    };

    /**
     * Parameterized constructor to create a Hipsterbility object.
     * it evaluates a json object for configuration. For example
     * @param conf : JSONObject
     */
    public Hipsterbility(JSONObject conf) {
        // TODO: implementation
    }

    //================================================================================
    // public Methods
    //================================================================================


    public String test() {
       // TODO: delete method, just for dev testing purposes
       return "hello Hipsterbility";
    }

    public void testAlert(Activity activity) {
        // TODO: delete method, just for dev testing purposes
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setMessage("Alert from lib")
                .setTitle(this.test());

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    public void testCapture() {

        // use this to start and trigger a service

        Session session = new Session("123");
        Intent i= new Intent(context, CaptureService.class);
        i.putExtra("Session", session);
        // potentially add data to the intent
        //i.putExtra("KEY1", "Value to be used by the service");
        context.startService(i);
    }

    public void stopCapture() {
        context.stopService(new Intent(context, CaptureService.class));
    }

    //================================================================================
    // Private Methods
    //================================================================================


}
