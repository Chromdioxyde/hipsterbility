package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.*;
import de.hsosnabrueck.iui.informatik.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.services.CaptureService;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;

import java.util.Date;

/**
 * Created by Albert Hoffmann on 17.02.14.
 */
public class CameraCapture extends AbstractModule implements SurfaceHolder.Callback {


    private WindowManager windowManager;
    private SurfaceView surfaceView;
    private Camera camera = null;
    private MediaRecorder mediaRecorder = null;
    private CaptureService service = null;
    private Surface fakeSurface = null;
    private boolean recording = false;

    public CameraCapture(CaptureService service, Session session, Activity activity) {
        super(session, activity);
        // Start foreground service to avoid unexpected kill
        Notification notification = new Notification.Builder(service)
                .setContentTitle("Background Video Recorder")
                .setContentText("")
                .setSmallIcon(R.drawable.ic_launcher)
                .build();
        service.startForeground(1234, notification);
        // Create new SurfaceView, set its size to 1x1, move it to the top left corner and set this service as a callback
        windowManager = (WindowManager) service.getSystemService(Context.WINDOW_SERVICE);
        surfaceView = new SurfaceView(service);
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
    public void startCapture() {
//        if(fakeSurface != null) {
//            prepareRecording();
//            mediaRecorder.start();
//        }
    }

    @Override
    public void stopCapture() {
        if(mediaRecorder == null){
            return;
        }
        try{
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();

            camera.lock();
            camera.release();
            windowManager.removeView(surfaceView);
        } catch (Exception e){System.err.println(e.getMessage());};
    }

    // Method called right after Surface created (initializing and starting MediaRecorder)
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        camera = Camera.open();
        mediaRecorder = new MediaRecorder();
        camera.unlock();

        mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
        mediaRecorder.setCamera(camera);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));

        mediaRecorder.setOutputFile(
                Environment.getExternalStorageDirectory()+"/"+
                        DateFormat.format("yyyy-MM-dd_kk-mm-ss", new Date().getTime())+
                        ".mp4"
        );

        try { mediaRecorder.prepare(); } catch (Exception e) {}
        mediaRecorder.start();
    }


    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {}

}
