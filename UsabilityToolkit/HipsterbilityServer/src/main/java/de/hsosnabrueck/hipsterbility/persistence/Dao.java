package de.hsosnabrueck.hipsterbility.persistence;

import com.sun.istack.internal.Nullable;

import java.util.Collection;

/**
 * Created by Albert on 14.09.2014.
 */
public abstract interface Dao<T> {
    public T retrieve(int id);

    public boolean delete(int id);

    @Nullable
    public T save(T object);

    public boolean update(int id, T object);

    public Collection<T> list(int offset, int count);

    public Collection<T> listAll();
}
