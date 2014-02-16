package de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.sessions.Session;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.modules.AbstractModule;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.modules.AudioCaptureModule;

import java.util.ArrayList;

/**
 * Created by Albert Hoffmann on 13.02.14.
 */
public class CaptureService extends Service{

    // TODO: Implementation

    //================================================================================
    // Properties
    //================================================================================

    private Session session;

    private ArrayList<AbstractModule> modules;
    //================================================================================
    // Constructors
    //================================================================================

    public CaptureService(){
        this.modules = new ArrayList<AbstractModule>();
    }

    public CaptureService(Session session) {
        this();
        this.session = session;
    }

    //================================================================================
    // Public Methods
    //================================================================================

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful

        this.session = (Session) intent.getParcelableExtra("Session");
        AudioCaptureModule audioCap = new AudioCaptureModule(session);
        this.modules.add(audioCap);
        for(AbstractModule module:modules){
            module.startCapture();
        }
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        for(AbstractModule module:modules){
            module.stopCapture();
        }
        super.onDestroy();
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    //================================================================================
    // Private Methods
    //================================================================================

}
