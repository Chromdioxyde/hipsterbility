package de.hsosnabrueck.iui.informatik.vma.hipsterbility.tests.testApp;

import android.app.Activity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.Hipsterbility;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */



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
        Button button = (Button) findViewById(R.id.button);

    }
}
