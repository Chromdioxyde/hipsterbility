package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.screencapture;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
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
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.lifecycle.ActivityLifecycleEvent;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.lifecycle.ActivityLifecycleListener;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.lifecycle.ActivityLifecycleWatcher;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.sessions.SessionManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Albert Hoffmann on 21.02.14.
 * Sources: http://stackoverflow.com/questions/16748384/android-take-screenshot-programmatically-of-the-whole-screen
 * http://stackoverflow.com/questions/2661536/how-to-programatically-take-a-screenshot-on-android?rq=1
 */
public class ScreenshotModule implements View.OnTouchListener, CaptureModule, ActivityLifecycleListener {

    private final static String TAG = ScreenshotModule.class.getSimpleName();
    private static ScreenshotModule instance;
    private Session session;
    private Activity activity;
    private ArrayList<Coordinates> touches = new ArrayList<Coordinates>();
    private boolean recording;
    private boolean pause;

    private ScreenshotModule() {}

    public static ScreenshotModule getInstance() {
        if (instance == null) {
            instance = new ScreenshotModule();
        }
        return instance;
    }

    public void takeScreenshot(final float x, final float y, final boolean marker) {
        /*
            Too much computation in the UI thread makes the app unresponsive and may lead to the app being killed by the
            systems watchdog.
            Therefore create a new thread for messing around with screenshots.
         */
        if (pause) return;

        Runnable r = new Runnable() {
            @Override
            public void run() {
                // Get device dimmensions

                Display display = activity.getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);

                // Get root view
                View view = activity.getWindow().getDecorView().getRootView();

                // Create the bitmap to use to draw the screenshot
                Bitmap bitmap = Bitmap.createBitmap(size.x, size.y, Bitmap.Config.ARGB_4444);
                Canvas canvas = new Canvas(bitmap);

                // Get current theme to know which background to use
                final Resources.Theme theme = activity.getTheme();
                final TypedArray ta = theme
                        .obtainStyledAttributes(new int[]{android.R.attr.windowBackground});
                final int res = ta.getResourceId(0, 0);
                final Drawable background = activity.getResources().getDrawable(res);

                // Draw background
                background.draw(canvas);

                // Draw views
                view.draw(canvas);

                if(marker) {
                    // add drawables
                    ShapeDrawable dr = new ShapeDrawable(new OvalShape());
                    dr.getPaint().setColor(Color.RED);

                    if (touches.size() > 0) {

                        for (int i = 0; i < touches.size(); i++) {

                            int xr = touches.get(i).getX();
                            int yr = touches.get(i).getY();

                            dr.setBounds(xr, yr, xr + 50, yr + 50); // TODO dynamically width and height
                            dr.draw(canvas);

                        }

                        touches.clear();

                    } else {

                        Coordinates coord = new Coordinates(x, y);
                        int xr = coord.getX();
                        int yr = coord.getY();
                        dr.setBounds(xr, yr, xr + 100, yr + 100); // TODO dynamically width and height
                        dr.draw(canvas);
                    }
                }

                // Save the screenshot to the file system
                FileOutputStream fos = null;
                try {
                    final File file = new File(Util.createOutputDirPathName(session.getId(), Util.SCREENSHOTS_DIR));
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    fos = new FileOutputStream(file.getAbsolutePath() + File.separator + createOutputFileName(Util.IMAGE_PNG));
                    if (fos != null) {
                        if (!bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos)) {
                            Log.d(TAG, "Compress/Write failed");
                        }
                        fos.flush();
                        fos.close();
                    }

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        new Thread(r).start();

    }

    private String createOutputFileName(String fileExtension) {
        return System.currentTimeMillis()
                + fileExtension;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_UP:
                Log.d(TAG, event.toString());

                // touch moved before? So we capture the previous touches
                if (touches.size() > 0) {
                    takeScreenshot(event.getX(), event.getY(), true);

                } else {
                    takeScreenshot(event.getX(), event.getY(), true);
                }

                break;
            case MotionEvent.ACTION_MOVE:

                this.touches.add(new Coordinates(event.getX(), event.getY()));
                Log.d(TAG, event.toString());

                break;
            default:
                break;
        }

        return false;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void startCapture() {
        this.session = SessionManager.getInstace().getSessionInProgress();
        this.activity = Hipsterbility.getInstance().getActivity();

        takeScreenshot(0, 0, false);
    }

    @Override
    public void stopCapture() {
        stopScreenshots();
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

    public void stopScreenshots() {
        unregisterTouchListener();
        //TODO: add other steps if necessary
    }

    private void unregisterTouchListener() {
        try {
            //Remove touch listener from activity's view
            this.activity.getWindow().getDecorView().getRootView().setOnTouchListener(null);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void activityCreated(ActivityLifecycleEvent activityLifecycleEvent) {

    }

    @Override
    public void activityStarted(ActivityLifecycleEvent activityLifecycleEvent) {

    }

    @Override
    public void activityResumed(ActivityLifecycleEvent activityLifecycleEvent) {
        this.activity = activityLifecycleEvent.getActivity();
        registerTouchListener();
    }

    private void registerTouchListener() {
        this.activity.getWindow().getDecorView().getRootView().setOnTouchListener(this);
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

    /**
     * Coordinates class implements model for X and Y touch coordinates.
     */
    private class Coordinates {
        private int x, y;

        /**
         * creates a new Coordinates Object with x and y coordinates from int values.
         *
         * @param x : int
         * @param y : int
         */
        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * creates a new Coordinates Object with x and y coordinates from float values.
         *
         * @param x : float
         * @param y : float
         */
        public Coordinates(float x, float y) {
            this.x = Math.round(x);
            this.y = Math.round(y);
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

    }
}
