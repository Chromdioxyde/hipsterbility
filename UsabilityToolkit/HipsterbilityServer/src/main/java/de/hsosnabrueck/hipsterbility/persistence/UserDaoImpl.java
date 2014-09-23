package de.hsosnabrueck.hipsterbility.persistence;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;
import de.hsosnabrueck.hipsterbility.entities.UserEntity;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Created by Albert on 17.09.2014.
 */
@Singleton
public class UserDaoImpl extends BasicDaoImpl<UserEntity> implements UserDao {

    protected UserDaoImpl() {
        super(UserEntity.class, UserEntity.TABLE_NAME);
    }

    @Override
    public DeviceEntity retrieveDevice(int userId, int deviceId) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<DeviceEntity> query = em.createQuery("SELECT d FROM " + DeviceEntity.TABLE_NAME
                +" d INNER JOIN User u on u = d WHERE d.id = " + deviceId,DeviceEntity.class);
        return query.getSingleResult();
    }
}
