package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.screencapture;

import android.util.Log;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.helper.Util;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.model.TestSessionEntity;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.CaptureModule;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.sessions.SessionManager;

import java.io.IOException;

/**
 * Created on 25.02.14.
 */
public class ScreenRecorder implements CaptureModule {

    private static final String TAG = ScreenRecorder.class.getName();
    private final static String VIDEO_FILE_EXTENSION = ".mp4";
    private static final String SCREEN_CAPUTRE_DIR = "screencapture";
    private static ScreenRecorder instance;
    private boolean recording = false;
    private Process captureProcess;

    private ScreenRecorder() {

    }

    public static ScreenRecorder getInstance() {
        if (instance == null) instance = new ScreenRecorder();
        return instance;
    }

    @Override
    public void startCapture() {
        TestSessionEntity session = SessionManager.sessionInProgress;
        try {
            Process suProcess = Runtime.getRuntime().exec("su");
            captureProcess = Runtime.getRuntime().exec("screenrecord "
                    + Util.createOutputDirPathName(session.getId(), SCREEN_CAPUTRE_DIR)
                    + System.currentTimeMillis() + VIDEO_FILE_EXTENSION);
            Log.d(TAG, "Starting screenrecord");
            recording = true;
        } catch (IOException e) {
//            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void stopCapture() {
        if (captureProcess != null) {
            captureProcess.destroy();
            Log.d(TAG, "Stopping screenrecord: " + captureProcess.exitValue());
        }
        recording = false;
    }

    @Override
    public void pauseCapture() {
        stopCapture();
    }

    @Override
    public void resumeCapture() {
        if (captureProcess == null) startCapture();
    }

    @Override
    public boolean isCapturing() {
        return recording;
    }

    @Override
    public void init() {

    }

}
