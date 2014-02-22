package de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.modules;

import android.app.Activity;
import android.content.Context;
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
import android.view.WindowManager;

import java.io.*;

/**
 * Created by albert on 21.02.14.
 */
public class ScreenshotTaker {



    // Some constants
    final static String SCREENSHOTS_LOCATIONS = Environment.getExternalStorageDirectory().toString() + "/screenshots/";
    private final static String TAG = ScreenshotTaker.class.getSimpleName();


    protected void takeScreenshot(Activity activity) {

// Get device dimmensions

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

// Get root view
        View view = activity.getWindow().getDecorView().findViewById(android.R.id.content).getRootView();

// Create the bitmap to use to draw the screenshot
        final Bitmap bitmap = Bitmap.createBitmap(size.x, size.y, Bitmap.Config.ARGB_4444);
        final Canvas canvas = new Canvas(bitmap);

// Get current theme to know which background to use
        final Resources.Theme theme = activity.getTheme();
        final TypedArray ta = theme
                .obtainStyledAttributes(new int[] { android.R.attr.windowBackground });
        final int res = ta.getResourceId(0, 0);
        final Drawable background = activity.getResources().getDrawable(res);

// Draw background
        background.draw(canvas);

// Draw views
        view.draw(canvas);

// Save the screenshot to the file system
        FileOutputStream fos = null;
        try {
            final File sddir = new File(SCREENSHOTS_LOCATIONS);
            if (!sddir.exists()) {
                sddir.mkdirs();
            }
            fos = new FileOutputStream(SCREENSHOTS_LOCATIONS
                    + System.currentTimeMillis() + ".jpg");
            if (fos != null) {
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos)) {
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

    private void takeScreenshotRoot(){
        Process sh = null;
        try {
            sh = Runtime.getRuntime().exec("su", null,null);
            OutputStream os = sh.getOutputStream();
            os.write(("/system/bin/screencap -p " + "/sdcard/img.png").getBytes("ASCII"));
            os.flush();
            os.close();
            sh.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
