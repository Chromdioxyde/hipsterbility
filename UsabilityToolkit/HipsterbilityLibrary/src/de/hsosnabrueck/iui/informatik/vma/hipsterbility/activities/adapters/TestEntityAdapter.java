package de.hsosnabrueck.iui.informatik.vma.hipsterbility.activities.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.hsosnabrueck.hipsterbility.entities.TaskEntity;
import de.hsosnabrueck.hipsterbility.entities.TestEntity;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created on 18.02.14.
 */
public class TestEntityAdapter extends ArrayAdapter<TestEntity> {
    private final Context context;
    private final ArrayList<TestEntity> tests;

    public TestEntityAdapter(Context context, ArrayList<TestEntity> tests) {
        super(context, R.layout.session_list_element, tests);
        this.context = context;
        this.tests = tests;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.session_list_element, parent, false);

        TextView textId = (TextView) rowView.findViewById(R.id.text_id);
        TextView textName = (TextView) rowView.findViewById(R.id.text_name);
        // Todo: uncomment when added description in database
        TextView textDesc = (TextView) rowView.findViewById(R.id.text_description);
//        TextView textTodos = (TextView) rowView.findViewById(R.id.text_todos_number);
        TextView textTasks = (TextView) rowView.findViewById(R.id.text_tasks_number);
        TextView textActive = (TextView) rowView.findViewById(R.id.text_active);

//        ImageView imageView = (ImageView) rowView.findViewById(R.id.text_id);

        TestEntity testEntity = tests.get(position);
        Random rnd = new Random();
        int r = rnd.nextInt(256);
        int g = rnd.nextInt(256);
        int b = rnd.nextInt(256);
        int color = Color.argb(255, r, g, b);
        if ((r + g + b) / 3 > 128) {
            textId.setTextColor(Color.BLACK);
        } else {
            textId.setTextColor(Color.WHITE);
        }
        textId.setBackgroundColor(color);
        textId.setText(String.valueOf(testEntity.getId()));
        textName.setText(testEntity.getName());
        textDesc.setText(testEntity.getDescription());
        int taskCount = 0;
        List<TaskEntity> tasks = testEntity.getTasks();
        if (tasks != null) {
            taskCount = tasks.size();
        }
//        textTodos.setText(String.valueOf(taskCount));
        textTasks.setText(String.valueOf(taskCount));


        if (!testEntity.isTemplate()) {
            //TODO: Change icons to custom ones
//            imageView.setImageResource(android.R.drawable.ic_media_play);
            textActive.setText("active");
            textActive.setTextColor(Color.GREEN);
        } else {
//            imageView.setImageResource(android.R.drawable.ic_lock_lock);
            textActive.setText("inactive");
            textActive.setTextColor(Color.RED);
        }
        return rowView;
    }
}
