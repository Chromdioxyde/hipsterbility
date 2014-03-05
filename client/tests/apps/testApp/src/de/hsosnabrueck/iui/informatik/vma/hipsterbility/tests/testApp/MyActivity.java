package de.hsosnabrueck.iui.informatik.vma.hipsterbility.tests.testApp;

import android.app.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.android.rssfeed.RssfeedActivity;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.Hipsterbility;

/**
 * Sources: http://www.vogella.com/tutorials/AndroidDialogs/article.html
 */
public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    // constant for identifying the dialog
    private static final int DIALOG_ALERT = 10;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        h.testAlert(this);
//        h.testCapture();
        addListenerOnButton();
        // Enable UX testing for this activity
        Hipsterbility.getInstance().enableTesting(this).setStartActivityClass(MyActivity.class);

    }


    public void addListenerOnButton() {

        //Select a specific button to bundle it with the action you want
        Button listViewButton = (Button) findViewById(R.id.button_open_list_view);
        listViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               launchActivity(MyListActivity.class);
            }
        });
        Button rssButton = (Button) findViewById(R.id.button_open_rss_feed);
        rssButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchActivity(RssfeedActivity.class);
            }
        });
        Button alertDialogButton = (Button) findViewById(R.id.button_show_alert_dialog);
        alertDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_ALERT);
            }
        });
    }

    private void launchActivity(Class aClass){
        Intent i = new Intent(this, aClass);
        startActivity(i);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_ALERT:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("This ends the activity");
                builder.setCancelable(true);
                builder.setPositiveButton("I agree", new OkOnClickListener());
                builder.setNegativeButton("No, no", new CancelOnClickListener());
                AlertDialog dialog = builder.create();
                dialog.show();
        }
        return super.onCreateDialog(id);
    }

    private final class CancelOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getApplicationContext(), "Cancle selected, activity continues",
                    Toast.LENGTH_LONG).show();
        }
    }

    private final class OkOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            MyActivity.this.finish();
        }
    }
}
