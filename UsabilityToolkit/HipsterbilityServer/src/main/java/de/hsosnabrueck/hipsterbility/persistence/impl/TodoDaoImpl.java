package de.hsosnabrueck.hipsterbility.persistence.impl;

import de.hsosnabrueck.hipsterbility.entities.TaskEntity;
import de.hsosnabrueck.hipsterbility.persistence.TodoDao;

import javax.inject.Singleton;

/**
 * Created by Albert on 16.09.2014.
 */
@Singleton
public class TodoDaoImpl extends BasicDaoImpl<TaskEntity> implements TodoDao {
    public TodoDaoImpl() {
        super(TaskEntity.class, TaskEntity.TABLE_NAME);
    }
}
