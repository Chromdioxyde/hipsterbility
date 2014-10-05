package de.hsosnabrueck.hipsterbility.persistence.impl;

import de.hsosnabrueck.hipsterbility.entities.TestEntity;
import de.hsosnabrueck.hipsterbility.persistence.TestDao;

import javax.inject.Singleton;

/**
 * Created by Albert on 04.10.2014.
 */
@Singleton
public class TestDaoImpl extends BasicDaoImpl<TestEntity> implements TestDao {
    public TestDaoImpl() {
        super(TestEntity.class, TestEntity.TABLE_NAME);
    }
}
