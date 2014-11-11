package de.hsosnabrueck.hipsterbility.persistence.impl;

import de.hsosnabrueck.hipsterbility.entities.UserEntity;
import de.hsosnabrueck.hipsterbility.exceptions.DataAccessException;
import de.hsosnabrueck.hipsterbility.persistence.UserDao;

import javax.inject.Singleton;
import javax.persistence.EntityManager;

/**
 * Created by Albert on 17.09.2014.
 */
@Singleton
public class UserDaoImpl extends GenericDaoImpl<UserEntity, Integer> implements UserDao {

    protected UserDaoImpl() {
        super(UserEntity.class, UserEntity.TABLE_NAME);
    }

    @Override
    public UserEntity findByUsername(String username) throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        UserEntity user = null;
        try {
            user = em.createNamedQuery("User.findByUsername", UserEntity.class).setParameter("username", username).getSingleResult();
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw new DataAccessException(e);
        } finally {
            em.close();
        }
        return user;
    }

//    @Override
//    public DeviceEntity retrieveDevice(int userId, int deviceId) {
//        EntityManager em = emf.createEntityManager();
//        DeviceEntity deviceEntity = null;
//        try {
//            TypedQuery<DeviceEntity> query = em.createQuery("SELECT d FROM " + DeviceEntity.TABLE_NAME
//                    + " d INNER JOIN User u on u = d WHERE d.id = " + deviceId, DeviceEntity.class);
//            deviceEntity = query.getSingleResult();
//        } catch (Exception e){
//            System.err.println(e.getMessage());
//        } finally {
//            em.close();
//        }
//        return deviceEntity;
//    }

    @Override
    public UserEntity findByEmail(String email) throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        UserEntity user = null;
        try {
            user = em.createNamedQuery("User.findByEmail", UserEntity.class).setParameter("email", email).getSingleResult();
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw new DataAccessException(e);
        } finally {
            em.close();
        }
        return user;
    }
}
