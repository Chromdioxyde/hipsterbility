package de.hsosnabrueck.iui.informatik.vma.hipsterbility.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import de.hsosnabrueck.iui.informatik.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.Hipsterbility;
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

    //TODO: sort methods and clean up
    private ListView listView;
    private SessionManager sessionManager;
    private ArrayList<Session> sessions;
    //TODO improve user management
    private User user = new User(1, "albert","wustsalat");

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sessionManager = SessionManager.getInstace();
        ActionBar ab = this.getActionBar();
        if(ab != null){
            ab.setTitle(getString(R.string.sessions_for_user) + " " + user.getName());
            ab.setSubtitle(getString(R.string.choose_session));
        }
        setContentView(R.layout.session_activity_layout);
        this.listView = (ListView) findViewById(R.id.sessionslistView);

        // Create a progress bar to display while the list loads
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        listView.setEmptyView(progressBar);

        // Must add the progress bar to the root of the layout
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        root.addView(progressBar);


        //TODO: delete after testing
//        sessions = this.sessionManager.getSessions();
//        displaySessions();
        //TODO: get user info in better manner
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
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        HipsterbilityRestClient.get(user.getId() + "/sessions", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray sessions) {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(Boolean.class, HipsterbilityRestClient.booleanAsIntAdapter)
                        .registerTypeAdapter(boolean.class, HipsterbilityRestClient.booleanAsIntAdapter)
                        .setPrettyPrinting().create();
                Type listType = new TypeToken<List<Session>>() {
                }.getType();
                List<Session> sessionList = gson.fromJson(sessions.toString(), listType);
                for(Session tempSession : sessionList){
                    tempSession.setUser(user);
                }
                SessionManager.getInstace().setSessions(new ArrayList<Session>(sessionList));
                displaySessions();
                Toast.makeText(getApplicationContext(),"Sessions loaded: " + sessionList.size() , Toast.LENGTH_LONG)
                        .show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable e) {
                super.onFailure(statusCode, headers, responseBody, e);
                Toast.makeText(getApplicationContext(),"Error: " + statusCode
                        , Toast.LENGTH_LONG)
                        .show();
                Log.d(TAG, "GET Sessions request failed: "+e.getMessage());
            }
        });
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
            this.startActivity(intent);
            Hipsterbility.getInstance().startSession();
        } else if (id == R.id.action_settings) {
            // action with ID action_settings was selected
//            Toast.makeText(this, "Settings selected", Toas
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        }

        return true;
    }
}