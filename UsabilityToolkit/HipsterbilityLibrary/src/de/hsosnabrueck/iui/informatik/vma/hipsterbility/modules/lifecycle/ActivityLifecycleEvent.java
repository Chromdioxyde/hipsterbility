package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.lifecycle;

import android.app.Activity;

import java.util.EventObject;

/**
 * Created by Albert on 27.03.2014.
 */
public class ActivityLifecycleEvent extends EventObject {
    private Activity activity;

    public ActivityLifecycleEvent(Object source, Activity activity) {
        super(source);
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }
}
