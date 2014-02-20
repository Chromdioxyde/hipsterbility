package de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.sessions;

import java.util.ArrayList;

/**
 * Created by Albert Hoffmann on 13.02.14.
 */
public class SessionManager {

    private static SessionManager instance = new SessionManager();

    private ArrayList<Session> sessions;

    private SessionManager() {
        this.sessions = new ArrayList<Session>();
    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }

    public void setSessions(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }

    public static SessionManager getInstace(){
        return instance;
    }
}
