package de.hsosnabrueck.hipsterbility.persistence;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Albert on 15.09.2014.
 */
@Singleton
public class DeviceDaoImpl implements DeviceDao {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("HipsterbilityService");

    public DeviceEntity retrieve(int id) {
        EntityManager em = emf.createEntityManager();
        DeviceEntity entity = null;
        try {
            entity = em.find(DeviceEntity.class, id);
        } finally {
            em.close();
        }
        return entity;
    }

    public void delete(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            DeviceEntity entity = em.find(DeviceEntity.class, id);
            if(entity == null) {
                System.err.println("Error Deleting Device: Device not found");
            }
            else {
                em.remove(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Error Deleting Device: " + e.getMessage());
            transaction.rollback();
        } finally {
            em.close();
        }
    }

    public void save(DeviceEntity deviceEntity) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(deviceEntity);
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Error Saving Device: " + e.getMessage());
            transaction.rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(int id, DeviceEntity deviceEntity) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(deviceEntity);
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Error Updating Device: " + e.getMessage());
            transaction.rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public Collection<DeviceEntity> list(int offset, int count) {
        EntityManager em = emf.createEntityManager();
        List<DeviceEntity> entity = null;
        try {
            entity = em.createQuery("SELECT d FROM Device d", DeviceEntity.class).getResultList()
                    .subList(offset, offset + count);
        } finally {
            em.close();
        }
        return entity;
    }

    @Override
    public Collection<DeviceEntity> listAll() {
        EntityManager em = emf.createEntityManager();
        List<DeviceEntity> entity = null;
        try {
            entity = em.createQuery("SELECT d FROM Device d", DeviceEntity.class).getResultList();
        } finally {
            em.close();
        }
        return entity;
    }
}
