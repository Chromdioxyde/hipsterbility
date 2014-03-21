package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules;

import de.hsosnabrueck.iui.informatik.vma.hipsterbility.helper.Util;

/**
 * Created by Albert Hoffmann on 05.03.14.
 */
public class CaptureModuleFactory {

    private static boolean root = Util.isDeviceRooted();

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
        if(root){
            return ScreenshotTaker.getInstance();
        }
        //TODO: change to real Class;
        return null;
    }
}
