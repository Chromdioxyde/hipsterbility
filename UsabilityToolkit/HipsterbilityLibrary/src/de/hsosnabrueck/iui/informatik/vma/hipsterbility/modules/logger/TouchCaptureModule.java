package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.logger;

import android.app.Activity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.Hipsterbility;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.TouchEvent;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.TouchEventListener;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.lifecycle.ActivityLifecycleEvent;

/**
 * Created by Albert on 25.03.2014.
 */
public class TouchCaptureModule extends AbstractLoggerModule implements TouchEventListener, GestureDetector.OnGestureListener,
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

    @Override
    public void startCapture() {
        super.startCapture();
        registerTouchListener();
    }

    @Override
    public void stopCapture() {
        super.stopCapture();
        unregisterTouchListener();
    }

    private void logTouch(Activity activity, String method, MotionEvent event){
        writeLine(
                activity.getClass().getName(),
                method


                );
        // TODO: log into memory and/or file
    }

    private void registerTouchListener() {
        Hipsterbility.addEventListener(TouchEventListener.class, this);
    }

    private void unregisterTouchListener() {
        Hipsterbility.removeEventListener(TouchEventListener.class, this);
    }

    @Override
    public void activityResumed(ActivityLifecycleEvent activityLifecycleEvent) {
        this.activity = activityLifecycleEvent.getActivity();
        // TODO: do something on detection
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        Log.d(tag, "onSingleTapConfirmed " + motionEvent);
        // TODO: do something on detection
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        Log.d(tag, "onDoubleTap " + motionEvent);
        // TODO: do something on detection
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        Log.d(tag, "onDoubleTapEvent: " + motionEvent);
        // TODO: do something on detection
        return true;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.d(tag, "onDown: " + motionEvent);
        // TODO: do something on detection
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        Log.d(tag, "onShowPress: " + motionEvent);
        // TODO: do something on detection
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.d(tag, "onSingleTapUp: " + motionEvent);
        // TODO: do something on detection
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        Log.d(tag, "onScroll: " + motionEvent + motionEvent2);
        // TODO: do something on detection
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        // TODO: do something on detection
        Log.d(tag, "onLongPress: " + motionEvent);
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float velocityX, float velocityY) {
        Log.d(tag, "onFling: " + motionEvent + motionEvent2);
        // TODO: do something on detection
        return true;
    }

    @Override
    public void onTouchEvent(TouchEvent e) {
        if (capture) {
            detector.onTouchEvent(e.getMotionEvent());
        }
    }

    private void printSamples(MotionEvent ev) {
        final int historySize = ev.getHistorySize();
        final int pointerCount = ev.getPointerCount();
        for (int h = 0; h < historySize; h++) {
            System.out.printf("At time %d:", ev.getHistoricalEventTime(h));
            for (int p = 0; p < pointerCount; p++) {
                System.out.printf("  pointer %d: (%f,%f)",
                        ev.getPointerId(p), ev.getHistoricalX(p, h), ev.getHistoricalY(p, h));
            }
        }
        System.out.printf("At time %d:", ev.getEventTime());
        for (int p = 0; p < pointerCount; p++) {
            System.out.printf("  pointer %d: (%f,%f)",
                    ev.getPointerId(p), ev.getX(p), ev.getY(p));
        }
    }
}
