package de.hsosnabrueck.hipsterbility.persistence.impl;

import de.hsosnabrueck.hipsterbility.entities.TaskEntity;
import de.hsosnabrueck.hipsterbility.persistence.TaskDao;

import javax.inject.Singleton;

/**
 * Created by Albert on 16.09.2014.
 */
@Singleton
public class TaskDaoImpl extends GenericDaoImpl<TaskEntity, Integer> implements TaskDao {
    protected TaskDaoImpl() {
        super(TaskEntity.class, TaskEntity.TABLE_NAME);
    }
}
