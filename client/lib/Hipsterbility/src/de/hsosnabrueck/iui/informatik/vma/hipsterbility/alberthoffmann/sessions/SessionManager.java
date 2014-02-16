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

    public static SessionManager getInstace(){
        return instance;
    }
}
