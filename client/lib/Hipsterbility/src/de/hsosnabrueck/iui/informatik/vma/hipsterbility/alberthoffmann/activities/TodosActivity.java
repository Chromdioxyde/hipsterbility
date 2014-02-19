package de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import de.hsosnabrueck.iui.informatik.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.activities.adapters.TodosExpandableListAdapter;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.sessions.Task;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.sessions.Todo;

import java.security.acl.Group;
import java.util.HashMap;

/**
 * Created by albert on 19.02.14.
 */
public class TodosActivity extends Activity {


    private HashMap<Todo, Task> groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todos_activity_layout);
        createData();
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);
        TodosExpandableListAdapter adapter = new TodosExpandableListAdapter(this,
                groups);
        listView.setAdapter(adapter);
    }

    public void createData() {
        for (int j = 0; j < 5; j++) {
            Group group = new Group("Test " + j);
            for (int i = 0; i < 5; i++) {
                group.children.add("Sub Item" + i);
            }
            groups.append(j, group);
        }
    }

}