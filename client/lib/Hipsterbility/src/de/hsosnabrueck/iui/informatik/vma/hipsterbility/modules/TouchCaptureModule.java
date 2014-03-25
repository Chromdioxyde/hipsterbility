package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules;

/**
 * Created by Albert on 25.03.2014.
 */
public class TouchCaptureModule implements CaptureModule {

    private static TouchCaptureModule instance;

    private TouchCaptureModule(){}

    public static TouchCaptureModule getInstance(){
        if(instance == null) instance = new TouchCaptureModule();
        return instance;
    }


    @Override
    public void startCapture() {
//        TODO: implement
    }

    @Override
    public void stopCapture() {
//        TODO: implement
    }

    @Override
    public void pauseCapture() {
//        TODO: implement
    }

    @Override
    public void resumeCapture() {
//        TODO: implement
    }

    @Override
    public boolean isCapturing() {
        return false;
    }
}
