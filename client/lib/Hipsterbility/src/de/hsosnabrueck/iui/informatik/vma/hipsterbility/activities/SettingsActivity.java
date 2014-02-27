package de.hsosnabrueck.iui.informatik.vma.hipsterbility.activities;

import android.app.Activity;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import de.hsosnabrueck.iui.informatik.R;

/**
 * Created by Albert Hoffmann on 26.02.14.
 */
public class SettingsActivity extends PreferenceActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        TODO: change to non-deprecated Methods
        addPreferencesFromResource(R.xml.settings);
        Preference button = findPreference("pref_key_button_discover_server");
        assert button != null;
        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference arg0) {
                discoverServer();
                return true;
            }
        });
    }

    private void discoverServer() {

    }
}