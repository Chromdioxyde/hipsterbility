package de.hsosnabrueck.hipsterbility.rest.service;


import java.util.Collection;

/**
 * Created by Albert on 15.09.2014.
 */
public interface Service<T> {
    public Collection<T> list();

    public Collection<T> list(int startIndex, int count);

    public T read(int id);

    public void delete(int id);

    public T create(T object);

    public void update(int id, T object);
}
