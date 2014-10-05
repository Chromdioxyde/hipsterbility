package de.hsosnabrueck.hipsterbility.rest.service.impl;

import de.hsosnabrueck.hipsterbility.entities.TaskEntity;
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
    public Collection<TaskEntity> list() {
        return taskDao.listAll();
    }

    @Override
    public Collection<TaskEntity> list(int startIndex, int count) {
        return taskDao.list(startIndex, count);
    }

    @Override
    public TaskEntity read(int id) {
        return taskDao.retrieve(id);
    }

    @Override
    public boolean delete(int id) {
        return taskDao.delete(id);
    }

    @Override
    public TaskEntity create(TaskEntity task) {
        return taskDao.save(task);
    }

    @Override
    public boolean update(int id, TaskEntity task) {
        return taskDao.update(id, task);
    }
}
