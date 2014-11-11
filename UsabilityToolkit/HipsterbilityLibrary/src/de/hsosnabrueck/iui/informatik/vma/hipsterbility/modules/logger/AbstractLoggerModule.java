package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.logger;

import android.util.Log;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.helper.Util;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.model.TestSessionEntity;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.CaptureModule;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.lifecycle.ActivityLifecycleEvent;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.lifecycle.ActivityLifecycleListener;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.lifecycle.ActivityLifecycleWatcher;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.sessions.SessionManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created on 15.05.2014.
 */
public abstract class AbstractLoggerModule implements CaptureModule, ActivityLifecycleListener {

    public final String logFileName;
    protected final String tag;
    protected boolean capture;
    protected TestSessionEntity session;
    protected BufferedWriter writer;

    protected AbstractLoggerModule(String tag, String logFileName) {
        this.tag = tag;
        this.logFileName = logFileName;
    }

    protected void writeLine(String... fields) {
        if (capture) {
            try {
                StringBuilder builder = new StringBuilder();
                builder.append(System.currentTimeMillis());
                for (String s : fields) {
                    builder.append(Util.LOGS_FIELD_SEPARATOR);
                    builder.append(s);
                }
                writer.write(builder.toString());
                writer.newLine();
                Log.i(tag, builder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void startCapture() {
        this.session = SessionManager.sessionInProgress;
        try {
            capture = true;
            writer = new BufferedWriter(
                    new FileWriter(
                            Util.createOutputDirPathName(
                                    session.getId(),
                                    Util.LOGS_DIR)
                                    + logFileName
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopCapture() {
        capture = false;
        if (writer != null) {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void pauseCapture() {
        capture = false;
        try {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resumeCapture() {
        capture = true;
    }

    @Override
    public boolean isCapturing() {
        return capture;
    }

    @Override
    public void init() {
        ActivityLifecycleWatcher.getInstance().addActivityLifecycleListener(this);
    }

    @Override
    public void activityCreated(ActivityLifecycleEvent activityLifecycleEvent) {
    }

    @Override
    public void activityStarted(ActivityLifecycleEvent activityLifecycleEvent) {
    }

    @Override
    public void activityResumed(ActivityLifecycleEvent activityLifecycleEvent) {
    }

    @Override
    public void activityPaused(ActivityLifecycleEvent activityLifecycleEvent) {
    }

    @Override
    public void activityStopped(ActivityLifecycleEvent activityLifecycleEvent) {
    }

    @Override
    public void activityDestroyed(ActivityLifecycleEvent activityLifecycleEvent) {
    }
}
