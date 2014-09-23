package de.hsosnabrueck.hipsterbility.persistence;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by Albert on 16.09.2014.
 */
public class BasicDaoImpl<T> implements Dao<T> {

    private Class<T> type;
    private String tableName;
    protected EntityManagerFactory emf = Persistence.createEntityManagerFactory("HipsterbilityService");
//    @PersistenceUnit(unitName = "HipsterbilityService")
//    protected EntityManagerFactory emf;

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
    public void delete(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            T entity = em.find(type, id);
            if(entity == null) {
                System.err.println("Error Deleting" + type.getSimpleName() + ": TodoEntity not found");
            }
            else {
                em.remove(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Error Deleting " + type.getSimpleName() + ": " + e.getMessage());
            transaction.rollback();
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
            transaction.rollback();
        } finally {
            em.close();
        }
        return object;
    }

    @Override
    public void update(int id, T object) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(object);
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Error Updating " + type.getSimpleName() + ": " + e.getMessage());
            transaction.rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public Collection<T> list(int offset, int count) {emf.createEntityManager();
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
