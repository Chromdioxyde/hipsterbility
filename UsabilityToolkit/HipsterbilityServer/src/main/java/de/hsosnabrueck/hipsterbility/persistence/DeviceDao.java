package de.hsosnabrueck.hipsterbility.persistence;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;

import java.util.Collection;

/**
 * Created by Albert on 15.09.2014.
 */
public interface DeviceDao extends Dao<DeviceEntity> {
    public Collection<DeviceEntity> listDevicesForUser(int userId);
    public DeviceEntity retrieveDeviceForUser(int userId, int deviceId);
}
