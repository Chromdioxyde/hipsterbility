package de.hsosnabrueck.hipsterbility.persistence.impl;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;
import de.hsosnabrueck.hipsterbility.persistence.DeviceDao;

import javax.inject.Singleton;

/**
 * Created by Albert on 15.09.2014.
 */
@Singleton
public class DeviceDaoImpl extends GenericDaoImpl<DeviceEntity, Integer> implements DeviceDao {

    protected DeviceDaoImpl() {
        super(DeviceEntity.class, DeviceEntity.TABLE_NAME);
    }
}
