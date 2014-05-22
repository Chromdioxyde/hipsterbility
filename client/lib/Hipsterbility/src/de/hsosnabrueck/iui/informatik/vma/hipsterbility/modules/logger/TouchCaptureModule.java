package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.logger;

import android.app.Activity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.lifecycle.ActivityLifecycleEvent;

/**
 * Created by Albert on 25.03.2014.
 */
public class TouchCaptureModule extends AbstractLoggerModule implements ViewGroup.OnTouchListener, GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    private static TouchCaptureModule instance;
    private Activity activity;
    private GestureDetector detector;

    private TouchCaptureModule() {
        super(TouchCaptureModule.class.getSimpleName(), "touch.txt");
        detector = new GestureDetector(null, this);
//        detector.setOnDoubleTapListener(this);
    }

    public static TouchCaptureModule getInstance() {
        if (instance == null) instance = new TouchCaptureModule();
        return instance;
    }

    private void registerTouchListener() {
//        this.activity.getWindow().getDecorView().getRootView().setOnTouchListener(this);
//        this.activity.getWindow().getDecorView().findViewById(android.R.id.content).setOnTouchListener(this);
//        ViewGroup group = (ViewGroup)this.activity.getWindow().getDecorView().getRootView();
//        for(int i = 0; i < group.getChildCount(); i++){
//            group.getChildAt(i).setOnTouchListener(this);
//
        this.activity.findViewById(android.R.id.content).setOnTouchListener(this);
//        group.setOnTouchListener(this);
    }

//    private void unregisterTouchListener() {
//        try {
//            //Remove touch listener from activity's view
//            this.activity.getWindow().getDecorView().getRootView().setOnTouchListener(null);
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (capture) {
            detector.onTouchEvent(motionEvent);
        }
        return true;
    }

    @Override
    public void activityResumed(ActivityLifecycleEvent activityLifecycleEvent) {
        this.activity = activityLifecycleEvent.getActivity();
        registerTouchListener();
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        Log.d(tag, "onSingleTapConfirmed " + motionEvent);
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        Log.d(tag, "onDoubleTap " + motionEvent);
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        Log.d(tag, "onDoubleTapEvent: " + motionEvent);
        return true;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.d(tag, "onDown: " + motionEvent);
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        Log.d(tag, "onShowPress: " + motionEvent);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.d(tag, "onSingleTapUp: " + motionEvent);
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        Log.d(tag, "onScroll: " + motionEvent + motionEvent2);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.d(tag, "onLongPress: " + motionEvent);
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float velocityX, float velocityY) {
        Log.d(tag, "onFling: " + motionEvent + motionEvent2);
        return true;
    }
}
