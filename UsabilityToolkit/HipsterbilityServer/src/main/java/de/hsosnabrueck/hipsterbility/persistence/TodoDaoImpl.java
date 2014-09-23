package de.hsosnabrueck.hipsterbility.persistence;

import de.hsosnabrueck.hipsterbility.entities.TodoEntity;

import javax.inject.Singleton;

/**
 * Created by Albert on 16.09.2014.
 */
@Singleton
public class TodoDaoImpl extends BasicDaoImpl<TodoEntity> implements TodoDao{
    public TodoDaoImpl() {
        super(TodoEntity.class, TodoEntity.TABLE_NAME);
    }
}
