package de.hsosnabrueck.iui.informatik.vma.hipsterbility.tests;

import android.util.Log;
import de.hsosnabrueck.hipsterbility.entities.TestEntity;

import java.util.ArrayList;

/**
 * Created on 13.02.14.
 */
public class TestManager {

    private static final String TAG = TestManager.class.getSimpleName();
    private static TestManager instance;

    public static TestEntity activeTest;

    private ArrayList<TestEntity> tests;


    private TestManager() {
        Log.i(TAG, this.getClass().getSimpleName() + " created");
//        this.sessions = new ArrayList<Session>();
        this.tests = new ArrayList<>();
    }

    public static TestManager getInstance() {
        if (instance == null) {
            instance = new TestManager();
        }
        return instance;
    }

    public ArrayList<TestEntity> getTests() {
        return tests;
    }

    public void setTests(ArrayList<TestEntity> tests) {
        this.tests = tests;
    }
}
