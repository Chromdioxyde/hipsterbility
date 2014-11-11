package de.hsosnabrueck.hipsterbility.persistence.impl;

import de.hsosnabrueck.hipsterbility.entities.TestAppEntity;
import de.hsosnabrueck.hipsterbility.persistence.TestAppDao;

/**
 * Created by Albert on 04.10.2014.
 */
public class TestAppDaoImpl extends GenericDaoImpl<TestAppEntity, Integer> implements TestAppDao {
    protected TestAppDaoImpl() {
        super(TestAppEntity.class, TestAppEntity.TABLE_NAME);
    }
}
