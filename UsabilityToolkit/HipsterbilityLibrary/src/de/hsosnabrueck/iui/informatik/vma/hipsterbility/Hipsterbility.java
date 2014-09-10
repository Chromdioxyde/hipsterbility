package de.hsosnabrueck.iui.informatik.vma.hipsterbility;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.view.MotionEvent;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.TouchEvent;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.TouchEventListener;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.lifecycle.ActivityLifecycleWatcher;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.services.HipsterbilityService;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;

/**
 * The Hipsterbility class is a monolithic wrapper for the Hipsterbility-library and implements all public methods which
 * a user can use in own application, following the facade design pattern.
 */
public class Hipsterbility {

    private static final String TAG = Hipsterbility.class.getSimpleName();

    private static Application application;
    private static Class startActivityClass;
    private static boolean rootFeaturesEnabled;
    private static HashMap<Class<?>, List<EventListener>> listeners = new HashMap<Class<?>, List<EventListener>>();

    private Hipsterbility(){}

    /**
     * This enables usability testing for the calling application
     *
     * @param activity starting activity from calling application
     */
    public static void enableTesting(Activity activity) throws Exception{
        application = activity.getApplication();
        startActivityClass = activity.getClass();
        boolean moduleEnabled = false;
        for(MODULE m : MODULE.values()){
            if(m.enabled){
                moduleEnabled = true;
                break;
            }
        }
        if(!moduleEnabled){
            throw new Exception("UX Library error: No Modules Enabled!");
        }
        startService();
        ActivityLifecycleWatcher.getInstance().setApp(application);
    }

    private static void startService() {
        Context context = application.getApplicationContext();
        assert (context != null);
        Intent i = new Intent(context, HipsterbilityService.class);
        context.startService(i);
    }

    public static boolean isRootFeaturesEnabled() {
        return rootFeaturesEnabled;
    }

    public static void setRootFeaturesEnabled(boolean enabled) {
        rootFeaturesEnabled = enabled;
    }

    public static Class getStartActivityClass() {
        return startActivityClass;
    }

    /**
     * Small enumeration to store which features should be enabled.
     * Features are disabled by default.
     */
    public static enum MODULE {
        AUDIO, VIDEO, SCREEN, TOUCH, LIFECYCLE;
        public boolean enabled = false;
    }

    public static void relayMotionEvent(Activity activity, MotionEvent motionEvent){
        notifyTouchEventListener(new TouchEvent(activity,motionEvent));
    }

    private static void notifyTouchEventListener(TouchEvent event) {
        List<EventListener> list = listeners.get(TouchEventListener.class);
        if(null == list) return;
        for(EventListener l : list){
            ((TouchEventListener)l).onTouchEvent(event);
        }
    }

    public static void addEventListener(Class<?> type , EventListener listener){
        List<EventListener> list = listeners.get(type);
        if(null == list){
            list = new ArrayList<EventListener>();
            listeners.put(type, list);
        }
        list.add(listener);
    }

    public static void removeEventListener(Class<?> type, EventListener listener){
        List<EventListener> list = listeners.get(type);
        if(null != list) {
            list.remove(listener);
        }
    }
}
