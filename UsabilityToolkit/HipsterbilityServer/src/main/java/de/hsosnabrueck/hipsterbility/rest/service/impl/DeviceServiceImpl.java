package de.hsosnabrueck.hipsterbility.rest.service.impl;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;
import de.hsosnabrueck.hipsterbility.persistence.DeviceDao;
import de.hsosnabrueck.hipsterbility.rest.service.DeviceService;

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
    public boolean delete(int id) {
        return deviceDao.delete(id);
    }

    @Override
    public DeviceEntity create(DeviceEntity device) {
        return deviceDao.save(device);
    }

    @Override
    public boolean update(int id, DeviceEntity device) {
       return deviceDao.update(id, device);
    }

}
