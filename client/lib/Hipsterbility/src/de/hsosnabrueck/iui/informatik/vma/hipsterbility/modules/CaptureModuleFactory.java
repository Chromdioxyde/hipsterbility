package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules;

import de.hsosnabrueck.iui.informatik.vma.hipsterbility.Hipsterbility;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.helper.Util;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.screencapture.ScreenshotModule;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.screencapture.ScreenshotModuleRoot;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Albert Hoffmann on 05.03.14.
 */
public class CaptureModuleFactory {

    private static boolean rootAvailable = Util.isDeviceRooted();
    private static boolean rootEnabled = Hipsterbility.getInstance().isRootFeaturesEnabled();
    private static ArrayList<CaptureModule> captureModules = new ArrayList<CaptureModule>();


    private static CaptureModule getAudioCaptureModule() {
//        TODO: audio capture module
        return null;
    }

    private static CaptureModule getCameraCaptureModule(boolean audio) {
        CameraCaptureModule capture = CameraCaptureModule.getInstance();
        capture.setAudioEnabled(audio);
        return capture;
    }

    private static CaptureModule getScreenCaptureModule(){
        if(rootAvailable && rootEnabled){
            return ScreenshotModuleRoot.getInstance();
        } else {
            return ScreenshotModule.getInstance();
        }
    }


    public static ArrayList<CaptureModule> getCaptureModules() {
        boolean screen = false, audio = false, lifecycle = false, video = false, touch = false;
        if(captureModules.isEmpty()){
            HashSet<Hipsterbility.MODULE> enabledModules = Hipsterbility.getInstance().getEnabledModules();
            for(Hipsterbility.MODULE m : enabledModules){
                switch (m){
                    case SCREEN:        screen = true;          break;
                    case AUDIO:         audio = true;           break;
                    case LIFECYCLE:     lifecycle = true;       break;
                    case VIDEO:         video = true;           break;
                    case TOUCH:         touch = true;           break;
                }
            }
//            Camera related modules
            if(video && audio){
                captureModules.add(getCameraCaptureModule(true));
            } else if (video){
                captureModules.add(getCameraCaptureModule(false));
            } else if (audio){
                captureModules.add(getAudioCaptureModule());
            }
//            Other modules
            if(screen){
                captureModules.add(getScreenCaptureModule());
            }
            if(touch){
                captureModules.add(TouchCaptureModule.getInstance());
            }
            if(lifecycle){
                captureModules.add(LifecycleCaptureModule.getInstance());
            }
        }
        return captureModules;
    }

}
