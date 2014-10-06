package de.hsosnabrueck.hipsterbility.persistence.impl;

import de.hsosnabrueck.hipsterbility.entities.TaskEntity;
import de.hsosnabrueck.hipsterbility.persistence.TaskDao;

import javax.inject.Singleton;

/**
 * Created by Albert on 16.09.2014.
 */
@Singleton
public class TaskDaoImpl extends BasicDaoImpl<TaskEntity> implements TaskDao {

    public TaskDaoImpl() {
        super(TaskEntity.class, TaskEntity.TABLE_NAME);
    }
}