package de.hsosnabrueck.iui.informatik.vma.hipsterbility.tests.testApp;

import android.app.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import de.hsosnabrueck.iui.informatik.olivererxleben.Hipsterbility;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    private Hipsterbility h;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.h = new Hipsterbility(this);

        h.testAlert(this);
        h.testCapture();
        addListenerOnButton();


    }


    public void addListenerOnButton() {

        //Select a specific button to bundle it with the action you want
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                h.stopCapture();
            }

        });

    }
}
