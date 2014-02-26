package de.hsosnabrueck.iui.informatik.vma.hipsterbility.activities;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import de.hsosnabrueck.iui.informatik.R;

/**
 * Created by Albert Hoffmann on 26.02.14.
 */
public class SettingsActivity extends PreferenceActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}