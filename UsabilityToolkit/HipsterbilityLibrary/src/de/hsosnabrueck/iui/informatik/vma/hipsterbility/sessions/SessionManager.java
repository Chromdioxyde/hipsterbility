package de.hsosnabrueck.iui.informatik.vma.hipsterbility.sessions;

import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;

import java.util.ArrayList;

/**
 * Created on 13.02.14.
 */
public class SessionManager {

    private static SessionManager instance;

    private ArrayList<Session> sessions;
    private Session sessionInProgress;

    private SessionManager() {
        sessionInProgress = null;
        this.sessions = new ArrayList<Session>();
    }

    public static SessionManager getInstace() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }

    public void setSessions(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }

    public Session getSessionInProgress() {
        return sessionInProgress;
    }

    public void setSessionInProgress(Session sessionInProgress) {
        this.sessionInProgress = sessionInProgress;
    }


}
