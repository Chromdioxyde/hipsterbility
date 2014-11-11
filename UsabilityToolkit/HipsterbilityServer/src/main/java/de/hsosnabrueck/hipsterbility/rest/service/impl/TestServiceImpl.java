package de.hsosnabrueck.hipsterbility.rest.service.impl;

import de.hsosnabrueck.hipsterbility.entities.TaskEntity;
import de.hsosnabrueck.hipsterbility.entities.TestEntity;
import de.hsosnabrueck.hipsterbility.exceptions.DataAccessException;
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
    public Collection<TestEntity> list() throws DataAccessException {
        return testDao.listAll();
    }

    @Override
    public Collection<TestEntity> list(int startIndex, int count) throws DataAccessException {
        return testDao.list(startIndex, count);
    }

    @Override
    public TestEntity read(Integer id) throws DataAccessException {
        return testDao.read(id);
    }

    @Override
    public void delete(Integer id) throws DataAccessException {
        testDao.delete(id);
    }

    @Override
    public TestEntity create(TestEntity test) throws DataAccessException {
        for(TaskEntity t : test.getTasks()){
            if(null == t.getTest()) t.setTest(test);
        }
        return testDao.create(test);
    }

    @Override
    public TestEntity update(Integer id, TestEntity test) throws DataAccessException {
        return testDao.update(id, test);
    }
}
