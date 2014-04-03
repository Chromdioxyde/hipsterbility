package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.util.Log;
import android.view.*;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.Hipsterbility;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.helper.Util;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.sessions.SessionManager;

import java.lang.reflect.Method;

/**
 * Created by Albert Hoffmann on 17.02.14.
 */
public class CameraCaptureModule implements SurfaceHolder.Callback, CaptureModule {

    public static final String VIDEOS_DIR = "videos";
    public static final String VIDEO_FILE_EXTENSION = ".mp4";
    private final static String TAG = CameraCaptureModule.class.getName();
    private static CameraCaptureModule instance;
    private WindowManager windowManager;
    private SurfaceView surfaceView;
    private Camera camera = null;
    private MediaRecorder mediaRecorder = null;
    //    private CaptureService service = null;
//    private Surface fakeSurface = null;
//    private boolean recording = false;
    private Session session;
    private Activity activity;
    private boolean audioEnabled;
    private int cameraNumber;
    private Context context;

    private CameraCaptureModule() {}

    public static CameraCaptureModule getInstance() {
        if (instance == null) {
            instance = new CameraCaptureModule();
        }
        return instance;
    }

    @Override
    public void startCapture() {
        //Todo: impove activity tracking
        this.activity = Hipsterbility.getInstance().getActivity();
        windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        this.context = activity.getApplicationContext();
        // Create new SurfaceView, set its size to 1x1, move it to the top left corner and set this service as a callback
        surfaceView = new SurfaceView(context);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                1, 1,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT
        );
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        windowManager.addView(surfaceView, layoutParams);

        boolean found = false;
        int i;
        for (i = 0; i < Camera.getNumberOfCameras(); i++) {
            Camera.CameraInfo newInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, newInfo);
            if (newInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                found = true;
                break;
            }
        }
        cameraNumber = i;

        camera = Camera.open(cameraNumber);
       /*
       // set Rotation for portrait display orientation
        // TODO follow display orientation on change
        camera.setDisplayOrientation(270);
        camera.getParameters().setRotation(270);
        */
        setCameraDisplayOrientation(camera);
        camera.unlock();
        surfaceView.getHolder().addCallback(this);
    }

    @Override
    public void stopCapture() {
        if (mediaRecorder == null) {
            return;
        }
        try {
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();

            camera.lock();
            camera.release();
            windowManager.removeView(surfaceView);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        ;
    }

    @Override
    public void pauseCapture() {
        //TODO: test pause and resume
        mediaRecorder.stop();
    }

    @Override
    public void resumeCapture() {
        setRecorderOutputFile();
        mediaRecorder.start();
    }

    @Override
    public boolean isCapturing() {
        return false;
    }

    @Override
    public void init() {
        this.session = SessionManager.getInstace().getSessionInProgress();
    }

    public boolean isAudioEnabled() {
        return audioEnabled;
    }

    public void setAudioEnabled(boolean audioEnabled) {
        this.audioEnabled = audioEnabled;
    }

    private void setCameraDisplayOrientation(android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraNumber, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        setDisplayOrientation(camera, result);
    }

    protected void setDisplayOrientation(Camera camera, int angle) {
        Method downPolymorphic;
        try {
            downPolymorphic = camera.getClass().getMethod("setDisplayOrientation", new Class[]{int.class});
            if (downPolymorphic != null)
                downPolymorphic.invoke(camera, new Object[]{angle});
        } catch (Exception e1) {
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setOrientationHint(270);

        mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
        mediaRecorder.setCamera(camera);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mediaRecorder.setProfile(CamcorderProfile.get(cameraNumber, CamcorderProfile.QUALITY_HIGH));

        setRecorderOutputFile();

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (Exception e) {
            Log.e(TAG, "MediaRecorder preparation failed: " + e.getMessage());
        }
    }

    private void setRecorderOutputFile() {
        mediaRecorder.setOutputFile(
                Util.createOutputDirPathName(this.session.getId(), VIDEOS_DIR) + System.currentTimeMillis() + VIDEO_FILE_EXTENSION
        );
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }
}
