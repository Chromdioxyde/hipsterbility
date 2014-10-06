package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.screencapture;

import de.hsosnabrueck.hipsterbility.entities.TestSessionEntity;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.helper.Util;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.CaptureModule;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.sessions.SessionManager;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.tests.TestManager;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Albert on 25.03.2014.
 */
public class ScreenshotModuleRoot implements CaptureModule {

    private static ScreenshotModuleRoot instance;
    private TestSessionEntity session;

    public static ScreenshotModuleRoot getInstance() {
        if (instance == null) instance = new ScreenshotModuleRoot();
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
//        TODO: implement
        return false;
    }

    @Override
    public void init() {
        this.session = SessionManager.sessionInProgress;
    }


    public void takeScreenshotRoot() {
        Process sh;
        try {
            String time = String.valueOf(System.nanoTime());
            sh = Runtime.getRuntime().exec("su", null, null);
            OutputStream os = sh.getOutputStream();
            os.write(("/system/bin/screencap -p "
                    + Util.getFullFilePath(session.getId(), Util.SCREENSHOTS_DIR, time, Util.IMAGE_PNG)).getBytes("ASCII"));
            os.flush();
            os.close();
            sh.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
