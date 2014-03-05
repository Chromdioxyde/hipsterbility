package de.hsosnabrueck.iui.informatik.vma.hipsterbility.helper;

import android.os.Environment;
import android.util.Log;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.Hipsterbility;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Albert Hoffmann on 25.02.14.
 * Class for various utility methods that are being user by several classes.
 */
public class Util {

    public static final String URL_SUFFIX_CAMERA = "videos/";
    public static final String URL_SUFFIX_CAPTURES = "captures/"; // for screenshots
    private static final String TAG = Util.class.getName();

    public static String createOutputDirPathName(long sessionId, String subdir) {
       String path =  createSessionDirPathName(sessionId)
                + subdir
                + File.separator;
        new File(path).mkdirs();
        return path;
    }

    public static String createSessionDirPathName(long sessionId){
        String path =  Environment.getExternalStorageDirectory()
                + File.separator
                + Hipsterbility.BASE_DIR
                + File.separator
                + sessionId
                + File.separator;
        new File(path).mkdirs();
        return path;
    }

    public static String createRelativeRoute(User u, Session s, String suffix){
        return u.getId() + "/" + s.getId() + "/" + suffix;
    }

    /**
     * Tries to determine if the device is rooted in several ways, because it is possible that single methods fail.
     * Source: http://stackoverflow.com/questions/1101380/determine-if-running-on-a-rooted-device
     *
     * @return True if the Test was successful and the device is rooted, false if all tests failed.
     */
    public static boolean isDeviceRooted(){
        String buildTags = android.os.Build.TAGS;
        if(buildTags != null && buildTags.contains("test-keys")){
            Log.d(TAG, "Build Tags contain 'test-keys', Device is probably rooted");
            return true;
        } else {
            Log.d(TAG, "No clue for root in build tags");
        }
        try {
            File file = new File("/system/app/Superuser.apk");
            if(file.exists()){
                Log.d(TAG, "Superuser.apk has been found, device may be rooted");
                return true;
            }
        } catch (Exception e) {
            Log.d(TAG, "Superuser.apk not found.");
        }
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            Log.d(TAG, "su command executed successfully, device is rooted");
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (process != null) {
                try {
                    process.destroy();
                } catch (Exception e) {
                    Log.d(TAG, "su Binary not found.");
                }
            }
        }
        Log.d(TAG, "Device is probably not rooted");
        return false;
    }
}
