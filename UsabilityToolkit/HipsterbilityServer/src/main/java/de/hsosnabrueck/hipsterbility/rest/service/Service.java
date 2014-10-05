package de.hsosnabrueck.hipsterbility.rest.service;


import java.util.Collection;

/**
 * Created by Albert on 15.09.2014.
 */
public abstract interface Service<T> {
    public Collection<T> list();

    public Collection<T> list(int startIndex, int count);

    public T read(int id);

    public boolean delete(int id);

    public T create(T object);

    public boolean update(int id, T object);
}
