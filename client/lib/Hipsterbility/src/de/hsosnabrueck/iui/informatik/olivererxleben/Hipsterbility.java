package de.hsosnabrueck.iui.informatik.olivererxleben;

import android.app.Activity;
import android.app.AlertDialog;
import org.json.JSONObject;

/**
 * The Hipsterbility class is a monolithic wrapper for the Hipsterbility-library and implements all public methods which
 * a user can use in own application.
 */
public class Hipsterbility {

    //================================================================================
    // Properties
    //================================================================================

    JSONObject conf; // TODO: check object doc

    //================================================================================
    // Constructors
    //================================================================================

    /**
     * The default constructor to create a Hipsterbility object.
     * It uses default values.
     */
    public Hipsterbility() {
        // TODO: implementation
    }

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

    //================================================================================
    // Private Methods
    //================================================================================


}
