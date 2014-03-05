package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.sessions.SessionManager;

import java.io.File;
import java.io.IOException;

/**
 * Created by Albert Hoffmann on 13.02.14.
 *
 * Example: http://developer.android.com/guide/topics/media/audio-capture.html
 * The application needs to have the permission to write to external storage
 * if the output file is written to the external storage, and also the
 * permission to record audio. These permissions must be set in the
 * application's AndroidManifest.xml file, with something like:
 *
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.RECORD_AUDIO" />
 *
 */

public class AudioCaptureModule implements CaptureModule{
    // TODO: Implementation

    //================================================================================
    // Properties
    //================================================================================

    private final String TAG = this.getClass().getCanonicalName();
    public static final String AUDIO_DIR = "audio";
    public static final String AUDIO_FILE_EXTENSSION = "";

    private String mFileName = null;
    private MediaRecorder mRecorder = null;
    private File outFile = null;
    private Session session;

    public AudioCaptureModule() {
        this.session = SessionManager.getInstace().getSessionInProgress();
    }


    @Override
    public void startCapture() {
        // TODO change path to a unified one
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + File.pathSeparator + "Hipsterbility"
                + File.pathSeparator + session.getId() + "_" + System.currentTimeMillis() + ".3gp";

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // TODO change output format if necessary
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
        mRecorder.start();
    }

    @Override
    public void stopCapture() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
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

    @Override
    public boolean isEnabled() {
        return false;
    }

    //================================================================================
    // Private Methods
    //================================================================================

}
