package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules;

import android.media.MediaRecorder;
import android.util.Log;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.helper.Util;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.sessions.SessionManager;

import java.io.IOException;

/**
 * Created by Albert Hoffmann on 13.02.14.
 * <p/>
 * Example: http://developer.android.com/guide/topics/media/audio-capture.html
 * The application needs to have the permission to write to external storage
 * if the output file is written to the external storage, and also the
 * permission to record audio. These permissions must be set in the
 * application's AndroidManifest.xml file, with something like:
 * <p/>
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.RECORD_AUDIO" />
 */

public class AudioCaptureModule implements CaptureModule {
    // TODO: Implementation

    //================================================================================
    // Properties
    //================================================================================
    private static AudioCaptureModule instance;

    private final String TAG = this.getClass().getCanonicalName();

    private MediaRecorder mRecorder = null;
    private boolean recording = false;
    private Session session;

    private AudioCaptureModule() {
    }

    public static AudioCaptureModule getInstance() {
        if (instance == null) instance = new AudioCaptureModule();
        return instance;
    }

    @Override
    public void startCapture() {

        // TODO change path to a unified one
        String mFileName = Util.createOutputFileAbsolutePathName(session.getId(), Util.AUDIO_DIR, Util.AUDIO_FILE_EXTENSSION);

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // TODO change output format if necessary
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        try {
            mRecorder.prepare();
            mRecorder.start();
            recording = true;
        } catch (IOException e) {
            Log.e(TAG, "MediaRecorder prepare() failed");
        }
    }

    @Override
    public void stopCapture() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        recording = false;
    }

    @Override
    public void pauseCapture() {
        stopCapture();
    }

    @Override
    public void resumeCapture() {
        startCapture();
    }

    @Override
    public boolean isCapturing() {
        return recording;
    }

    @Override
    public void init() {
        session = SessionManager.getInstace().getSessionInProgress();
    }

}
