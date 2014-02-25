package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Toast;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.Hipsterbility;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.helper.Util;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;

import java.io.*;

/**
 * Created by Albert Hoffmann on 21.02.14.
 * Sources: http://stackoverflow.com/questions/16748384/android-take-screenshot-programmatically-of-the-whole-screen
 *          http://stackoverflow.com/questions/2661536/how-to-programatically-take-a-screenshot-on-android?rq=1
 */
public class ScreenshotTaker {

    // Some constants
    public final static String SCREENSHOTS_DIR = "screenshots";
    private final static String IMAGE_JPG = ".jpg";
    private final static String IMAGE_PNG = ".png";
    private final static String TAG = ScreenshotTaker.class.getSimpleName();

    private Session session;

    public ScreenshotTaker(Session session) {
        this.session = session;
    }

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
                        takeScreenshot(activity);
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
    }

    public void takeScreenshot(Activity activity) {

        // Get device dimmensions

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Get root view
        View view = activity.getWindow().getDecorView().getRootView();

        // Create the bitmap to use to draw the screenshot
        final Bitmap bitmap = Bitmap.createBitmap(size.x, size.y, Bitmap.Config.ARGB_4444);
        final Canvas canvas = new Canvas(bitmap);

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

        // Save the screenshot to the file system
        FileOutputStream fos = null;
        try {
            final File file = new File(Util.createOutputDirPathName(session, SCREENSHOTS_DIR));
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

    public void takeScreenshotRoot() {
        Process sh = null;
        try {
            sh = Runtime.getRuntime().exec("su", null, null);
            OutputStream os = sh.getOutputStream();
//            os.write(("/system/bin/screencap -p " + "/sdcard/img.png").getBytes("ASCII"));
            os.write(("/system/bin/screencap -p "
                    + Util.createOutputDirPathName(session, SCREENSHOTS_DIR)
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



}
