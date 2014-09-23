package de.hsosnabrueck.hipsterbility.persistence;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import java.util.Collection;

/**
 * Created by Albert on 15.09.2014.
 */
@Singleton
public class DeviceDaoImpl extends BasicDaoImpl<DeviceEntity> implements DeviceDao {

    public DeviceDaoImpl() {
        super(DeviceEntity.class, DeviceEntity.TABLE_NAME);
    }

    @Override
    public Collection<DeviceEntity> listDevicesForUser(int userId) {
        EntityManager em = emf.createEntityManager();
        // TODO implement or remove
        return null;
    }

    @Override
    public DeviceEntity retrieveDeviceForUser(int userId, int deviceId) {
        // TODO implement or remove
        return null;
    }
}
