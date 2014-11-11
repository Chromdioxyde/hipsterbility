package de.hsosnabrueck.hipsterbility.rest.service.impl;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;
import de.hsosnabrueck.hipsterbility.exceptions.DataAccessException;
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
    public Collection<DeviceEntity> list() throws DataAccessException {
        return deviceDao.listAll();
    }

    @Override
    public Collection<DeviceEntity> list(int startIndex, int count) throws DataAccessException {
        return deviceDao.list(startIndex, count);
    }

    @Override
    public DeviceEntity read(Integer id) throws DataAccessException {
        return deviceDao.read(id);
    }

    @Override
    public void delete(Integer id) throws DataAccessException {
        deviceDao.delete(id);
    }

    @Override
    public DeviceEntity create(DeviceEntity device) throws DataAccessException {
        return deviceDao.create(device);
    }

    @Override
    public DeviceEntity update(Integer id, DeviceEntity device) throws DataAccessException {
       return deviceDao.update(id, device);
    }

}
