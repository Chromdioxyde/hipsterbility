package de.hsosnabrueck.iui.informatik.vma.hipsterbility.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.Hipsterbility;
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
    private Session session;
    private SessionManager sessionManager = SessionManager.getInstace();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todos_activity_layout);
        setTitle(R.string.todos_title);
        session = sessionManager.getSessionInProgress();
        Log.w(TAG, "Session: " + session.getId() + " " + session.getName());
        displayTodos();
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