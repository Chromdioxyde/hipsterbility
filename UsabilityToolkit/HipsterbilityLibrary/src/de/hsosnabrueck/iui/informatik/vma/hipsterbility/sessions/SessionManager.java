package de.hsosnabrueck.iui.informatik.vma.hipsterbility.sessions;

import android.content.Context;
import android.util.Log;
import de.hsosnabrueck.hipsterbility.entities.TestEntity;
import de.hsosnabrueck.hipsterbility.entities.TestSessionEntity;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.persistence.DatabaseHelper;

import java.sql.SQLException;

/**
 * Created by Albert on 06.10.2014.
 */
public class SessionManager {
    private static final String TAG = SessionManager.class.getSimpleName();

    public static TestSessionEntity sessionInProgress;

    private DatabaseHelper databaseHelper;

    public SessionManager(Context context) {
        databaseHelper = new DatabaseHelper(context);
        Log.i(TAG, this.getClass().getSimpleName() + " created");
    }


    public TestSessionEntity createNewSession(String name, String description, TestEntity testEntity){
        TestSessionEntity session = new TestSessionEntity();
        session.setName(name);
        session.setTest(testEntity);
        session.setDescription(description);
        try {
            databaseHelper.getSessionDao().create(session);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sessionInProgress = session;
        return session;

    }

}
