package de.hsosnabrueck.hipsterbility.persistence.impl;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;
import de.hsosnabrueck.hipsterbility.persistence.DeviceDao;

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
}
