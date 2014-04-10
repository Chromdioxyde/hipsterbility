package de.hsosnabrueck.iui.informatik.vma.hipsterbility.services;

import android.app.*;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.R;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.helper.Util;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.rest.HipsterbilityRestClient;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.sessions.SessionManager;
import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created on 25.02.14.
 *
 * Service to handle data uploads to the server after a session has finished.
 *
 */
public class UploadService extends Service {

    // Constants
    private final static String TAG = UploadService.class.getName();
    private final static int NOTIFICATION_ID = 3;
    //TODO: set parameter names for future file types
    private final static String PARAM_NAME_SCREENSHOT = "screenshot";
    private final static String PARAM_NAME_CAMERA = "video";
    private final static String PARAM_NAME_SCREEN_RECORDING = "";
    private final static String PARAM_NAME_TOUCH_LOG = "";
    private final static String PARAM_NAME_AUDIO_CAPTURE = "";

    private volatile int totalBytes = 0;
    private volatile int bytesDone = 0;
    private int totalFileCount = 0;
    private volatile int fileDoneCount = 0;
    private Session session;
    private NotificationManager notificationManager;

    private HashMap<String, List<File>> files;

    /**
     * private constructor for static singleton.
     */
    public UploadService() {
        files = new HashMap<String, List<File>>();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void uploadSessionData() {
        updateSession();

        //Create empty lists for different kinds of files
        listFiles();
        for (String s : files.keySet()) {
            if (s.equalsIgnoreCase(Util.VIDEOS_DIR)) {
                uploadFiles(session, files.get(s), Util.URL_SUFFIX_CAMERA, PARAM_NAME_CAMERA);
            } else if (s.equalsIgnoreCase(Util.SCREENSHOTS_DIR)) {
                uploadFiles(session, files.get(s), Util.URL_SUFFIX_CAPTURES, PARAM_NAME_SCREENSHOT);
            }
        }
    }

    /**
     * Lists files in the subdirectories of the base session folder and stores them in the HashMap files
     * where the directory name serves as key.
     */
    private void listFiles() {
        File sessionDir = new File(Util.createSessionDirPathName(session.getId()));
        totalBytes = 0;
        totalFileCount = 0;
        if (!sessionDir.exists()) {
            Log.e(TAG, "Session directory does not exist: " + sessionDir.getAbsolutePath());
            return;
        }
        File[] subdirs = sessionDir.listFiles();
        Log.d(TAG, subdirs.toString());
        for (File f : subdirs) {
            if (f.isDirectory()) {
                List<File> fileList = Arrays.asList(f.listFiles());
                totalFileCount += fileList.size();
                files.put(f.getName(), fileList);
                for (File fs : fileList) {
                    if (fs != null) {
                        totalBytes += fs.length();
                    }
                }
            }
        }
        Log.d(TAG, "Files: " + totalFileCount + "Bytes: " + totalBytes);
    }

    private void updateSession() {
        this.session = SessionManager.getInstace().getSessionInProgress();
    }

    /**
     * Creates a notification which is used as a trigger to upload files when tapped.
     * Notification can be dismissed by sliding.
     */
    private void createUploadNotification() {
        updateSession();
        String text = getString(R.string.notification_text_upload_data)
                + "\n" + getString(R.string.session)+ ": " + session.getId()
                + "\n" + session.getName();
        Intent i = new Intent(this, this.getClass());
        i.setAction(getString(R.string.action_upload));
        PendingIntent p = PendingIntent.getService(this, 0, i, 0);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_main)
                .setStyle(new Notification.BigTextStyle()
                        .bigText(text))
                .setContentIntent(p)
                .setContentTitle(getString(R.string.notification_title_upload_data))
                .build();
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    private void deleteFiles() {
        if (files.size() == 0) {
            return;
        }
        for (List<File> flist : files.values()) {
            for(File f : flist){
                f.delete();
            }
        }
    }

    private void uploadFiles(Session session, List<File> mFilesList, String suffix, String paramName) {
        for (File f : mFilesList) {
            RequestParams params = new RequestParams();
            try {
                params.put(paramName, f);
            } catch (FileNotFoundException e) {
                Log.e(TAG, e.getMessage());
            }
            FileUploadAsyncHttpResponseHandler responseHandler = new FileUploadAsyncHttpResponseHandler();
            HipsterbilityRestClient.post(
                    Util.createRelativeRoute(session.getUser(), session, suffix),
                    params, responseHandler);
        }
    }


    private synchronized void updateProgress() {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_stat_main);
        builder.setProgress(totalFileCount, fileDoneCount, false);
        builder.setContentTitle(getString(R.string.notification_title_upload_data));
        builder.setContentText("Files complete: " +fileDoneCount + " / " + totalFileCount);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String action = intent.getAction();
        if(action !=null){
            if (action.equals(getString(R.string.action_upload))){
                uploadSessionData();
            } else if (action.equals(getString(R.string.action_delete_files))){
                deleteFiles();
            }
        } else {
            createUploadNotification();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * Sends a PUT request to the server on the current session resource to signal successful upload of all files.
     * Creates a notification afterwards, which deletes the uploaded files of tap.
     * TODO: Automatically delete uploaded files after required testing.
     */
    private synchronized void finishUpload(){
        RequestParams params = new RequestParams();
        params.add("finished", "1");
        Log.d(TAG, "put finish");
        HipsterbilityRestClient.put("/" + session.getUser().getId() + "/sessions/" + session.getId(), params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
            }
        });

        Intent i = new Intent(this, UploadService.class);
        i.setAction(getString(R.string.action_delete_files));
        PendingIntent pIntent = PendingIntent.getService(this, 0, i, 0);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_stat_main);
        builder.setContentTitle(getString(R.string.notification_title_upload_data));
        builder.setContentText(getString(R.string.notification_text_upload_data_complete));
        builder.addAction(R.drawable.ic_stat_dismiss, getString(R.string.notification_action_upload_delete), pIntent);
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }

    /**
     * Custom response handler for file uploads.
     * Used for progress update and to check if all files were successfully uploaded.
     * TODO: Better error handing if connection to the server is cut off.
     */
    private class FileUploadAsyncHttpResponseHandler extends AsyncHttpResponseHandler {
        private final String TAG = FileUploadAsyncHttpResponseHandler.class.getName();

        @Override
        public void onStart() {
            // Initiated the request
            Log.d(TAG, "Request started");
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            // Successfully got a response
            fileDoneCount++;
            updateProgress();
            Log.d(TAG, "Request successful, status code: " + statusCode);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            // Response failed :(
            Log.d(TAG, "Request failed, status code: " + statusCode + ", error: " + error.getMessage());
        }

        @Override
        public void onRetry() {
            // Request was retried
            Log.d(TAG, "Request retry");
        }

        @Override
        public void onProgress(int bytesWritten, int totalSize) {
            // Progress notification
            bytesDone += bytesWritten;
        }

        @Override
        public void onFinish() {
            // Completed the request (either success or failure)
            // Check if all files were uploaded successfully
            if (totalFileCount == fileDoneCount) {
               finishUpload();
            }
            Log.d(TAG, "Request finished");
        }
    }

}
