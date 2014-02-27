package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.Hipsterbility;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.helper.Util;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Albert Hoffmann on 21.02.14.
 * Sources: http://stackoverflow.com/questions/16748384/android-take-screenshot-programmatically-of-the-whole-screen
 *          http://stackoverflow.com/questions/2661536/how-to-programatically-take-a-screenshot-on-android?rq=1
 */
public class ScreenshotTaker implements View.OnTouchListener {

    // Some constants
    public final static String SCREENSHOTS_DIR = "screenshots";
    private final static String IMAGE_JPG = ".jpg";
    private final static String IMAGE_PNG = ".png";
    private final static String TAG = ScreenshotTaker.class.getSimpleName();

    private Session session;

    private Activity activity;

    private ArrayList<Coordinates> touches = new ArrayList<Coordinates>();

    public ScreenshotTaker(Session session, Activity activity) {
        this.session = session;
        this.activity = activity;
        this.activity.getWindow().getDecorView().getRootView().setOnTouchListener(this);
    }

/*
    /**
     *
     * TODO: remove me if not needed anymore
     * @param activity
     * @param shotsPerSecond
     * @param count
     * @param root
     */
    /*
    public void takeContinuousScreenshots(final Activity activity, final int shotsPerSecond,
                                          final int count, final boolean root){
        Runnable r = new Runnable() {
            long sleeptime = 1000/shotsPerSecond;
            @Override
            public void run() {

                for(int i = 0; i < count; i++){
                    if(root){
                        takeScreenshotRoot();
                    } else {
                        //takeScreenshot();
                    }
                    try {
                        Thread.sleep(sleeptime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(activity, "Screenshot recording finished", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        new Thread(r).start();
    }*/

    public void takeScreenshot(final float x, final float y) {
        /*
            Too much computation in the UI thread makes the app unresponsive and may lead to the app being killed by the
            systems watchdog.
            Therefore create a new thread for messing around with screenshots.
         */

        Runnable r = new Runnable(){
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


                // add drawables
                ShapeDrawable dr = new ShapeDrawable(new OvalShape());
                dr.getPaint().setColor(Color.RED);

                if( touches.size() > 0) {

                    for (int i = 0; i < touches.size(); i++) {

                        int xr = touches.get(i).getX();
                        int yr = touches.get(i).getY();

                        dr.setBounds(xr, yr, xr+50, yr+50); // TODO dynamically width and height
                        dr.draw(canvas);

                    }

                    touches.clear();

                } else {

                    Coordinates coord = new Coordinates(x,y);
                    int xr = coord.getX();
                    int yr = coord.getY();
                    dr.setBounds(xr, yr, xr+100, yr+100); // TODO dynamically width and height
                    dr.draw(canvas);
                }

                // Save the screenshot to the file system
                FileOutputStream fos = null;
                try {
                    final File file = new File(Util.createOutputDirPathName(session.getId(), SCREENSHOTS_DIR));
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    fos = new FileOutputStream(file.getAbsolutePath()+ File.separator + createOutputFileName(IMAGE_PNG));
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


    public void takeScreenshotRoot() {
        Process sh = null;
        try {
            sh = Runtime.getRuntime().exec("su", null, null);
            OutputStream os = sh.getOutputStream();
//            os.write(("/system/bin/screencap -p " + "/sdcard/img.png").getBytes("ASCII"));
            os.write(("/system/bin/screencap -p "
                    + Util.createOutputDirPathName(session.getId(), SCREENSHOTS_DIR)
                    + createOutputFileName(IMAGE_PNG)).getBytes("ASCII"));
            os.flush();
            os.close();
            sh.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private String createOutputFileName(String fileExtension) {
        return System.currentTimeMillis()
                + fileExtension;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_UP:
                Log.d(TAG, event.toString());

                // touch moved before? So we capture the previous touches
                if (touches.size() > 0) {
                    takeScreenshot(event.getX(), event.getY());

                } else {
                    takeScreenshot(event.getX(), event.getY());
                }

                break;
            case MotionEvent.ACTION_MOVE:

                this.touches.add(new Coordinates(event.getX(), event.getY()));
                Log.d(TAG, event.toString());

                break;
            default: break;
        }


        return false;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }


    /**
     * Coordinates class implements model for X and Y touch coordinates.
     */
    private class Coordinates {
        private int x, y;

        /**
         * creates a new Coordinates Object with x and y coordinates from int values.
         * @param x : int
         * @param y : int
         */
        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * creates a new Coordinates Object with x and y coordinates from float values.
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

    public void stopScreenshots(){
        unregisterTouchListener();
        //TODO: add other steps if necessary
    }

    private void unregisterTouchListener() {
        try{
            //Remove touch listener from activity's view
            this.activity.getWindow().getDecorView().getRootView().setOnTouchListener(null);
        } catch (NullPointerException e) {
           e.printStackTrace();
        }
    }
}
