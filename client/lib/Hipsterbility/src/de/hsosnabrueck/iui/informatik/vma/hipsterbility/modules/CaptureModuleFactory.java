package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules;

import android.util.Log;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.Hipsterbility;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.helper.Util;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.screencapture.ScreenshotModule;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.screencapture.ScreenshotModuleRoot;

import java.util.ArrayList;

/**
 * Created on 05.03.14.
 */
public class CaptureModuleFactory {

    private static final String TAG = CameraCaptureModule.class.getName();
    private static boolean rootAvailable = Util.isDeviceRooted();
    private static boolean rootEnabled = Hipsterbility.getInstance().isRootFeaturesEnabled();

    private static CaptureModule getCameraCaptureModule(boolean audio) {
        Log.d(TAG, "Creating camera capture module, audio: " + audio);
        CameraCaptureModule capture = CameraCaptureModule.getInstance();
        capture.setAudioEnabled(audio);
        return capture;
    }

    private static CaptureModule getScreenCaptureModule() {
        if (rootAvailable && rootEnabled) {
            Log.d(TAG, "Creating screenshot root capture module");
            return ScreenshotModuleRoot.getInstance();
        } else {
            Log.d(TAG, "Creating screenshot capture module");
            return ScreenshotModule.getInstance();
        }
    }


    public static ArrayList<CaptureModule> getCaptureModules() {
        boolean screen = Hipsterbility.MODULE.SCREEN.enabled, audio = Hipsterbility.MODULE.AUDIO.enabled, lifecycle = Hipsterbility.MODULE.LIFECYCLE.enabled, video = Hipsterbility.MODULE.VIDEO.enabled, touch = Hipsterbility.MODULE.TOUCH.enabled;
        ArrayList<CaptureModule> captureModules = new ArrayList<CaptureModule>();
        Log.d(TAG, "screen: " + Hipsterbility.MODULE.SCREEN.enabled);
        Log.d(TAG, "audio: " + audio);
        Log.d(TAG, "lifecycle: " + lifecycle);
        Log.d(TAG, "video: " + video);
        Log.d(TAG, "touch: " + touch);

//            Camera related modules
        if (video && audio) {
            captureModules.add(getCameraCaptureModule(true));
        } else if (video) {
            captureModules.add(getCameraCaptureModule(false));
        } else if (audio) {
            Log.d(TAG, "Creating audio capture module");
            captureModules.add(AudioCaptureModule.getInstance());
        }
//            Other modules
        if (screen) {
            captureModules.add(getScreenCaptureModule());
        }
        if (touch) {
            Log.d(TAG, "Creating touch capture module");
            captureModules.add(TouchCaptureModule.getInstance());
        }
        if (lifecycle) {
            Log.d(TAG, "Creating lifecycle capture module");
            captureModules.add(LifecycleCaptureModule.getInstance());
        }
        return captureModules;
    }

}
