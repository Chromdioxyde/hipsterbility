package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules;

import android.app.Activity;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.EventObject;

/**
 * Created by Albert on 22.05.2014.
 */
public class TouchEvent extends EventObject{

    private MotionEvent motionEvent;
    private ArrayList<MyMotionEvent> myMotionEvents;
    private ArrayList<MyMotionEvent> myMotionEventsHistory;
    private final Activity activity;

    public TouchEvent(Activity source, MotionEvent motionEvent) {
        super(source);
        this.motionEvent = motionEvent;
        this.myMotionEvents = new ArrayList<MyMotionEvent>();
        this.activity = source;

        // Collect data from provided MotionEvent Object
        final int action = motionEvent.getAction();
        // Get batched event count and pointer count.
        final int historySize = motionEvent.getHistorySize();
        final int pointerCount = motionEvent.getPointerCount();
        // Consume batched MotionEvents, if existing.
        if(0 < historySize) {
            this.myMotionEventsHistory = new ArrayList<MyMotionEvent>();
            for (int h = 0; h < historySize; h++) {
                for (int p = 0; p < pointerCount; p++) {
                    myMotionEventsHistory.add(
                            new TouchEvent.MyMotionEvent(
                                    motionEvent.getHistoricalEventTime(h),
                                    MotionEvent.ACTION_MOVE,
                                    motionEvent.getPointerId(p),
                                    motionEvent.getHistoricalX(h),
                                    motionEvent.getHistoricalY(h),
                                    motionEvent.getToolType(p)
                            )
                    );
                }
            }
        }
        // Get MotionEvents for all pointers and strip unneeded information.
        for (int p = 0; p < pointerCount; p++) {
            myMotionEvents.add(
                    new TouchEvent.MyMotionEvent(
                            motionEvent.getEventTime(),
                            action,
                            motionEvent.getPointerId(p),
                            motionEvent.getX(),
                            motionEvent.getY(),
                            motionEvent.getToolType(p)
                    )
            );
        }
    }

    public MotionEvent getMotionEvent() {
        return motionEvent;
    }

    public ArrayList<MyMotionEvent> getMyMotionEvents() {
        return myMotionEvents;
    }

    public ArrayList<MyMotionEvent> getMyMotionEventsHistory() {
        return myMotionEventsHistory;
    }

    public Activity getActivity() {
        return activity;
    }



    /**
     * Minimal subclass to store necessary information of MotionEvents.
     * These objects can then safely passed to other Treads, without messing around with MotionEvent objects or copies of them.
     * Because it is just a small class for data transfer inside the application, direct field access is used to keep it simple and small.
     */
    public static class MyMotionEvent{
        public long timestamp;
        public int action;
        public int pointer;
        public float x;
        public float y;
        public int toolType;

        public MyMotionEvent(long timestamp, int action, int pointer, float x, float y, int type) {
            this.timestamp = timestamp;
            this.action = action;
            this.pointer = pointer;
            this.x = x;
            this.y = y;
            this.toolType = type;
        }
    }
}
