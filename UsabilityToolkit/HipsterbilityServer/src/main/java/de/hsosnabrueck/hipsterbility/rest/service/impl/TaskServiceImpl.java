package de.hsosnabrueck.hipsterbility.rest.service.impl;

import de.hsosnabrueck.hipsterbility.entities.TaskEntity;
import de.hsosnabrueck.hipsterbility.exceptions.DataAccessException;
import de.hsosnabrueck.hipsterbility.persistence.TaskDao;
import de.hsosnabrueck.hipsterbility.rest.service.TaskService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

/**
 * Created by Albert on 16.09.2014.
 */
@Singleton
public class TaskServiceImpl implements TaskService {

    @Inject
    TaskDao taskDao;

    @Override
    public Collection<TaskEntity> list() throws DataAccessException {
        return taskDao.listAll();
    }

    @Override
    public Collection<TaskEntity> list(int startIndex, int count) throws DataAccessException {
        return taskDao.list(startIndex, count);
    }

    @Override
    public TaskEntity read(Integer id) throws DataAccessException {
        return taskDao.read(id);
    }

    @Override
    public void delete(Integer id) throws DataAccessException {
        taskDao.delete(id);
    }

    @Override
    public TaskEntity create(TaskEntity task) throws DataAccessException {
        return taskDao.create(task);
    }

    @Override
    public TaskEntity update(Integer id, TaskEntity task) throws DataAccessException {
        return taskDao.update(id, task);
    }
}
