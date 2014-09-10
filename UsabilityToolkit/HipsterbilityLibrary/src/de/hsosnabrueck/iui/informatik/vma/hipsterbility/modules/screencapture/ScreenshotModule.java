package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.screencapture;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.Hipsterbility;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.helper.Util;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.CaptureModule;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.TouchEvent;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.TouchEventListener;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.lifecycle.ActivityLifecycleEvent;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.lifecycle.ActivityLifecycleListener;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.lifecycle.ActivityLifecycleWatcher;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.sessions.SessionManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * Created on 21.02.14.
 * Sources: http://stackoverflow.com/questions/16748384/android-take-screenshot-programmatically-of-the-whole-screen
 * http://stackoverflow.com/questions/2661536/how-to-programatically-take-a-screenshot-on-android?rq=1
 */
public class ScreenshotModule implements TouchEventListener, CaptureModule, ActivityLifecycleListener {

    // Size for touch marker in display points, not pixels
    private final static float TOUCH_MARKER_RADIUS_DP = 15.0f;
    private final static int TOUCH_MARKER_ALPHA = 50;
    private final static int COLOR_ACTION_DOWN = Color.GREEN;
//    private final static int COLOR_ACTION_MOVE = Color.YELLOW;
    private final static int COLOR_ACTION_UP = Color.RED;
    private final static int COLOR_ACTION_CANCEL = Color.GRAY;
    private final static int MIN_TIME_BETWEEN_SCREENSHOTS_MS = 200;

    private final static String TAG = ScreenshotModule.class.getSimpleName();

    private static ScreenshotModule instance;
    private Session session;
    private Activity activity;
//    private ArrayList<Coordinates> touches = new ArrayList<Coordinates>();
    private boolean capture;
    private boolean drawTouches = true;
    private boolean pause = false;
    //TODO make this a setting
    private boolean collectEvents = true;
    private Point displaySize;
    private int continuousInterval = 1000;
    private Timer timer;
    private boolean continuousScreenshotTaking = false;
    private int touchMarkerRadiusInPixels = -1;
    private Executor executor;
    private long lastScreenshotTime;

    private ArrayList<TouchEvent.MyMotionEvent> events;


    private ScreenshotModule() {}

    public static ScreenshotModule getInstance() {
        if (instance == null) {
            instance = new ScreenshotModule();
        }
        return instance;
    }

    private void takeScreenshot(final Activity activity, final long time){
        /*
            Too much computation in the UI thread makes the app unresponsive and may lead to the app being killed by the
            systems watchdog.
            Therefore create a new thread for messing around with screenshots.
         */
        long threshold = TimeUnit.MILLISECONDS.convert(time-lastScreenshotTime, TimeUnit.NANOSECONDS);
        Log.d(TAG, "Time since last screenshot (ms): " + threshold);
        if (pause || !capture || MIN_TIME_BETWEEN_SCREENSHOTS_MS >= threshold ) return;
        // Update time, because we don't want to take screenshots too often, as they take some time to be fully processed.
        lastScreenshotTime = time;
        // Get the Events and clear the list for the next ones.
        final ArrayList<TouchEvent.MyMotionEvent> events = this.events;
        this.events = new ArrayList<TouchEvent.MyMotionEvent>();
        final String filename = Util.getFullFilePath(session.getId(), Util.SCREENSHOTS_DIR, String.valueOf(time), Util.IMAGE_PNG);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                long startTime = System.nanoTime();
                Log.d(TAG, "Screenshot thread started");
                Bitmap bitmap = Bitmap.createBitmap(displaySize.x, displaySize.y, Bitmap.Config.ARGB_4444);
                createScreenshotCanvas(activity, bitmap);
                try {
                    saveBitmapToFile(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                long finishTime = System.nanoTime();
                Log.d(TAG, "Execution Time (ms): " + TimeUnit.MILLISECONDS.convert(finishTime-startTime, TimeUnit.NANOSECONDS));
            }

            private void createScreenshotCanvas(Activity activity, Bitmap bitmap) {
                // Get root view
                final View rootView = activity.getWindow().getDecorView().getRootView();
                // Create the bitmap to use to draw the screenshot
                final Canvas canvas = new Canvas(bitmap);
                // Get current theme to know which background to use
                final Resources.Theme theme = activity.getTheme();
                assert theme != null;
                final TypedArray ta = theme
                        .obtainStyledAttributes(new int[]{android.R.attr.windowBackground});
                assert ta != null;
                final int res = ta.getResourceId(0, 0);
                final Drawable background = activity.getResources().getDrawable(res);
                // Draw background
                assert background != null;
                background.draw(canvas);
                // Draw views
                assert rootView != null;
                // This part has to be run in the UI thread. Otherwise clicking links etc. lets the app crash.
                Runnable r = new Runnable(){
                    @Override
                    public void run() {
                        rootView.draw(canvas);
                        if (drawTouches && (null != events && !events.isEmpty())) {
                            drawTouchesOnCanvas(canvas);
                        }
                    }
                };
                activity.runOnUiThread(r);
            }

            private Canvas drawTouchesOnCanvas(Canvas canvas) {
                int eventCount = events.size();
                ShapeDrawable dr = new ShapeDrawable(new OvalShape());
                float i = 0f;
                for(TouchEvent.MyMotionEvent event : events){
                    int color;
                    switch (event.action){
                        case(MotionEvent.ACTION_UP): color = COLOR_ACTION_UP; break;
                        case(MotionEvent.ACTION_DOWN): color = COLOR_ACTION_DOWN; break;
                        case(MotionEvent.ACTION_MOVE): //color = COLOR_ACTION_MOVE
                            // Color gradient from green (start) to red (finish)
                            float ratio = i / eventCount;
                            color = Color.rgb((int)(ratio * 255f),
                                    (int)((1 - ratio) * 255),
                                    63); break;
                        default: color = COLOR_ACTION_CANCEL;
                    }
                    i++;

                    Paint paint = dr.getPaint();
                    paint.setColor(color);
                    dr.setAlpha(TOUCH_MARKER_ALPHA);
                    int x = (int) event.x;
                    int y = (int) event.y;
                    dr.setBounds(
                            x - touchMarkerRadiusInPixels,
                            y - touchMarkerRadiusInPixels,
                            x + touchMarkerRadiusInPixels,
                            y + touchMarkerRadiusInPixels
                    );
                    dr.draw(canvas);
                }
                return canvas;
            }

            private void saveBitmapToFile(Bitmap bitmap) throws IOException {
                // Save the screenshot to the file system
                FileOutputStream fos = new FileOutputStream(filename);
                if (!bitmap.compress(Bitmap.CompressFormat.PNG, 10, fos)) {
                    Log.e(TAG, "Compress/Write failed");
                }
                fos.flush();
                fos.close();

            }
        };
        executor.execute(r);
    }

    @Override
    public void startCapture() {
        this.session = SessionManager.getInstace().getSessionInProgress();
        Util.makeDirectoriesForPath(Util.createOutputDirPathName(session.getId(), Util.SCREENSHOTS_DIR));
        registerTouchListener();
        this.capture = true;
        startCaptureTimer();
    }

    private void startCaptureTimer() {
        if(!continuousScreenshotTaking) return;
        if(null == timer) {
            timer = new Timer();
        }
        timer.scheduleAtFixedRate(new TimerTask(){
            public void run(){
                takeScreenshot(activity, System.nanoTime());
            }
        }, 200, continuousInterval);
    }

    @Override
    public void stopCapture() {
        unregisterTouchListener();
        this.capture = false;
        stopCaptureTimer();
    }

    private void stopCaptureTimer() {
        if(null != timer){
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void pauseCapture() {
        this.pause = true;
    }

    @Override
    public void resumeCapture() {
        this.pause = false;
    }

    @Override
    public boolean isCapturing() {
        return false;
    }

    @Override
    public void init() {
        ActivityLifecycleWatcher.getInstance().addActivityLifecycleListener(this);
        executor = Executors.newCachedThreadPool();
    }


    private void unregisterTouchListener() {
        Hipsterbility.removeEventListener(TouchEventListener.class, this);
    }


    @Override
    public void activityResumed(ActivityLifecycleEvent activityLifecycleEvent) {
        Activity activity = activityLifecycleEvent.getActivity();
        this.activity = activity;
//        if(touchMarkerRadiusInPixels == -1){
            touchMarkerRadiusInPixels = Math.round(Util.convertDpToPixel(TOUCH_MARKER_RADIUS_DP, activity.getApplicationContext()));
//        }
        long time = System.nanoTime();
        Display display = activity.getWindowManager().getDefaultDisplay();
        displaySize = new Point();
        display.getSize(displaySize);
        if(capture && !pause){
            if(continuousScreenshotTaking)startCaptureTimer();
        } else {
            takeScreenshot(activity, time);
        }
    }

    private void registerTouchListener() {
        Hipsterbility.addEventListener(TouchEventListener.class, this);
    }

    @Override
    public void onTouchEvent(TouchEvent e) {
        long time = System.nanoTime();
        // If screenshots are triggered by timer, we don't need to evaluate the event.
        if(continuousScreenshotTaking){
            return;
        }
        // Get the action from the first event and pointer
        if(null == events) events = new ArrayList<TouchEvent.MyMotionEvent>();
        events.addAll(e.getMyMotionEvents());
        ArrayList<TouchEvent.MyMotionEvent> history = e.getMyMotionEventsHistory();
        if(null != history) events.addAll(history);
        int action= e.getMyMotionEvents().get(0).action;
        if(MotionEvent.ACTION_UP == action || MotionEvent.ACTION_CANCEL == action){
            takeScreenshot(e.getActivity(), time);
        }
    }

    @Override
    public void activityCreated(ActivityLifecycleEvent activityLifecycleEvent) {}

    @Override
    public void activityStarted(ActivityLifecycleEvent activityLifecycleEvent) {}

    @Override
    public void activityPaused(ActivityLifecycleEvent activityLifecycleEvent) {
        stopCaptureTimer();
    }

    @Override
    public void activityStopped(ActivityLifecycleEvent activityLifecycleEvent) {}

    @Override
    public void activityDestroyed(ActivityLifecycleEvent activityLifecycleEvent) {}

}
