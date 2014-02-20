package de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import de.hsosnabrueck.iui.informatik.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.activities.adapters.TodosExpandableListAdapter;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.sessions.Session;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.sessions.Task;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.sessions.Todo;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.sessions.TodoManager;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by albert on 19.02.14.
 */
public class TodosActivity extends Activity {


    private static String TAG = TodosActivity.class.getSimpleName();
    private ArrayList<Todo> groups;
    private TodoManager todoManager = TodoManager.getInstance();
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todos_activity_layout);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Log.w(TAG, "Extras bundle size: " + extras.size());
        if (extras != null) {
            session = extras.getParcelable("session");
        }
        Log.w(TAG, "Session: " + session.getId() + " " + session.getName());
        this.groups = todoManager.getTodos(session);
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);
        TodosExpandableListAdapter adapter = new TodosExpandableListAdapter(this,
                groups);
        listView.setAdapter(adapter);
    }
}