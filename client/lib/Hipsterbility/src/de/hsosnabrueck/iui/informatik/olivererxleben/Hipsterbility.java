package de.hsosnabrueck.iui.informatik.olivererxleben;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.CaptureService;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.HipsterbilityService;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.sessions.*;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The Hipsterbility class is a monolithic wrapper for the Hipsterbility-library and implements all public methods which
 * a user can use in own application.
 */
public class Hipsterbility{

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

    public Hipsterbility(Activity activity){
        super();
        this.activity = activity;
        context = activity.getApplication().getApplicationContext();
        startService();
        createTestData();
    }

    //TODO: delete after testing
    private void createTestData() {
        SessionManager sm = SessionManager.getInstace();
        TodoManager tm = TodoManager.getInstance();
        ArrayList<Session> sessions = new ArrayList<Session>();

        sessions.add(new Session(1, "wurst", "mhhhh, tasty", "Nexus", "bla", true));
        sessions.add(new Session(3, "salat", "vitamins ftw", "Galaxy", "blubb", false));
        sessions.add(new Session(5, "foo", "poop! i did it again", "Nexus", "blubb", true));
        sessions.add(new Session(2 ,"bar", "vodka lemon", "HTC", "blubb", false));
        sessions.add(new Session(25, "baz", "more bass digga", "Dumbphone", "bla", true));
        sessions.add(new Session(13, "42", "The answer to the question of quesions", "Nexus", "bla", true));
        sessions.add(new Session(34, "baz", "more bass digga", "Dumbphone", "bla", false));
        sessions.add(new Session(4, "baz", "more bass digga", "Dumbphone", "bla", true));
        int k = 0;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 10; j++){
                Todo t = new Todo(j++,String.valueOf(Math.random()),String.valueOf(Math.random()),j%2==0,sessions.get(i));
                for(int x = 0; x < 10; x++){
                    t.addTask(new Task(x,"task " + x, t));
                }
                sessions.get(i).addTodo(t);
            }
        }
        sm.setSessions(sessions);

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

    //TODO: delete me after testing
    public void testCapture() {

        // use this to start and trigger a service

        Session session = new Session(123);
        Intent i= new Intent(context, CaptureService.class);
        i.putExtra("Session", session);
        context.startService(i);
    }

    public void stopCapture() {
        context.stopService(new Intent(context, CaptureService.class));
    }


    //================================================================================
    // Private Methods
    //================================================================================

    private void startService(){
        Intent i= new Intent(context, HipsterbilityService.class);
        context.startService(i);
    }
}
