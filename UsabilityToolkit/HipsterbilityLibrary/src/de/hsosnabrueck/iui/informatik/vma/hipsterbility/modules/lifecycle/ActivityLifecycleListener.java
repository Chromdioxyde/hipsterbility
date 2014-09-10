package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.lifecycle;

import java.util.EventListener;

/**
 * Created by Albert on 27.03.2014.
 */
public interface ActivityLifecycleListener extends EventListener {
    void activityCreated(ActivityLifecycleEvent activityLifecycleEvent);

    void activityStarted(ActivityLifecycleEvent activityLifecycleEvent);

    void activityResumed(ActivityLifecycleEvent activityLifecycleEvent);

    void activityPaused(ActivityLifecycleEvent activityLifecycleEvent);

    void activityStopped(ActivityLifecycleEvent activityLifecycleEvent);

    void activityDestroyed(ActivityLifecycleEvent activityLifecycleEvent);
}
