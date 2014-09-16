package de.hsosnabrueck.hipsterbility.rest.service;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;
import de.hsosnabrueck.hipsterbility.model.Device;
import de.hsosnabrueck.hipsterbility.persistence.DeviceDao;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Albert on 15.09.2014.
 */
@Singleton
public class DeviceServiceImpl implements DeviceService {

    @Inject
    DeviceDao deviceDao;

    @Override
    public Collection<Device> list() {
        return toDevice(deviceDao.listAll());
    }

    @Override
    public Collection<Device> list(int startIndex, int count) {
        return toDevice(deviceDao.list(startIndex, count));
    }

    @Override
    public Device read(int id) {
        return toDevice(deviceDao.retrieve(id));
    }

    @Override
    public void delete(int id) {
        deviceDao.delete(id);
    }

    @Override
    public void create(Device device) {
        deviceDao.save(toEntity(device));
    }

    @Override
    public void update(int id, Device device) {
        deviceDao.update(id, toEntity(device));
    }

    // Conversion methods between entities and POJOs

    private DeviceEntity toEntity(Device device) {
        DeviceEntity entity = new DeviceEntity();
        if(null != device){
            entity.setId(device.getId());
            entity.setCustomName(device.getCustomName());
            entity.setOsVersion(device.getOsVersion());
            entity.setName(device.getName());
            entity.setDeviceClass(device.getDeviceClass());
        }
        return entity;
    }

    private Device toDevice(DeviceEntity entity){
        Device device = new Device();
        if(null != entity){
            device.setId(entity.getId());
            device.setName(entity.getName());
            device.setCustomName(entity.getCustomName());
            device.setOsVersion(entity.getOsVersion());
            device.setDeviceClass(entity.getDeviceClass());
        }
        return  device;
    }

    private Collection<Device> toDevice(Collection<DeviceEntity> deviceEntities) {
        ArrayList<Device> result = new ArrayList<Device>(deviceEntities.size());
        for(DeviceEntity e : deviceEntities){
            result.add(toDevice(e));
        }
        return result;
    }


}
