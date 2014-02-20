package de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;
import de.hsosnabrueck.iui.informatik.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.activities.adapters.TodosExpandableListAdapter;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.sessions.*;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Albert Hoffmann on 19.02.14.
 */
public class TodosActivity extends Activity {


    private static String TAG = TodosActivity.class.getSimpleName();
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