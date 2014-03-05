package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules;

import android.provider.MediaStore;

/**
 * Created by Albert Hoffmann on 05.03.14.
 * Interface to unified methods for capture control.
 */
public interface CaptureModule {

    public static int TYPE_SCREEN                   = 0;
    public static int TYPE_CAMERA                   = 1;
    public static int TYPE_TOUCH_INPUT              = 3;
    public static int TYPE_CAMERA_WITHOUT_AUDIO     = 4;
    public static int TYPE_AUDIO                    = 5;

    public void startCapture();
    public void stopCapture();
    public void pauseCapture();
    public void resumeCapture();

    public boolean isCapturing();
}
