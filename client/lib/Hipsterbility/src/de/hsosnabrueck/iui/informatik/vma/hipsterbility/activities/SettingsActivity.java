package de.hsosnabrueck.iui.informatik.vma.hipsterbility.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import de.hsosnabrueck.iui.informatik.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.rest.HipsterbilityRestClient;

import java.util.prefs.PreferenceChangeListener;

/**
 * Created by Albert Hoffmann on 26.02.14.
 */
public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences prefs;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
//        TODO: change to non-deprecated Methods
        addPreferencesFromResource(R.xml.settings);
        Preference button = findPreference(getString(R.string.pref_key_button_test_server));
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
        SharedPreferences.Editor editor = prefs.edit();
        String server = HipsterbilityRestClient.testConnectionToServer();
        editor.putString(getString(R.string.pref_key_server),server);
        editor.commit();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_key_server)) || key.equals(R.string.pref_key_port)) {
//            Preference serverPref = findPreference(key);
            // Set summary to be the user-description for the selected value
//            serverPref.setSummary(sharedPreferences.getString(key, ""));
            HipsterbilityRestClient.setServer(
                    prefs.getString(getString(R.string.pref_key_server),""),
                    Integer.valueOf(prefs.getString(getString(R.string.pref_key_port), "3000")));
        }
    }
}