package de.hsosnabrueck.iui.informatik.olivererxleben;

import android.app.Activity;
import android.app.AlertDialog;

import static android.app.PendingIntent.getActivity;

/**
 * Created by olivererxleben on 25.12.13.
 */
public class Hipsterbility {

    public String test() {
       return "hello Hipsterbility";
    }

    public void testAlert(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("Alert from lib")
                .setTitle(this.test());

// 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();

        dialog.show();
    }

}
