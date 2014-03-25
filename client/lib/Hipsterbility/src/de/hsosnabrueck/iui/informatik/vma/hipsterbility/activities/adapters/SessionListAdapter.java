package de.hsosnabrueck.iui.informatik.vma.hipsterbility.activities.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Todo;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Albert Hoffmann on 18.02.14.
 */
public class SessionListAdapter extends ArrayAdapter<Session> {
    private final Context context;
    private final ArrayList<Session> sessions;

    public SessionListAdapter(Context context, ArrayList<Session> sessions) {
        super(context, R.layout.session_list_element, sessions);
        this.context = context;
        this.sessions = sessions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.session_list_element, parent, false);

        TextView textId = (TextView) rowView.findViewById(R.id.text_id);
        TextView textName = (TextView) rowView.findViewById(R.id.text_name);
        TextView textDesc = (TextView) rowView.findViewById(R.id.text_description);
        TextView textTodos = (TextView) rowView.findViewById(R.id.text_todos_number);
        TextView textTasks = (TextView) rowView.findViewById(R.id.text_tasks_number);
        TextView textActive = (TextView) rowView.findViewById(R.id.text_active);

//        ImageView imageView = (ImageView) rowView.findViewById(R.id.text_id);

        Session s = sessions.get(position);
        Random rnd = new Random();
        int r = rnd.nextInt(256);
        int g = rnd.nextInt(256);
        int b = rnd.nextInt(256);
        int color = Color.argb(255, r, g, b);
        if((r+g+b) / 3 > 128){
            textId.setTextColor(Color.BLACK);
        } else {
            textId.setTextColor(Color.WHITE);
        }
        textId.setBackgroundColor(color);
        textId.setText(String.valueOf(s.getId()));
        textName.setText(s.getName());
        textDesc.setText(s.getDescription());
        int tasks = 0;
        int todos = 0;
        ArrayList<Todo> todolist = s.getTodos();
        if (todolist != null) {
            todos = todolist.size();
            for (Todo t : s.getTodos()) {
                tasks += t.getTasks().size();
            }
        }
        textTodos.setText(String.valueOf(todos));
        textTasks.setText(String.valueOf(tasks));


        if (s.isActive()) {
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
