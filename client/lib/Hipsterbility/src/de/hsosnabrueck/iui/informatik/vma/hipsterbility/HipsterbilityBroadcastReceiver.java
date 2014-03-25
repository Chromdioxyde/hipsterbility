package de.hsosnabrueck.iui.informatik.vma.hipsterbility;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.screencapture.ScreenshotModule;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.rest.UploadManager;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.services.CaptureService;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.sessions.SessionManager;

/**
 * Created by Albert Hoffmann on 27.02.14.
 */
public class HipsterbilityBroadcastReceiver extends BroadcastReceiver{
    private static final String TAG = HipsterbilityBroadcastReceiver.class.getName();

    public static final String ACTION_START_SESSION = "hipsterbility.START_SESSION";
    public static final String ACTION_FINISH_CAPTURE = "hipsterbility.FINISH_SESSION";
    public static final String ACTION_START_CAPTURE = "hipsterbility.START_CAPTURE";
    public static final String ACTION_STOP_CAPTURE = "hipsterbility.STOP_CAPTURE";
    public static final String ACTION_PAUSE_CAPTURE = "hipsterbility.PAUSE_CAPTURE";
    public static final String ACTION_RESUME_CAPTURE = "hipsterbility.RESUME_CAPTURE";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action == null){
            Log.d(TAG, "Recieved null action");
            return;
        }
        if(action.equals(ACTION_START_CAPTURE)){
            Log.d(TAG, "Received Action: " + ACTION_START_CAPTURE);
            Activity a = Hipsterbility.getInstance().getActivity();
            Session s = SessionManager.getInstace().getSessionInProgress();
            Hipsterbility.getInstance().setScreenshotModule(new ScreenshotModule(s, a));
            Intent i = new Intent(context, CaptureService.class);
            i.putExtra("session_id", s.getId());
            context.startService(i);

        } else if (action.equals(ACTION_STOP_CAPTURE)){
            Log.d(TAG, "Received Action: " + ACTION_STOP_CAPTURE);
            ScreenshotModule st = Hipsterbility.getInstance().getScreenshotModule();
            if(st!= null){
                st.stopScreenshots();
            }
            Hipsterbility.getInstance().stopCapture();
//            AlertDialog dialog = new AlertDialog.Builder(Hipsterbility.getInstance().getActivity())
/*            AlertDialog dialog = new AlertDialog.Builder()
                    .setCancelable(true)
                    .setTitle("Upload data?")
                    .setPositiveButton("ok",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            UploadManager.getInstance().uploadSessionData(SessionManager.getInstace().getSessionInProgress());
                        }
                    })
                    .create();
            dialog.show();*/

        } else if (action.equals(ACTION_START_SESSION)){
            Log.d(TAG, "Received Action: " + ACTION_START_SESSION);
            Hipsterbility.getInstance().startSession();

        } else if (action.equals(ACTION_FINISH_CAPTURE)){
            Log.d(TAG, "Received Action: " + ACTION_FINISH_CAPTURE);
            UploadManager.getInstance().uploadSessionData(SessionManager.getInstace().getSessionInProgress());
        }
    }
}
