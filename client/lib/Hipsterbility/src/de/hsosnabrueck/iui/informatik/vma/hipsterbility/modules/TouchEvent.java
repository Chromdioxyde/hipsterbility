package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules;

import android.app.Activity;
import android.view.MotionEvent;

import java.util.EventObject;

/**
 * Created by Albert on 22.05.2014.
 */
public class TouchEvent extends EventObject{

    private final MotionEvent motionEvent;
    private final Activity activity;

    public TouchEvent(Activity source, MotionEvent motionEvent) {
        super(source);
        this.motionEvent = motionEvent;
        this.activity = source;
    }

    public MotionEvent getMotionEvent() {
        return motionEvent;
    }

    public Activity getActivity() {
        return activity;
    }
}
