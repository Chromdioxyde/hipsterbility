package de.hsosnabrueck.hipsterbility.rest.service;


import de.hsosnabrueck.hipsterbility.exceptions.DataAccessException;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Albert on 15.09.2014.
 */
public abstract interface Service<T, PK extends Serializable> {
    public Collection<T> list() throws DataAccessException;

    public Collection<T> list(int startIndex, int count) throws DataAccessException;

    public T read(PK id) throws DataAccessException;

    public void delete(PK id) throws DataAccessException;

    public T create(T object) throws DataAccessException;

    public T update(PK id, T object) throws DataAccessException;
}
