package de.hsosnabrueck.hipsterbility.persistence;

import com.sun.istack.internal.Nullable;
import de.hsosnabrueck.hipsterbility.exceptions.DataAccessException;

import javax.persistence.EntityManagerFactory;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Albert on 14.09.2014.
 */
public abstract interface GenericDao<T, PK extends Serializable> {

    T read(PK id) throws DataAccessException;
    void delete(PK id) throws DataAccessException;
    @Nullable T create(T object) throws DataAccessException;
    @Nullable T createIfNotExists(T object, PK primaryKey) throws DataAccessException;
    T update(PK id, T object) throws DataAccessException;
    Collection<T> list(int offset, int count) throws DataAccessException;
    Collection<T> listAll() throws DataAccessException;
    void setEntityManagerFactory(EntityManagerFactory emf);

}
