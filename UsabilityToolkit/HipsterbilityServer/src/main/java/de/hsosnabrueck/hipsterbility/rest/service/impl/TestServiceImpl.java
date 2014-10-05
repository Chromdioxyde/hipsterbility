package de.hsosnabrueck.hipsterbility.rest.service.impl;

import de.hsosnabrueck.hipsterbility.entities.TestEntity;
import de.hsosnabrueck.hipsterbility.persistence.TestDao;
import de.hsosnabrueck.hipsterbility.rest.service.TestService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

/**
 * Created by Albert on 04.10.2014.
 */
@Singleton
public class TestServiceImpl implements TestService {

    @Inject
    TestDao testDao;

    @Override
    public Collection<TestEntity> list() {
        return testDao.listAll();
    }

    @Override
    public Collection<TestEntity> list(int startIndex, int count) {
        return testDao.list(startIndex, count);
    }

    @Override
    public TestEntity read(int id) {
        return testDao.retrieve(id);
    }

    @Override
    public boolean delete(int id) {
        return testDao.delete(id);
    }

    @Override
    public TestEntity create(TestEntity test) {
        return testDao.save(test);
    }

    @Override
    public boolean update(int id, TestEntity test) {
        return testDao.update(id, test);
    }
}
