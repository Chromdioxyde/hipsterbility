package de.hsosnabrueck.hipsterbility.persistence.impl;

import de.hsosnabrueck.hipsterbility.exceptions.DataAccessException;
import de.hsosnabrueck.hipsterbility.persistence.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by Albert on 16.09.2014.
 *
 * Abstract base class for alle DAO-classes, which provides simple CRUD methods
 */
public abstract class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    protected Class<T> type;
    private String tableName;
//    @PersistenceContext(name = "HipsterbilityService" )
//    protected EntityManagerFactory emf = Persistence.createEntityManagerFactory("HipsterbilityServiceTest");
    @PersistenceUnit(name = "HipsterbilityService" )
    protected EntityManagerFactory emf; // = Persistence.createEntityManagerFactory("HipsterbilityService");

    protected GenericDaoImpl(Class<T> type, String tableName){
        this.type = type;
        this.tableName = tableName;
    }

    @Override
    public T read(PK id) throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        T entity = null;
        try {
            entity = em.find(type, id);
        } catch (Exception e){
            throw new DataAccessException(e);
        } finally {
            em.close();
        }
        if(null == entity) throw new DataAccessException(type.getSimpleName() + " with id " + id + " does not exist.");
        return entity;
    }

    @Override
    public void delete(PK id) throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            T entity = em.find(type, id);
            if(entity == null) {
                System.err.println("Error Deleting" + type.getSimpleName() + ": Entity not found");
                transaction.rollback();
                throw new DataAccessException("Error: Object does not exist");
            }
            else {
                em.remove(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Error Deleting " + type.getSimpleName() + ": " + e.getMessage());
            throw new DataAccessException(e);
        } finally {
            em.close();
        }
    }

    @Override
    public T create(T object) throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(object);
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Error Saving " + type.getSimpleName() + ": " + e.getMessage());
            throw new DataAccessException(e);
        } finally {
            em.close();
        }
        return object;
    }

    @Override
    public T createIfNotExists(T object, PK primaryKey) throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            T result = em.find(type, primaryKey);
            transaction.begin();
            if(null == result){
                em.persist(object);
            } else {
                object = em.merge(object);
            }
            transaction.commit();
            return object;
        } catch (Exception e) {
            System.err.println("Error Updating " + type.getSimpleName() + ": " + e.getMessage());
            throw new DataAccessException(e);
        } finally {
            em.close();
        }
    }

    @Override
    public T update(PK id, T object) throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            object = em.merge(object);
            transaction.commit();
            return object;
        } catch (Exception e) {
            System.err.println("Error Updating " + type.getSimpleName() + ": " + e.getMessage());
            throw new DataAccessException(e);
        } finally {
            em.close();
        }
    }

    @Override
    public Collection<T> list(int offset, int count) throws DataAccessException {
        emf.createEntityManager();
        EntityManager em = emf.createEntityManager();
        List<T> entity = null;
        try {
            entity = em.createQuery("SELECT e FROM "+ tableName +" e", type).getResultList()
                    .subList(offset, offset + count);
        } catch (Exception e){
            throw new DataAccessException(e);
        } finally {
            em.close();
        }
        return entity;
    }

    @Override
    public Collection<T> listAll() throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        List<T> entity = null;
        try {
            entity = em.createQuery("SELECT e FROM "+ tableName +" e", type).getResultList();
        } catch (Exception e){
            throw new DataAccessException(e);
        } finally {
            em.close();
        }
        return entity;
    }

    /**
     * To set the EntityManagerFactory for testing outside JavaEE containers.
     * ONLY FOR TESTING!
     *
     * @param emf EntityManagerFactory to use
     */
    @Override
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }
}
