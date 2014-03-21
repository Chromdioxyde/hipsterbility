package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules;

import de.hsosnabrueck.iui.informatik.vma.hipsterbility.Hipsterbility;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.helper.Util;

import java.util.ArrayList;

/**
 * Created by Albert Hoffmann on 05.03.14.
 */
public class CaptureModuleFactory {

    private static boolean rootAvailable = Util.isDeviceRooted();
    private static boolean rootEnabled = false;
    private static ArrayList<CaptureModule> captureModules;

    public static CaptureModule getCaptureModule(int type){
        CaptureModule module = null;
        switch(type){
            case CaptureModule.TYPE_SCREEN: module = getScreenCaptureModule();
                break;
            case CaptureModule.TYPE_CAMERA: module = getCameraCaptureModule(true);
                break;
        }
        return module;
    }

    private static CaptureModule getCameraCaptureModule(boolean audio) {
        CameraCaptureModule capture = CameraCaptureModule.getInstance();
        capture.setAudioEnabled(audio);
        return capture;
    }

    private static CaptureModule getScreenCaptureModule(){
        if(rootAvailable){
            return ScreenshotTaker.getInstance();
        }
        //TODO: change to real Class;
        return null;
    }


    public static ArrayList<CaptureModule> getCaptureModules() {
        return captureModules;
    }

    public static void setRootEnabled(boolean val){
        rootEnabled = val;
    }
}
