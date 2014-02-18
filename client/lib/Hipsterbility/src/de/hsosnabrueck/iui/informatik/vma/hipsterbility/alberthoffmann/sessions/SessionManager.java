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
        createTestSessions();
    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }

    public void setSessions(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }

    //TODO: delete when data service is implemented
    private void createTestSessions(){
        this.sessions.add(new Session(1 ,"wurst", "Nexus", "bla", true));
        this.sessions.add(new Session(3 ,"salat", "Galaxy", "blubb", false));
        this.sessions.add(new Session(5 ,"foo", "Nexus", "blubb", true));
        this.sessions.add(new Session(2 ,"bar", "HTC", "blubb", false));
        this.sessions.add(new Session(25 ,"baz", "Dumbphone", "bla", true));
        this.sessions.add(new Session(13 ,"42", "Nexus", "bla", true));
    }

    public static SessionManager getInstace(){
        return instance;
    }
}
