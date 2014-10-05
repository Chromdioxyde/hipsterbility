package de.hsosnabrueck.hipsterbility.persistence.impl;

import com.sun.istack.internal.Nullable;
import de.hsosnabrueck.hipsterbility.persistence.Dao;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by Albert on 16.09.2014.
 *
 * Abstract base class for alle DAO-classes, which provides simple CRUD methods
 */
public abstract class BasicDaoImpl<T> implements Dao<T> {

    private Class<T> type;
    private String tableName;
//    @PersistenceContext(name = "HipsterbilityService" )
//    protected EntityManagerFactory emf = Persistence.createEntityManagerFactory("HipsterbilityServiceTest");
    protected EntityManagerFactory emf = Persistence.createEntityManagerFactory("HipsterbilityService");

    protected BasicDaoImpl(Class<T> type, String tableName){
        this.type = type;
        this.tableName = tableName;
    }

    @Override
    public T retrieve(int id) {
        EntityManager em = emf.createEntityManager();
        T entity = null;
        try {
            entity = em.find(type, id);
        } finally {
            em.close();
        }
        return entity;
    }

    @Override
    public boolean delete(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            T entity = em.find(type, id);
            if(entity == null) {
                System.err.println("Error Deleting" + type.getSimpleName() + ": TodoEntity not found");
                transaction.rollback();
                return false;
            }
            else {
                em.remove(entity);

            }
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.err.println("Error Deleting " + type.getSimpleName() + ": " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public T save(T object) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(object);
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Error Saving " + type.getSimpleName() + ": " + e.getMessage());
            object = null;
        } finally {
            em.close();
        }
        return object;
    }

    @Override
    public boolean update(int id, T object) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(object);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.err.println("Error Updating " + type.getSimpleName() + ": " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public Collection<T> list(int offset, int count) {
        emf.createEntityManager();
        EntityManager em = emf.createEntityManager();
        List<T> entity = null;
        try {
            entity = em.createQuery("SELECT e FROM "+ tableName +" e", type).getResultList()
                    .subList(offset, offset + count);
        } finally {
            em.close();
        }
        return entity;
    }

    @Override
    public Collection<T> listAll() {
        EntityManager em = emf.createEntityManager();
        List<T> entity = null;
        try {
            entity = em.createQuery("SELECT e FROM "+ tableName +" e", type).getResultList();
        } finally {
            em.close();
        }
        return entity;
    }
}
