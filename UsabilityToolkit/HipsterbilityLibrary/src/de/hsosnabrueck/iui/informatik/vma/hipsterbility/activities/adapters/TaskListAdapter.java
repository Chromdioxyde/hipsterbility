package de.hsosnabrueck.iui.informatik.vma.hipsterbility.activities.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.model.TaskEntity;

import java.util.List;

/**
 * Created by albert on 19.02.14.
 */
public class TaskListAdapter extends ArrayAdapter<TaskEntity> {

    private final static String TAG = TaskListAdapter.class.getName();

    public LayoutInflater inflater;
    public Activity activity;

    public TaskListAdapter(Context context, int resource, List<TaskEntity> tasks) {
        super(context, resource, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TaskEntity task = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.task_list_item, null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.text_name);
            viewHolder.description = (TextView) convertView.findViewById(R.id.text_description);
            viewHolder.order = (TextView) convertView.findViewById(R.id.text_order);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(task.getName());
        viewHolder.description.setText(task.getDescription());
        viewHolder.order.setText(String.valueOf(task.getOrderNr()));
        return convertView;
    }

    private static class ViewHolder {
        TextView order;
        TextView name;
        TextView description;
    }
}