package de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.activities.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;
import de.hsosnabrueck.iui.informatik.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.sessions.Task;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.sessions.Todo;

import java.util.ArrayList;

/**
 * Created by albert on 19.02.14.
 */
public class TodosExpandableListAdapter extends BaseExpandableListAdapter {

    private final ArrayList<Todo> todos;
    public LayoutInflater inflater;
    public Activity activity;

    public TodosExpandableListAdapter(Activity activity, ArrayList<Todo> todos) {
        this.activity = activity;
        this.todos = todos;
        this.inflater = activity.getLayoutInflater();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return todos.get(groupPosition).getTasks().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final Task children = (Task) getChild(groupPosition, childPosition);
        TextView text = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.todos_listrow_details, null);
        }
        text = (TextView) convertView.findViewById(R.id.textView1);
        text.setText(children.getName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: do something with selected item
                Toast.makeText(activity, children.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return todos.get(groupPosition).getTasks().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return todos.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return todos.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.todos_listrow_group, null);
        }
        Todo group = (Todo) getGroup(groupPosition);
        ((CheckedTextView) convertView).setText(group.getName());
        ((CheckedTextView) convertView).setChecked(isExpanded);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


}