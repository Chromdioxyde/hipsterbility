package de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import de.hsosnabrueck.iui.informatik.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.activities.adapters.SessionListItemAdapter;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.sessions.Session;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.sessions.SessionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Albert Hoffmann on 17.02.14.
 * Sources: http://developer.android.com/guide/topics/ui/layout/listview.html
 *          http://www.vogella.com/tutorials/AndroidListView/article.html
 */
public class SessionActivity extends Activity implements AdapterView.OnItemClickListener {

    //TODO: sort methods and clean up
    private ListView listView;
    private SessionManager sessionManager;
    private ArrayList<Session> sessions;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sessionManager = SessionManager.getInstace();

        setContentView(R.layout.session_activity_layout);
        this. listView = (ListView) findViewById(R.id.sessionslistView);

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
        sessions = this.sessionManager.getSessions();
        displaySessions();
    }

    private void displaySessions(){
        SessionListItemAdapter adapter = new SessionListItemAdapter(this, sessions);
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
        i.putExtra("session",s);
        startActivity(i);

        Toast.makeText(getApplicationContext(),
                    "Session id " + s.getId()
                            + " - "
                            + "Name "+ s.getName() , Toast.LENGTH_LONG)
                    .show();
    }
}