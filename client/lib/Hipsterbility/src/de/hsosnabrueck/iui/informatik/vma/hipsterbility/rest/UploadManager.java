package de.hsosnabrueck.iui.informatik.vma.hipsterbility.rest;

import android.util.Log;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.helper.Util;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.ScreenshotTaker;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.services.CaptureService;
import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Albert Hoffmann on 25.02.14.
 */
public class UploadManager {

    private final static String TAG = UploadManager.class.getName();

    private static UploadManager instance = new UploadManager();
    //TODO: set parameter names
    private final static String PARAM_NAME_SCREENSHOT = "screenshot";
    private final static String PARAM_NAME_CAMERA = "video";
    private final static String PARAM_NAME_SCREEN = "";
    private final static String PARAM_NAME_TOUCH_LOG = "";

    private long size = 0;
    private int fileTotalCount = 0;
    private volatile int fileDoneCount = 0;
    private Session session;

    private UploadManager() {
    }

    public boolean uploadSessionData(Session session) {
        this.session = session;

        size = 0;
        //Create empty lists for different kinds of files
        ArrayList<File> cameraFilesList = new ArrayList<File>();
        ArrayList<File> screenshotFileList = new ArrayList<File>();
        ArrayList<File> touchLogFileList = new ArrayList<File>();

        File sessionDir = new File(Util.createSessionDirPathName(session.getId()));
        if (!sessionDir.exists()) {
            Log.e(TAG, "Session directory does not exist: " + sessionDir.getAbsolutePath());
            return false;
        }
        File[] subdirs = sessionDir.listFiles();
        Log.d(TAG, subdirs.toString());
        for(File f : subdirs){
            if(f.getName().equalsIgnoreCase(CaptureService.VIDEOS_DIR)){
                cameraFilesList = new ArrayList<File>(Arrays.asList(f.listFiles()));
                fileTotalCount += cameraFilesList.size();
            } else if (f.getName().equalsIgnoreCase(ScreenshotTaker.SCREENSHOTS_DIR)){
                screenshotFileList = new ArrayList<File>(Arrays.asList(f.listFiles()));
                fileTotalCount += screenshotFileList.size();
            }
            for(File fs : f.listFiles()){
                if(fs != null){
                    size += fs.length();
                }
            }
        }
        Log.d(TAG, "Overall size of files: " + size + " bytes");
        uploadFiles(session, cameraFilesList, Util.URL_SUFFIX_CAMERA, PARAM_NAME_CAMERA);
        uploadFiles(session, screenshotFileList, Util.URL_SUFFIX_CAPTURES, PARAM_NAME_SCREENSHOT);
//            RequestParams params = new RequestParams();
//            params.add("finished", "1");
//            HipsterbilityRestClient.put(session.getUser().getId() + "/sessions/" + session.getId(), params, new TextHttpResponseHandler() {
//                @Override
//                public void onSuccess(String content) {
//                    super.onSuccess(content);
//                }
//            });

        //TODO: check and upload data

//        File myFile = new File("/path/to/file.png");
//        RequestParams params = new RequestParams();
//        try {
//            params.put("profile_picture", myFile);
//        } catch(FileNotFoundException e) {}
        return true;
    }

    private boolean uploadFiles(Session session, ArrayList<File> mFilesList, String suffix, String paramName) {
        for(File f : mFilesList){
            RequestParams params = new RequestParams();
        try {
            params.put(paramName, f);
        } catch(FileNotFoundException e) {}
            postFile(Util.createRelativeRoute(session.getUser(), session, suffix), params);
        }

        return false;
    }

    private boolean postFile(String relUrl, RequestParams params){
        FileUploadAsyncHttpResponseHandler responseHandler = new FileUploadAsyncHttpResponseHandler();
        HipsterbilityRestClient.post(relUrl, params, responseHandler);
        return responseHandler.isSuccess();
    }

    private class FileUploadAsyncHttpResponseHandler extends AsyncHttpResponseHandler {

        private final String TAG = FileUploadAsyncHttpResponseHandler.class.getName();

        private boolean success;

        public boolean isSuccess() {
            return success;
        }

        @Override
        public void onStart() {
            // Initiated the request
            //TODO: do something or delete
            Log.d(TAG, "Request started" );
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            // Successfully got a response
            //TODO: do something or delete
            fileDoneCount++;
            Log.d(TAG, "Request successful, status code: " +statusCode );
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            // Response failed :(
            //TODO: do something else on failure
            Log.d(TAG, "Request failed, status code: " + statusCode + ", error: " + error.getMessage() );
            success = false;
        }

        @Override
        public void onRetry() {
            // Request was retried
            //TODO: do something or delete
            Log.d(TAG, "Request retry" );
        }

        @Override
        public void onProgress(int bytesWritten, int totalSize) {
            // Progress notification
            //TODO: do something or delete, notify user
        }

        @Override
        public void onFinish() {
            // Completed the request (either success or failure)
            //TODO: do something or delete
            if(fileTotalCount == fileDoneCount){
                RequestParams params = new RequestParams();
                params.add("finished", "1");
                HipsterbilityRestClient.put(session.getUser().getId() + "/sessions/" + session.getId(), params, new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(String content) {
                        super.onSuccess(content);
                    }
                });
            }
            Log.d(TAG, "Request finished" );
        }

    }

    public static UploadManager getInstance() {
        return instance;
    }

    private synchronized void updateProgress(){

    }

}
