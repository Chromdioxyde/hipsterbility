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
import de.hsosnabrueck.hipsterbility.model.enums.DevicePlatform;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.model.DeviceEntity;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.model.TestSessionEntity;

import java.io.File;

/**
 * Created on 25.02.14.
 * Class for various utility methods that are being user by several classes.
 */
public class Util {

    private static final String TAG = Util.class.getSimpleName();

    // Directory name constants.
    public static final String BASE_DIR = "hipsterbility";
    public final static String SCREENSHOTS_DIR = "screenshots";
    public static final String VIDEOS_DIR = "videos";
    public static final String AUDIO_DIR = "audio";
    public static final String LOGS_DIR = "logs";
    // File extension constants.
    public final static String IMAGE_JPG = ".jpg";
    public final static String IMAGE_PNG = ".png";
    public static final String VIDEO_FILE_EXTENSION = ".mp4";
    public static final String AUDIO_FILE_EXTENSSION = ".aac";
    // URL suffix constants.
    public static final String URL_SUFFIX_CAMERA = "videos/";
    public static final String URL_SUFFIX_CAPTURES = "captures/"; // for screenshots
    // Misc. constants.
    public static final String LOGS_FIELD_SEPARATOR = ",";

    /**
     * Creates an absolute directory path based on provided arguments.
     *
     * @param sessionId id for the current Session.
     * @param subdir name of the subdirectory.
     * @return String containing the absolute subdirectory path for output data.
     */
    public static String createOutputDirPathName(long sessionId, String subdir) {
        return createSessionDirPathName(sessionId)
                + subdir
                + File.separator;
    }

    /**
     * Creates a String containing an absolute path where data for the Session shall be stored.
     *
     * @param sessionId id for the current Session.
     * @return absolute path as String.
     */
    public static String createSessionDirPathName(long sessionId) {
        return Environment.getExternalStorageDirectory()
                + File.separator
                + BASE_DIR
                + File.separator
                + sessionId
                + File.separator;
    }

    /**
     * Creates (missing) directories for the given file path String.
     *
     * @param path String containing an absolute path.
     * @return true if directories were created, else false.
     */
    public static boolean makeDirectoriesForPath(String path){
        return new File(path).mkdirs();
    }

    /**
     * Method to create the full output path for a file as String.
     *
     * @param sessionId     long Session id for session subdir.
     * @param subdir        Sub directory for File types. Constants provided by this class.
     * @param fileExtension File extension for output file. Constants provided by this class.
     * @return
     */
    public static String getFullFilePath(long sessionId, String subdir, String filename, String fileExtension) {
        return createOutputDirPathName(sessionId, subdir)
                + filename + fileExtension;
    }

    /**
     * Creates a relative route which can be attached to the base URL of the server to create a full URL string.
     *
     * @param s Session for id.
     * @param suffix last (custom) URL path String
     * @return String containing the route.
     */
    public static String createRelativeRoute(TestSessionEntity s, String suffix) {
        return "/sessions/" + s.getId() + "/" + suffix;
    }

    /**
     * Tries to determine if the device is rooted in several ways, because it is possible that single methods fail.
     * Source: http://stackoverflow.com/questions/1101380/determine-if-running-on-a-rooted-device
     *
     * @return true if at least one test was successful and the device is rooted, false if all tests failed.
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

    /**
     * Collects device properties, provided by the Android (build) config.
     * The data can be used to classify the phone or match it with a given phone in database.
     *
     * @param context Android Context (Activity, Application...)
     * @return Device object containing gathered information.
     */
    public static DeviceEntity getDeviceInfo(Context context) {
        DeviceEntity device = new DeviceEntity();

        // "Unique" ID (may change after factory reset)
        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        device.setUuid(android_id);

        // Basic information provided by the Build class
        device.setName(Build.DEVICE);
//        device.setBrand(Build.BRAND);
//        device.setModel(Build.MODEL);
//        device.setManufacturer(Build.MANUFACTURER);
//        device.setRomVersion(Build.PRODUCT);
//        device.setBuildNumber(Build.ID);
        device.setOsVersion(Build.VERSION.RELEASE);
        device.setPlatform(DevicePlatform.ANDROID);

        // Additional configuration parameters
        Configuration conf = context.getResources().getConfiguration();
//        device.setLocale(conf.locale);

        // Display metrics
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getRealMetrics(outMetrics);

//        device.setScreenWidthInPixels(outMetrics.widthPixels);
//        device.setScreenHeightInPixels(outMetrics.heightPixels);

        // Calculate screen diameter (may not be very precise)
        double x = outMetrics.heightPixels / outMetrics.xdpi;
        double y = outMetrics.widthPixels / outMetrics.ydpi;
        double screenInches = Math.sqrt(x * x + y * y);
//        device.setScreenSizeInInch(screenInches);

        // Estimate device class
//        device.setScreenLayoutSize(estimateScreenLayoutSize(context));
//        device.setDeviceClass(estimateDeviceClass(device));

        Log.i(TAG, device.toString());
        return device;
    }

    /**
     * Returns the used layout short name of the currently used layout
     *
     * @param context Android Context (Activity, Application...)
     * @return String with basic layout name ("xlarge","large",...)
     */
    public static int estimateScreenLayoutSize(Context context) {
        int layout = context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        return layout;
    }

    /**
     * Tries to classify the given device into basic classes like tablet, phablet and phone.
     *
     * @param device the device to be estimated
     * @return Devce.DeviceType which matches basic classification properties or DeviceType.OTHER for no match.
     */
//    public static DeviceClass estimateDeviceClass(DeviceEntity device, double screensize, ) {
//        double screenSize = device.getScreenSizeInInch();
//        int layout = device.getScreenLayoutSize();
//        if (screenSize >= 7.0 || layout == Device.ScreenLayoutSize.XLARGE) {
//            return Device.DeviceType.TABLET;
//        } else if ((7.0 > screenSize || 5.0 <= screenSize) || layout == Device.ScreenLayoutSize.LARGE) {
//            return Device.DeviceType.PHABLET;
//        } else if (layout == Device.ScreenLayoutSize.NORMAL || layout == Device.ScreenLayoutSize.SMALL) {
//            return Device.DeviceType.PHONE;
//        } else return Device.DeviceType.OTHER;
//    }

    /**
     * (Source http://stackoverflow.com/a/9563438)
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }

    /**
     * (Source: http://stackoverflow.com/a/9563438)
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return px / (metrics.densityDpi / 160f);
    }

}
