package de.hsosnabrueck.iui.informatik.vma.hipsterbility.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import de.hsosnabrueck.iui.informatik.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.Hipsterbility;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.HipsterbilityBroadcastReceiver;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.activities.adapters.SessionListAdapter;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.User;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.rest.HipsterbilityRestClient;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.sessions.SessionManager;
import org.apache.http.Header;
import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Albert Hoffmann on 17.02.14.
 * Sources: http://developer.android.com/guide/topics/ui/layout/listview.html
 *          http://www.vogella.com/tutorials/AndroidListView/article.html
 */
public class SessionActivity extends Activity implements AdapterView.OnItemClickListener {

    private final static String TAG = SessionActivity.class.getName();
    private boolean sessionChosen = false;
    private Context context;
    private SharedPreferences prefs;

    //TODO: sort methods and clean up
    private ListView listView;
    private SessionManager sessionManager;
    private ArrayList<Session> sessions;
    //TODO improve user management
    private User user = new User(1, "albert","wustsalat");
    private AlertDialog alertDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sessionManager = SessionManager.getInstace();
        this.context=this;
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        HipsterbilityRestClient.setServer(
                prefs.getString(getString(R.string.pref_key_server),""),
                Integer.valueOf(prefs.getString(getString(R.string.pref_key_port), "3000")));
        ActionBar ab = this.getActionBar();
        if(ab != null){
            ab.setTitle(getString(R.string.sessions_for_user) + " " + user.getName());
            ab.setSubtitle(getString(R.string.choose_session));
        }
        setContentView(R.layout.session_activity_layout);
        this.listView = (ListView) findViewById(R.id.sessionslistView);

        Log.d(TAG, "User ID = " + user.getId());
        getSessionsFromServer();
    }

    private void displaySessions(){
        sessions = this.sessionManager.getSessions();
        SessionListAdapter adapter = new SessionListAdapter(this, sessions);
        this.listView.setAdapter(adapter);

        // Add listener to ListView for actions on selected item
        listView.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        Session s = sessions.get(position);
        Intent i = new Intent(this, TodosActivity.class);
//        i.putExtra("session",s);
        sessionManager.setSessionInProgress(s);
        startActivity(i);
        this.sessionChosen = true;
        Toast.makeText(getApplicationContext(),
                    "Session id " + s.getId()
                            + " - "
                            + "Name "+ s.getName() , Toast.LENGTH_LONG)
                    .show();
    }

    public void getSessionsFromServer(){

//        if(prefs.getString(SettingsActivity))
        HipsterbilityRestClient.get(user.getId() + "/sessions", null, new JsonHttpResponseHandler() {

            private ProgressDialog progressDialog;

            @Override
            public void onStart() {
                super.onStart();
                Resources res = getResources();
                showProgressDialog(res.getString(R.string.loading_sessions),res.getString(R.string.loading_message));
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dismissProgressDialog();
            }

            @Override
            public void onSuccess(JSONArray sessions) {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(Boolean.class, HipsterbilityRestClient.booleanAsIntAdapter)
                        .registerTypeAdapter(boolean.class, HipsterbilityRestClient.booleanAsIntAdapter)
                        .setPrettyPrinting().create();
                Type listType = new TypeToken<List<Session>>() {
                }.getType();
                List<Session> sessionList = gson.fromJson(sessions.toString(), listType);
                for (Session tempSession : sessionList) {
                    tempSession.setUser(user);
                }
                SessionManager.getInstace().setSessions(new ArrayList<Session>(sessionList));
                dismissProgressDialog();
                displaySessions();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable e) {
                super.onFailure(statusCode, headers, responseBody, e);
                Log.d(TAG, "GET Sessions request failed: " + e.getMessage());
                alertDialog = new AlertDialog.Builder(context)
                        .setTitle(getString(R.string.alert_title_server_connection_failed))
                        .setMessage(getString(R.string.alert_message_server_connection_failed))
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

            private void showProgressDialog(String title, String message){
                progressDialog = ProgressDialog.show(context, title, message);
            }

            private void dismissProgressDialog(){
                if(progressDialog != null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void stopTesting() {
        alertDialog.dismiss();
        finish();
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
            // action with ID action_refresh was selected
            if(!sessionChosen){
                Toast.makeText(this, getString(R.string.select_session_first), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
            Intent intent = new Intent(this, Hipsterbility.getInstance().getStartActivityClass());
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//            Hipsterbility.getInstance().startSession();
            Intent i = new Intent();
            i.setAction(HipsterbilityBroadcastReceiver.ACTION_START_SESSION);
            sendBroadcast(i);
            this.startActivity(intent);
        } else if (id == R.id.action_settings) {
            // action with ID action_settings was selected
//            Toast.makeText(this, "Settings selected", Toas
            openSettings();
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSessionsFromServer();
    }



}