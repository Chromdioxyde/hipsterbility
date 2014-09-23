package de.hsosnabrueck.hipsterbility.persistence;

import de.hsosnabrueck.hipsterbility.entities.TestSessionEntity;

import javax.inject.Singleton;

/**
 * Created by Albert on 17.09.2014.
 */
@Singleton
public class TestSessionDaoImpl extends BasicDaoImpl<TestSessionEntity> implements TestSessionDao {
    protected TestSessionDaoImpl() {
        super(TestSessionEntity.class, TestSessionEntity.TABLE_NAME);
    }
}
