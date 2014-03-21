package de.hsosnabrueck.iui.informatik.vma.hipsterbility.activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;
import com.loopj.android.http.TextHttpResponseHandler;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.helper.Util;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.rest.HipsterbilityRestClient;

/**
 * Created by Albert Hoffmann on 26.02.14.
 */
public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences prefs;
    ProgressDialog progressDialog;


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
                testServerConnection();
                return true;
            }
        });
        Preference rootFeatures = findPreference(getString(R.string.pref_key_enable_root));
        if(rootFeatures!=null && !Util.isDeviceRooted()){
            rootFeatures.setEnabled(false);
        }
    }

    private void testServerConnection() {
        showProgressDialog();
        HipsterbilityRestClient.get("ping", null, new TextHttpResponseHandler(){
            @Override
            public void onFailure(String responseBody, Throwable error) {
                super.onFailure(responseBody, error);
                showToast(getString(R.string.msg_server_not_found) + ": " + error.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseBody) {
                super.onSuccess(statusCode, headers, responseBody);
                showToast(getString(R.string.msg_server_found) + " Response from Server: " + statusCode);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dismissProgressDialog();
            }
        });
    }

    private void showToast(String msg){
        Toast.makeText(this, msg,Toast.LENGTH_LONG).show();
    }

    private void showProgressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.setMessage(getString(R.string.settings_progress_testing_server));
        progressDialog.show();
    }

    private void dismissProgressDialog(){
        if(progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String server, port, timeout, retries, connections;
        server = getString(R.string.pref_key_server);
        port = getString(R.string.pref_key_port);
        retries = getString(R.string.pref_key_retries);
        timeout = getString(R.string.pref_key_timeout);
        connections = getString(R.string.pref_key_max_connection);

        if ( key.equals(server) || key.equals(port) ){
            HipsterbilityRestClient.setServer(prefs.getString(server,""),
                    Integer.valueOf(prefs.getString(port, "3000")));
        } else if (
                key.equals(retries) ||
                key.equals(timeout)
                ){
            HipsterbilityRestClient.setMaxRetriesAndTimeout(
                    Integer.valueOf(prefs.getString(retries, "0")),
                    Integer.valueOf(prefs.getString(timeout, "300"))
            );
        } else if (key.equals(connections)){
            HipsterbilityRestClient.setMaxConnections(
                    Integer.valueOf(prefs.getString(connections, "1"))
            );
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        prefs.registerOnSharedPreferenceChangeListener(this);
    }
}