package de.hsosnabrueck.hipsterbility.persistence.impl;

import de.hsosnabrueck.hipsterbility.entities.TodoEntity;
import de.hsosnabrueck.hipsterbility.persistence.TodoDao;

import javax.inject.Singleton;

/**
 * Created by Albert on 16.09.2014.
 */
@Singleton
public class TodoDaoImpl extends BasicDaoImpl<TodoEntity> implements TodoDao {
    public TodoDaoImpl() {
        super(TodoEntity.class, TodoEntity.TABLE_NAME);
    }
}
