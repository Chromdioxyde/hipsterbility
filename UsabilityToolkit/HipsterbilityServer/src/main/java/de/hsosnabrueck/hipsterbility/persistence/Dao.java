package de.hsosnabrueck.hipsterbility.persistence;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;
import de.hsosnabrueck.hipsterbility.model.Device;

import java.util.Collection;
import java.util.List;

/**
 * Created by Albert on 14.09.2014.
 */
public interface Dao<T> {
    public T retrieve(int id);

    public void delete(int id);

    public void save(T object);

    public void update(int id, T object);

    public Collection<T> list(int offset, int count);

    public Collection<T> listAll();
}
