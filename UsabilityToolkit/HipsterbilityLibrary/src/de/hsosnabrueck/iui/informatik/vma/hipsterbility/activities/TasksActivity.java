package de.hsosnabrueck.iui.informatik.vma.hipsterbility.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.activities.adapters.TaskListAdapter;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.model.TaskEntity;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.model.TestEntity;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.tests.TestManager;

import java.util.Collections;
import java.util.List;

/**
 * Created on 19.02.14.
 */
public class TasksActivity extends Activity {


    private static String TAG = TasksActivity.class.getName();
    private List<TaskEntity> tasks;
    private TestEntity test;
//    private TestSessionEntity session;
    private TestManager testManager = TestManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todos_activity);
        setTitle(R.string.todos_title);
        test = TestManager.activeTest;
        Log.w(TAG, "Test: " + test.getId() + " " + test.getName());
        displayTodos();
    }

    private void displayTodos() {
        this.tasks = test.getTasks();
        Collections.sort(tasks);
        if (this.tasks == null) {
            finish();
            Toast.makeText(this, R.string.err_no_tasks_for_test,
                    Toast.LENGTH_LONG).show();
        } else {
            Log.w(TAG, tasks.size() + " Todos for Session found.");
            ListView listView = (ListView) findViewById(R.id.listView);
            TaskListAdapter adapter = new TaskListAdapter(this, R.layout.task_list_item, tasks);
            listView.setAdapter(adapter);
            //            TODO: do something with selected Item
        }
    }

}