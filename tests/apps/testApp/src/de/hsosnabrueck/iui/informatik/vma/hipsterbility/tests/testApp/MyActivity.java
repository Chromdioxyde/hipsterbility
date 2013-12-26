package de.hsosnabrueck.iui.informatik.vma.hipsterbility.tests.testApp;

import android.app.Activity;

import android.os.Bundle;

import de.hsosnabrueck.iui.informatik.olivererxleben.Hipsterbility;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Hipsterbility h = new Hipsterbility();

        h.testAlert(this);


    }
}
