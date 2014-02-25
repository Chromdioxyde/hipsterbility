package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules;

import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;

/**
 * Created by Albert Hoffmann on 13.02.14.
 * Simple Template for unified capture modules.
 */
public abstract class AbstractModule {


    // TODO: Implementation

    //================================================================================
    // Properties
    //================================================================================

    protected Session session;

    //================================================================================
    // Constructors
    //================================================================================

    protected AbstractModule(Session session) {
        this.session = session;
    }

    //================================================================================
    // Public Methods
    //================================================================================


    public Session getSession() {
        return session;
    }


    public void setSession(Session session) {
        this.session = session;
    }

    public abstract void startCapture();
    public abstract void stopCapture();

    //================================================================================
    // Private Methods
    //================================================================================

}
