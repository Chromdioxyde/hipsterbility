package de.hsosnabrueck.iui.informatik.vma.hipsterbility.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import de.hsosnabrueck.iui.informatik.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.HipsterbilityBroadcastReceiver;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.helper.Util;

/**
 * Created by Albert Hoffmann on 13.02.14.
 */
public class CaptureService extends Service implements SurfaceHolder.Callback{

    private static final String TAG = CaptureService.class.getName();

    public static final String VIDEOS_DIR = "videos";
    public static final String VIDEO_FILE_EXTENSION = ".mp4";
    private long sessionId;
//    private ArrayList<AbstractModule> modules;

    private WindowManager windowManager;
    private SurfaceView surfaceView;
    private Camera camera = null;
    private MediaRecorder mediaRecorder = null;


    public CaptureService(){
//        this.modules = new ArrayList<AbstractModule>();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Start foreground service to avoid unexpected kill
        Intent intent = new Intent();
        intent.setAction(HipsterbilityBroadcastReceiver.ACTION_STOP_CAPTURE);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        Notification notification = new Notification.Builder(this)
                .setContentTitle("Background Video Recorder")
                .setContentText("")
                .setSmallIcon(R.drawable.ic_notification_recording)
                .addAction(R.drawable.ic_action_stop_recording, "Stop Recording", pIntent)
                .build();
        startForeground(1234, notification);

        // Create new SurfaceView, set its size to 1x1, move it to the top left corner and set this service as a callback
        windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        surfaceView = new SurfaceView(this);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                1, 1,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT
        );
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        windowManager.addView(surfaceView, layoutParams);
        surfaceView.getHolder().addCallback(this);

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        boolean found = false;
        int i;
        for (i=0; i< Camera.getNumberOfCameras(); i++) {
            Camera.CameraInfo newInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, newInfo);
            if (newInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                found = true;
                break;
            }
        }
        camera = Camera.open(i);
        mediaRecorder = new MediaRecorder();

        camera.unlock();

        mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
        mediaRecorder.setCamera(camera);
        camera.setDisplayOrientation(270);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        // Set quality to low, because high does not work with front facing camera.
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_LOW));

        mediaRecorder.setOutputFile(
                Util.createOutputDirPathName(sessionId,VIDEOS_DIR) + System.currentTimeMillis() + VIDEO_FILE_EXTENSION
        );

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (Exception e) {
            Log.e(TAG, "MediaRecorder preparation failed: " + e.getMessage());
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        this.sessionId = intent.getLongExtra("session_id", 0);
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mediaRecorder.stop();
        mediaRecorder.reset();
        mediaRecorder.release();

        camera.lock();
        camera.release();

        windowManager.removeView(surfaceView);

        super.onDestroy();
    }



    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {}


//    private String getOuputFileName(){
//        return Environment.getExternalStorageDirectory()
//                + File.separator
//                + Hipsterbility.BASE_DIR
//                + File.separator
//                + VIDEOS_DIR
//                + File.separator
//                + sessionId
//                + File.separator
//                + System.currentTimeMillis()
//                + ".mp4";
//    }

//    public static void setCameraDisplayOrientation(Activity activity,
//                                                   int cameraId, android.hardware.Camera camera) {
//        android.hardware.Camera.CameraInfo info =
//                new android.hardware.Camera.CameraInfo();
//        android.hardware.Camera.getCameraInfo(cameraId, info);
//        int rotation = activity.getWindowManager().getDefaultDisplay()
//                .getRotation();
//        int degrees = 0;
//        switch (rotation) {
//            case Surface.ROTATION_0: degrees = 0; break;
//            case Surface.ROTATION_90: degrees = 90; break;
//            case Surface.ROTATION_180: degrees = 180; break;
//            case Surface.ROTATION_270: degrees = 270; break;
//        }
//
//        int result;
//        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//            result = (info.orientation + degrees) % 360;
//            result = (360 - result) % 360;  // compensate the mirror
//        } else {  // back-facing
//            result = (info.orientation - degrees + 360) % 360;
//        }
//        camera.setDisplayOrientation(result);
//    }
}
