package de.hsosnabrueck.iui.informatik.vma.hipsterbility.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import de.hsosnabrueck.iui.informatik.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.activities.adapters.TodosExpandableListAdapter;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Task;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Todo;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.rest.HipsterbilityRestClient;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.sessions.*;
import org.apache.http.Header;
import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Albert Hoffmann on 19.02.14.
 */
public class TodosActivity extends Activity {


    private static String TAG = TodosActivity.class.getName();
    private ArrayList<Todo> groups;
//    private TodoManager todoManager = TodoManager.getInstance();
    private Session session;
    private SessionManager sessionManager = SessionManager.getInstace();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todos_activity_layout);
//        TODO: remove clutter
//        Intent intent = getIntent();
//        Bundle extras = intent.getExtras();
//        Log.w(TAG, "Extras bundle size: " + extras.size());
//        if (extras != null) {
//            session = extras.getParcelable("session");
//        }
        session = sessionManager.getSessionInProgress();

        Log.w(TAG, "Session: " + session.getId() + " " + session.getName());
       getTodosFromServer();
    }

    public void getTodosFromServer(){
        HipsterbilityRestClient.get(session.getUser().getId() + "/" + session.getId() + "/todos", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray sessions) {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(Boolean.class, HipsterbilityRestClient.booleanAsIntAdapter)
                        .registerTypeAdapter(boolean.class, HipsterbilityRestClient.booleanAsIntAdapter)
                        .setPrettyPrinting().create();
                Type listType = new TypeToken<List<Todo>>() {
                }.getType();
                List<Todo> t = gson.fromJson(sessions.toString(), listType);
                ArrayList<Todo> todoList = new  ArrayList<Todo>(t);
                for(Todo tempTodo : todoList){
                    getTasksForTodoFromServer(tempTodo);
                }
                session.setTodos(todoList);

                displayTodos();
                Toast.makeText(getApplicationContext(), "Todos loaded: " + t.size(), Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable e) {
                super.onFailure(statusCode, headers, responseBody, e);
                Toast.makeText(getApplicationContext(), "Error: " + statusCode
                        , Toast.LENGTH_LONG)
                        .show();
                Log.d(TAG, "GET Todos request failed: " + e.getMessage());
            }
        });
    }

    public void getTasksForTodoFromServer(final Todo todo){
        HipsterbilityRestClient.get(session.getUser().getId() + "/" + session.getId()
                + "/todos/" + todo.getId() + "/" + "tasks", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray sessions) {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(Boolean.class, HipsterbilityRestClient.booleanAsIntAdapter)
                        .registerTypeAdapter(boolean.class, HipsterbilityRestClient.booleanAsIntAdapter)
                        .setPrettyPrinting().create();
                Type listType = new TypeToken<List<Task>>() {
                }.getType();
                List<Task> t = gson.fromJson(sessions.toString(), listType);
                todo.setTasks(new ArrayList<Task>(t));
                Log.d(TAG, "GET Tasks for Todo " + todo.getId() + " count: " + t.size());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable e) {
                super.onFailure(statusCode, headers, responseBody, e);
                Toast.makeText(getApplicationContext(), "Error: " + statusCode
                        , Toast.LENGTH_LONG)
                        .show();
                Log.d(TAG, "GET Tasks request failed: " + e.getMessage());
            }
        });
    }

    private void displayTodos() {
        this.groups = session.getTodos();
        if(this.groups == null){
            finish();
            Toast.makeText(this, R.string.err_no_todos_for_session,
                    Toast.LENGTH_LONG).show();
        } else {
            Log.w(TAG, groups.size() + " Todos for Session found.");
            ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);
            TodosExpandableListAdapter adapter = new TodosExpandableListAdapter(this,
                    groups);
            listView.setAdapter(adapter);
            //            TODO: do something with selected Item
        }
    }
}