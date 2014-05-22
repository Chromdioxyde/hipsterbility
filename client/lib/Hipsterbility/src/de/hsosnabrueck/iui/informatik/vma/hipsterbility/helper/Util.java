package de.hsosnabrueck.iui.informatik.vma.hipsterbility.helper;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Device;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.User;

import java.io.File;

/**
 * Created on 25.02.14.
 * Class for various utility methods that are being user by several classes.
 */
public class Util {

    public static final String BASE_DIR = "hipsterbility";

    public final static String SCREENSHOTS_DIR = "screenshots";
    public final static String IMAGE_JPG = ".jpg";
    public final static String IMAGE_PNG = ".png";

    public static final String VIDEOS_DIR = "videos";
    public static final String VIDEO_FILE_EXTENSION = ".mp4";

    public static final String AUDIO_DIR = "audio";
    public static final String AUDIO_FILE_EXTENSSION = ".aac";

    public static final String LOGS_DIR = "logs";
    public static final String LOGS_FIELD_SEPARATOR = ",";

    public static final String URL_SUFFIX_CAMERA = "videos/";
    public static final String URL_SUFFIX_CAPTURES = "captures/"; // for screenshots
    private static final String TAG = Util.class.getSimpleName();

    public static String createOutputDirPathName(long sessionId, String subdir) {
        String path = createSessionDirPathName(sessionId)
                + subdir
                + File.separator;
        new File(path).mkdirs();
        return path;
    }

    public static String createSessionDirPathName(long sessionId) {
        String path = Environment.getExternalStorageDirectory()
                + File.separator
                + BASE_DIR
                + File.separator
                + sessionId
                + File.separator;
        new File(path).mkdirs();
        return path;
    }

    /**
     * Method to create the full output path for a file as String.
     *
     * @param sessionId     long Session id for session subdir.
     * @param subdir        Sub directory for File types. Constants provided by this class.
     * @param fileExtension File extension for output file. Constants provided by this class.
     * @return
     */
    public static String createOutputFileAbsolutePathName(long sessionId, String subdir, String fileExtension) {
        return createOutputDirPathName(sessionId, subdir) + System.currentTimeMillis() + fileExtension;
    }

    public static String createRelativeRoute(User u, Session s, String suffix) {
        return "/" + u.getId() + "/" + s.getId() + "/" + suffix;
    }

    /**
     * Tries to determine if the device is rooted in several ways, because it is possible that single methods fail.
     * Source: http://stackoverflow.com/questions/1101380/determine-if-running-on-a-rooted-device
     *
     * @return true if the Test was successful and the device is rooted, false if all tests failed.
     */
    public static boolean isDeviceRooted() {
        String buildTags = android.os.Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            Log.d(TAG, "Build Tags contain 'test-keys', Device is probably rooted");
            return true;
        } else {
            Log.d(TAG, "No clue for root in build tags");
        }
        try {
            File file = new File("/system/app/Superuser.apk");
            if (file.exists()) {
                Log.d(TAG, "Superuser.apk has been found, device may be rooted");
                return true;
            } else {
                Log.d(TAG, "Superuser.apk not found.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error while looking for Superuser.apk: " + e.getMessage());
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

    public static Device getDeviceInfo(Context context) {
        Device device = new Device();

        // "Unique" ID (may change after factory reset)
        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        device.setAndroidId(android_id);

        // Basic information provided by the Build class
        device.setName(Build.DEVICE);
        device.setBrand(Build.BRAND);
        device.setModel(Build.MODEL);
        device.setManufacturer(Build.MANUFACTURER);
        device.setRomVersion(Build.PRODUCT);
        device.setBuildNumber(Build.ID);
        device.setAndroidVersion(Build.VERSION.RELEASE);

        // Additional configuration parameters
        Configuration conf = context.getResources().getConfiguration();
        device.setLocale(conf.locale);

        // Display metrics
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getRealMetrics(outMetrics);

        device.setScreenWidthInPixels(outMetrics.widthPixels);
        device.setScreenHeightInPixels(outMetrics.heightPixels);

        // Calculate screen diameter (may not be very precise)
        double x = outMetrics.heightPixels / outMetrics.xdpi;
        double y = outMetrics.widthPixels / outMetrics.ydpi;
        double screenInches = Math.sqrt(x * x + y * y);
        device.setScreenSizeInInch(screenInches);

        // Estimate device class
        device.setScreenLayoutSize(estimateScreenLayoutSize(context));
        device.setType(estimateDeviceClass(device));

        Log.i(TAG, device.toString());
        return device;
    }

    /**
     * Returns the used layout short name of the currently used layout
     *
     * @param context Android Context (Activity, Application...)
     * @return String with basic layout name ("xlarge","large",...)
     */
    public static Device.ScreenLayoutSize estimateScreenLayoutSize(Context context) {
        int layout = context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        switch (layout) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                return Device.ScreenLayoutSize.XLARGE;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                return Device.ScreenLayoutSize.LARGE;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                return Device.ScreenLayoutSize.NORMAL;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                return Device.ScreenLayoutSize.SMALL;
            default:
                return Device.ScreenLayoutSize.UNDEFINED;
        }
    }

    /**
     * Tries to classify the given device into basic classes like tablet, phablet and phone.
     *
     * @param device the device to be estimated
     * @return Devce.DeviceType which matches basic classification properties or DeviceType.OTHER for no match.
     */
    public static Device.DeviceType estimateDeviceClass(Device device) {
        double screenSize = device.getScreenSizeInInch();
        Device.ScreenLayoutSize layout = device.getScreenLayoutSize();
        if (screenSize >= 7.0 || layout == Device.ScreenLayoutSize.XLARGE) {
            return Device.DeviceType.TABLET;
        } else if (screenSize < 7.0 && layout == Device.ScreenLayoutSize.LARGE) {
            return Device.DeviceType.PHABLET;
        } else if (layout == Device.ScreenLayoutSize.NORMAL || layout == Device.ScreenLayoutSize.SMALL) {
            return Device.DeviceType.PHONE;
        } else return Device.DeviceType.OTHER;
    }

}
