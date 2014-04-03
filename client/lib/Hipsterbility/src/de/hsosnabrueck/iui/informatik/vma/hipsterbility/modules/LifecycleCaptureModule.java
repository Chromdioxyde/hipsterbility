package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules;

import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.lifecycle.ActivityLifecycleEvent;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.lifecycle.ActivityLifecycleListener;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.lifecycle.ActivityLifecycleWatcher;

/**
 * Created by Albert on 25.03.2014.
 */
public class LifecycleCaptureModule implements CaptureModule, ActivityLifecycleListener {

    private static LifecycleCaptureModule instance;

    private LifecycleCaptureModule() {
    }

    public static LifecycleCaptureModule getInstance() {
        if (instance == null) instance = new LifecycleCaptureModule();
        return instance;
    }


    @Override
    public void startCapture() {

    }

    @Override
    public void stopCapture() {

    }

    @Override
    public void pauseCapture() {

    }

    @Override
    public void resumeCapture() {

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
