package de.hsosnabrueck.iui.informatik.vma.hipsterbility.activities.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.sessions.SessionManager;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.tests.TestManager;

/**
 * Created by Albert on 06.10.2014.
 */
public class StartSessionDialogFragment extends DialogFragment {
    String name;
    StartSessionDialogListener mListener;

    public interface StartSessionDialogListener {
        public void onStartSession(DialogFragment dialog);
    }

    public static StartSessionDialogFragment newInstance(String name) {
        StartSessionDialogFragment f = new StartSessionDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("name", name);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString("name");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle(R.string.start_session);
        View v = inflater.inflate(R.layout.start_session_dialog_fragment, container, false);
        View tv = v.findViewById(R.id.text_test_name);
        ((TextView)tv).setText(name);
        final EditText editName = (EditText) v.findViewById(R.id.edit_text_session_name);
        final EditText editDescription = (EditText) v.findViewById(R.id.edit_text_session_description);
        // Watch for button clicks.
        Button button = (Button)v.findViewById(R.id.button_start_session);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SessionManager sessionManager = new SessionManager(getActivity());
                sessionManager.createNewSession(editName.getText().toString(), editDescription.getText().toString(), TestManager.activeTest);
                notifyStartSession();
            }
        });

        return v;
    }

    private void notifyStartSession() {
        mListener.onStartSession(this);
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (StartSessionDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement StartSessionDialogListener");
        }
    }

}
