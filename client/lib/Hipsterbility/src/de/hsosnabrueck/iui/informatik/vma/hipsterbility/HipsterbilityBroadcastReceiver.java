package de.hsosnabrueck.iui.informatik.vma.hipsterbility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules.ScreenshotTaker;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.rest.UploadManager;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.services.CaptureService;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.services.HipsterbilityService;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.sessions.SessionManager;

/**
 * Created by Albert Hoffmann on 27.02.14.
 */
public class HipsterbilityBroadcastReceiver extends BroadcastReceiver{
    private static final String TAG = HipsterbilityBroadcastReceiver.class.getName();

    public static final String ACTION_START_SESSION = "de.hipsterbility.START_SESSION";
    public static final String ACTION_FINISH_CAPTURE = "de.hipsterbility.FINISH_SESSION";
    public static final String ACTION_START_CAPTURE = "de.hipsterbility.START_CAPTURE";
    public static final String ACTION_STOP_CAPTURE = "de.hipsterbility.STOP_CAPTURE";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if(action.equals(ACTION_START_CAPTURE)){
            Log.d(TAG, "Received Action: " + ACTION_START_CAPTURE);
            Activity a = Hipsterbility.getInstance().getActivity();
            Session s = SessionManager.getInstace().getSessionInProgress();
            Hipsterbility.getInstance().setScreenshotTaker(new ScreenshotTaker(s, a));
            Intent i = new Intent(context, CaptureService.class);
            i.putExtra("session_id", s.getId());
            context.startService(i);

        } else if (action.equals(ACTION_STOP_CAPTURE)){
            Log.d(TAG, "Received Action: " + ACTION_STOP_CAPTURE);
            ScreenshotTaker st = Hipsterbility.getInstance().getScreenshotTaker();
            if(st!= null){
                st.stopScreenshots();
            }
            Hipsterbility.getInstance().stopCapture();
            AlertDialog dialog = new AlertDialog.Builder(Hipsterbility.getInstance().getActivity())
                    .setCancelable(true)
                    .setTitle("Upload data?")
                    .setPositiveButton("ok",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            UploadManager.getInstance().uploadSessionData(SessionManager.getInstace().getSessionInProgress());
                        }
                    })
                    .create();
            dialog.show();

        } else if (action.equals(ACTION_START_SESSION)){
            Log.d(TAG, "Received Action: " + ACTION_START_SESSION);
            Hipsterbility.getInstance().startSession();

        } else if (action.equals(ACTION_FINISH_CAPTURE)){
            Log.d(TAG, "Received Action: " + ACTION_FINISH_CAPTURE);
            UploadManager.getInstance().uploadSessionData(SessionManager.getInstace().getSessionInProgress());
        }
    }
}
