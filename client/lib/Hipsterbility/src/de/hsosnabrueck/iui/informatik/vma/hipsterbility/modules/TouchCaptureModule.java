package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules;

import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.lifecycle.ActivityLifecycleEvent;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.lifecycle.ActivityLifecycleListener;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.lifecycle.ActivityLifecycleWatcher;

/**
 * Created by Albert on 25.03.2014.
 */
public class TouchCaptureModule implements CaptureModule, ActivityLifecycleListener {

    private static TouchCaptureModule instance;

    private TouchCaptureModule() {
    }

    public static TouchCaptureModule getInstance() {
        if (instance == null) instance = new TouchCaptureModule();
        return instance;
    }


    @Override
    public void startCapture() {
//        TODO: implement
    }

    @Override
    public void stopCapture() {
//        TODO: implement
    }

    @Override
    public void pauseCapture() {
//        TODO: implement
    }

    @Override
    public void resumeCapture() {
//        TODO: implement
    }

    @Override
    public boolean isCapturing() {
        return false;
    }

    @Override
    public void init() {
        ActivityLifecycleWatcher.getInstance().addActivityLifecycleListener(this);
    }

    @Override
    public void activityCreated(ActivityLifecycleEvent activityLifecycleEvent) {

    }

    @Override
    public void activityStarted(ActivityLifecycleEvent activityLifecycleEvent) {

    }

    @Override
    public void activityResumed(ActivityLifecycleEvent activityLifecycleEvent) {

    }

    @Override
    public void activityPaused(ActivityLifecycleEvent activityLifecycleEvent) {

    }

    @Override
    public void activityStopped(ActivityLifecycleEvent activityLifecycleEvent) {

    }

    @Override
    public void activityDestroyed(ActivityLifecycleEvent activityLifecycleEvent) {

    }
}
