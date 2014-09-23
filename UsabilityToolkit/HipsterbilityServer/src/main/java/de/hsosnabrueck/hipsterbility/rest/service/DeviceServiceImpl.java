package de.hsosnabrueck.hipsterbility.rest.service;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;
import de.hsosnabrueck.hipsterbility.persistence.DeviceDao;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

/**
 * Created by Albert on 15.09.2014.
 */
@Singleton
public class DeviceServiceImpl implements DeviceService {

    @Inject
    DeviceDao deviceDao;

    @Override
    public Collection<DeviceEntity> list() {
        return deviceDao.listAll();
    }

    @Override
    public Collection<DeviceEntity> list(int startIndex, int count) {
        return deviceDao.list(startIndex, count);
    }

    @Override
    public DeviceEntity read(int id) {
        return deviceDao.retrieve(id);
    }

    @Override
    public void delete(int id) {
        deviceDao.delete(id);
    }

    @Override
    public DeviceEntity create(DeviceEntity device) {
        return deviceDao.save(device);
    }

    @Override
    public void update(int id, DeviceEntity device) {
        deviceDao.update(id, device);
    }

}
