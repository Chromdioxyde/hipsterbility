package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.logger;

import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.lifecycle.ActivityLifecycleEvent;

/**
 * Created on 25.03.2014.
 */
public class LifecycleCaptureModule extends AbstractLoggerModule {

    private static LifecycleCaptureModule instance;

    private LifecycleCaptureModule() {
        super(LifecycleCaptureModule.class.getSimpleName(), "lifecycle.txt");
    }

    public static LifecycleCaptureModule getInstance() {
        if (instance == null) instance = new LifecycleCaptureModule();
        return instance;
    }

    private void logEvent(MyEvent event, ActivityLifecycleEvent activityLifecycleEvent) {
        writeLine(event.name, activityLifecycleEvent.getActivity().getClass().getName());
    }

    @Override
    public void activityCreated(ActivityLifecycleEvent activityLifecycleEvent) {
        logEvent(MyEvent.CREATED, activityLifecycleEvent);
    }

    @Override
    public void activityStarted(ActivityLifecycleEvent activityLifecycleEvent) {
        logEvent(MyEvent.STARTED, activityLifecycleEvent);
    }

    @Override
    public void activityResumed(ActivityLifecycleEvent activityLifecycleEvent) {
        logEvent(MyEvent.RESUMED, activityLifecycleEvent);
    }

    @Override
    public void activityPaused(ActivityLifecycleEvent activityLifecycleEvent) {
        logEvent(MyEvent.PAUSED, activityLifecycleEvent);
    }

    @Override
    public void activityStopped(ActivityLifecycleEvent activityLifecycleEvent) {
        logEvent(MyEvent.STOPPED, activityLifecycleEvent);
    }

    @Override
    public void activityDestroyed(ActivityLifecycleEvent activityLifecycleEvent) {
        logEvent(MyEvent.DESTROYED, activityLifecycleEvent);
    }

    private static enum MyEvent {
        CREATED("created"), STARTED("started"), RESUMED("resumed"),
        PAUSED("paused"), STOPPED("stopped"), DESTROYED("destroyed");

        private String name;

        MyEvent(String n) {
            name = n;
        }
    }
}
