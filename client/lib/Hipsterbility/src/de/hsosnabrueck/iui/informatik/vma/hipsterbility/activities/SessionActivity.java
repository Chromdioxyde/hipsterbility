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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.Hipsterbility;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.activities.adapters.SessionListAdapter;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Task;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Todo;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.User;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.rest.HipsterbilityRestClient;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.services.CaptureService;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.sessions.SessionManager;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 17.02.14.
 * Sources: http://developer.android.com/guide/topics/ui/layout/listview.html
 * http://www.vogella.com/tutorials/AndroidListView/article.html
 */
public class SessionActivity extends Activity implements AdapterView.OnItemClickListener {

    private final static String TAG = SessionActivity.class.getName();
    private boolean sessionChosen = false;
    private Context context;
    private SharedPreferences prefs;

    private ListView listView;
    private SessionManager sessionManager;
    private ArrayList<Session> sessions;

    private User user;
    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;
    private SessionListAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sessionManager = SessionManager.getInstace();
        this.context = this;
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        loadSettings();
        ActionBar ab = this.getActionBar();
        if (ab != null) {
            ab.setTitle(getString(R.string.sessions_for_user));
            ab.setSubtitle(getString(R.string.choose_session));
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeButtonEnabled(true);
            ab.setIcon(R.drawable.ic_launcher);
        }
        setContentView(R.layout.session_activity_layout);
        this.listView = (ListView) findViewById(R.id.sessionslistView);
        // TODO uncomment after testing
        SessionManager.getInstace().setSessionInProgress(new Session(0));
        startSession();
//        loadUserIdFromServer();
    }

    private void loadSettings() {
        HipsterbilityRestClient.setServer(
                prefs.getString(getString(R.string.pref_key_server), ""),
                Integer.valueOf(prefs.getString(getString(R.string.pref_key_port), "3000")));
        HipsterbilityRestClient.setMaxConnections(
                Integer.valueOf(
                        prefs.getString(getString(R.string.pref_key_max_connection), "1")
                )
        );
        Hipsterbility.getInstance().setRootFeaturesEnabled(
                prefs.getBoolean(getString(R.string.pref_key_enable_root), false)
        );

    }

    private void displaySessions() {
        sessions = this.sessionManager.getSessions();
        adapter = new SessionListAdapter(this, sessions);
        this.listView.setAdapter(adapter);

        // Add listener to ListView for actions on selected item
        listView.setOnItemClickListener(this);
        listView.setSelector(R.drawable.session_list_selector);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent i = new Intent(this, TodosActivity.class);
        sessionManager.setSessionInProgress(sessions.get(position));
        startActivity(i);
        this.sessionChosen = true;
    }

    public void loadUserIdFromServer() {
        final String username = prefs.getString(getString(R.string.pref_key_user_name), "");
        final String password = prefs.getString(getString(R.string.pref_key_password), "");
        if (username.equals("") || password.equals("")) {
            Toast.makeText(this, R.string.toast_username_or_password_missing, Toast.LENGTH_LONG).show();
            return;
        }
        RequestParams params = new RequestParams();
        params.add("name", username);
        params.add("password", password);
        HipsterbilityRestClient.post("/auth/", params, new JsonHttpResponseHandler() {

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
            public void onSuccess(JSONObject userId) {
                try {
                    int id = userId.getInt("id");
                    Log.d(TAG, "User ID = " + id);
                    user = new User(id, username, password);
                    loadSessionsFromServer();
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    dismissProgressDialog();
                }
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


    private synchronized void loadSessionsFromServer() {

        HipsterbilityRestClient.get("/" + user.getId() + "/sessions", null, new JsonHttpResponseHandler() {

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
            public void onSuccess(JSONArray sessions) {
                Gson gson = HipsterbilityRestClient.getGsonBooleanWorkaround();
                Type listType = new TypeToken<List<Session>>() {
                }.getType();
                List<Session> sessionList = gson.fromJson(sessions.toString(), listType);
                progressDialog.setIndeterminate(false);
                progressDialog.setMax(sessionList.size());
                for (int i = 0; i < sessionList.size(); i++) {
                    Session s = sessionList.get(i);
                    s.setUser(user);
                    getTodosFromServer(s);
                    progressDialog.setProgress(i + 1);
                }
                SessionManager.getInstace().setSessions(new ArrayList<Session>(sessionList));
                dismissProgressDialog();
                displaySessions();
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
            if (!sessionChosen) {
                Toast.makeText(this, getString(R.string.select_session_first), Toast.LENGTH_SHORT)
                        .show();
                // TODO: remove after debug
                SessionManager.getInstace().setSessionInProgress(new Session(0));
//                return false;
            }
            startSession();
        } else if (id == R.id.action_settings) {
            openSettings();
        } else if (id == R.id.action_reload_sessions) {
            loadUserIdFromServer();
        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    public void getTodosFromServer(final Session session) {
        HipsterbilityRestClient.get("/" + session.getUser().getId() + "/" + session.getId() + "/todos", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray sessions) {
                Gson gson = HipsterbilityRestClient.getGsonBooleanWorkaround();
                Type listType = new TypeToken<List<Todo>>() {
                }.getType();
                List<Todo> t = gson.fromJson(sessions.toString(), listType);
                ArrayList<Todo> todoList = new ArrayList<Todo>(t);
                for (Todo tempTodo : todoList) {
                    getTasksForTodoFromServer(tempTodo, session);
                }
                session.setTodos(todoList);
                listAdapterNotifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable e) {
                super.onFailure(statusCode, headers, responseBody, e);
                Toast.makeText(context, "Error: " + statusCode
                        , Toast.LENGTH_LONG)
                        .show();
                Log.d(TAG, "GET Todos request failed: " + e.getMessage());
            }
        });
    }

    public void getTasksForTodoFromServer(final Todo todo, final Session session) {
        HipsterbilityRestClient.get("/" + session.getUser().getId() + "/" + session.getId()
                + "/todos/" + todo.getId() + "/" + "tasks", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray sessions) {
                Gson gson = HipsterbilityRestClient.getGsonBooleanWorkaround();
                Type listType = new TypeToken<List<Task>>() {
                }.getType();
                List<Task> t = gson.fromJson(sessions.toString(), listType);
                todo.setTasks(new ArrayList<Task>(t));
                Log.d(TAG, "GET Tasks for Todo " + todo.getId() + " count: " + t.size());
                listAdapterNotifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable e) {
                super.onFailure(statusCode, headers, responseBody, e);
                Toast.makeText(context, "Error: " + statusCode
                        , Toast.LENGTH_LONG)
                        .show();
                Log.d(TAG, "GET Tasks request failed: " + e.getMessage());
            }
        });
    }

    private synchronized void listAdapterNotifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    private void startSession() {
        Intent intent = new Intent(this, Hipsterbility.getInstance().getStartActivityClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startService(new Intent(this, CaptureService.class));
        this.startActivity(intent);
        finish();
    }

}