package de.hsosnabrueck.iui.informatik.vma.hipsterbility.activities;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.loopj.android.http.JsonHttpResponseHandler;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.Hipsterbility;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.activities.adapters.TestEntityAdapter;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.activities.fragments.StartSessionDialogFragment;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.model.TestEntity;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.persistence.DatabaseHelper;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.rest.HipsterbilityRestClient;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.services.CaptureService;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.sessions.SessionManager;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.tests.TestManager;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 17.02.14.
 * Sources: http://developer.android.com/guide/topics/ui/layout/listview.html
 * http://www.vogella.com/tutorials/AndroidListView/article.html
 */
public class TestsActivity extends OrmLiteBaseActivity<DatabaseHelper> implements AdapterView.OnItemClickListener, StartSessionDialogFragment.StartSessionDialogListener {

    private final static String TAG = TestsActivity.class.getName();
    private boolean testChosen = false;
    private Context context;
    private SharedPreferences prefs;

    private ListView listView;
    private TestManager testManager;
    private SessionManager sessionManager;
    private ArrayList<TestEntity> tests;

    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;
    private TestEntityAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.testManager = TestManager.getInstance();
        this.sessionManager = new SessionManager(this);
        this.context = this;
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        loadSettings();
        ActionBar ab = this.getActionBar();
        if (ab != null) {
            ab.setTitle(getString(R.string.tests_for_user));
            ab.setSubtitle(getString(R.string.choose_test));
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeButtonEnabled(true);
            ab.setIcon(R.drawable.ic_launcher);
        }
        setContentView(R.layout.tests_activity);
        this.listView = (ListView) findViewById(R.id.testslistView);
        // TODO uncomment after testing
//        SessionManager.getInstance().setSessionInProgress(new Session(0));
//        startSession();
//        loadUserIdFromServer();
        testLogin();
    }

    private void loadSettings() {
        HipsterbilityRestClient.setServer(
                prefs.getString(getString(R.string.pref_key_server), ""),
                Integer.valueOf(prefs.getString(getString(R.string.pref_key_port), "3000")),
                prefs.getBoolean(getString(R.string.pref_key_ssl), false));
        HipsterbilityRestClient.setMaxConnections(
                Integer.valueOf(
                        prefs.getString(getString(R.string.pref_key_max_connection), "1")
                )
        );
        HipsterbilityRestClient.setBasicAuthCredentials(
                prefs.getString(getString(R.string.pref_key_user_name),""),
                        prefs.getString(getString(R.string.pref_key_password), "")
                );
        Hipsterbility.setRootFeaturesEnabled(
                prefs.getBoolean(getString(R.string.pref_key_enable_root), false)
        );

    }

    private void displayTests() {
        tests = this.testManager.getTests();
        adapter = new TestEntityAdapter(this, tests);
        this.listView.setAdapter(adapter);

        // Add listener to ListView for actions on selected item
        listView.setOnItemClickListener(this);
        listView.setSelector(R.drawable.session_list_selector);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent i = new Intent(this, TasksActivity.class);
        TestManager.activeTest = tests.get(position);
        startActivity(i);
        this.testChosen = true;
    }

    private void testLogin(){
        final String username = prefs.getString(getString(R.string.pref_key_user_name), "");
        final String password = prefs.getString(getString(R.string.pref_key_password), "");
        if (username.equals("") || password.equals("")) {
            Toast.makeText(this, R.string.toast_username_or_password_missing, Toast.LENGTH_LONG).show();
            return;
        }
//        RequestParams params = new RequestParams();
//        params.add("name", username);
//        params.add("password", password);
        HipsterbilityRestClient.get("/users", null, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                Resources res = getResources();
                showProgressDialog(
                        res.getString(R.string.authentication_user),
                        res.getString(R.string.authentication_user_message)
                );
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dismissProgressDialog();
            }

            @Override
            public void onSuccess(JSONObject user) {
                dismissProgressDialog();
                loadTestFromServer();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable e) {
                super.onFailure(statusCode, headers, responseBody, e);
                Log.d(TAG, "GET Sessions request failed: " + e.getMessage());
                dismissProgressDialog();
                showAlertDialog(
                        getString(R.string.alert_title_server_connection_failed),
                        getString(R.string.alert_message_server_connection_failed)
                );
            }

        });
    }


    private synchronized void loadTestFromServer() {

        HipsterbilityRestClient.get("/tests", null, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                Resources res = getResources();
                showProgressDialog(res.getString(R.string.loading_sessions), res.getString(R.string.loading_message));
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dismissProgressDialog();
            }

            @Override
            public void onSuccess(JSONArray tests) {
                Gson gson = HipsterbilityRestClient.getGsonBooleanWorkaround();
                Type listType = new TypeToken<List<TestEntity>>() {
                }.getType();
                List<TestEntity> testsList = gson.fromJson(tests.toString(), listType);
                progressDialog.setIndeterminate(false);
                progressDialog.setMax(testsList.size());
                TestManager.getInstance().setTests(new ArrayList<TestEntity>(testsList));
                dismissProgressDialog();
                displayTests();
                listAdapterNotifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable e) {
                super.onFailure(statusCode, headers, responseBody, e);
                dismissProgressDialog();
                Log.d(TAG, "GET Sessions request failed: " + e.getMessage());
                showAlertDialog(getString(R.string.alert_title_server_connection_failed),
                        getString(R.string.alert_message_server_connection_failed));
            }
        });
    }

    private void stopTesting() {
        alertDialog.dismiss();
        finish();
    }

    private void showAlertDialog(String title, String message) {
        alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.open_settings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openSettings();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        stopTesting();
                    }
                })
                .setIcon(R.drawable.ic_alert_dialog)
                .show();
    }

    private void showProgressDialog(String title, String message) {
        progressDialog = ProgressDialog.show(context, title, message);
        progressDialog.setCancelable(true);
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void openSettings() {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_start_session) {
            if (!testChosen) {
                Toast.makeText(this, getString(R.string.select_test_first), Toast.LENGTH_SHORT)
                        .show();
                // TODO: remove after debug
//                sessionManager.createNewSession();

//                return false;
            }
            showDialog();
//            startSession();
        } else if (id == R.id.action_settings) {
            openSettings();
        } else if (id == R.id.action_reload_sessions) {
            testLogin();
        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    void showDialog() {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = StartSessionDialogFragment.newInstance(TestManager.activeTest.getName());
        newFragment.show(ft, "dialog");
    }

    private synchronized void listAdapterNotifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    private void startSession() {
        Intent intent = new Intent(this, Hipsterbility.getStartActivityClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startService(new Intent(this, CaptureService.class));
        this.startActivity(intent);
        finish();
    }

    @Override
    public void onStartSession(DialogFragment dialog) {
        startSession();
    }
}