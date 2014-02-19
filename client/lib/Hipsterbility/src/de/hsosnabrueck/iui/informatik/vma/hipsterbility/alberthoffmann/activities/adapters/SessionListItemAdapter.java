package de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import de.hsosnabrueck.iui.informatik.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.sessions.Session;

import java.util.ArrayList;

/**
 * Created by Albert Hoffmann on 18.02.14.
 */
public class SessionListItemAdapter extends ArrayAdapter<Session> {
    private final Context context;
    private final ArrayList<Session> sessions;

    public SessionListItemAdapter(Context context, ArrayList<Session> sessions) {
        super(context, R.layout.session_list_element, sessions);
        this.context = context;
        this.sessions = sessions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.session_list_element, parent, false);

        TextView textName = (TextView) rowView.findViewById(R.id.text_name);
        TextView textDesc = (TextView) rowView.findViewById(R.id.text_description);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        Session s = sessions.get(position);

        textName.setText(s.getName());
        textDesc.setText(s.getDescription());


        if (s.isActive()) {
            //TODO: Change icons to custom ones
            imageView.setImageResource(android.R.drawable.ic_media_play);
//            textViewSecond.setText("active");
        } else {
            imageView.setImageResource(android.R.drawable.ic_lock_lock);
//            textViewSecond.setText("inactive");
        }
        return rowView;
    }
}
