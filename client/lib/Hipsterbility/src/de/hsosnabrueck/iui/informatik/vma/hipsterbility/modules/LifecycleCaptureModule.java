package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules;

/**
 * Created by Albert on 25.03.2014.
 */
public class LifecycleCaptureModule implements CaptureModule {

    private static LifecycleCaptureModule instance;

    private LifecycleCaptureModule() {}

    public static LifecycleCaptureModule getInstance(){
        if(instance == null) instance = new LifecycleCaptureModule();
        return instance;
    }


    @Override
    public void startCapture() {

    }

    @Override
    public void stopCapture() {

    }

    @Override
    public void pauseCapture() {

    }

    @Override
    public void resumeCapture() {

    }

    @Override
    public boolean isCapturing() {
        return false;
    }
}
