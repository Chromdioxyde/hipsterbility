package de.hsosnabrueck.hipsterbility.persistence;

import de.hsosnabrueck.hipsterbility.entities.TestResultEntity;

import javax.inject.Singleton;

/**
 * Created by Albert on 17.09.2014.
 */
@Singleton
public class TestResultDaoImpl extends BasicDaoImpl<TestResultEntity> implements TestResultDao {

    protected TestResultDaoImpl() {
        super(TestResultEntity.class, TestResultEntity.TABLE_NAME);
    }
}
