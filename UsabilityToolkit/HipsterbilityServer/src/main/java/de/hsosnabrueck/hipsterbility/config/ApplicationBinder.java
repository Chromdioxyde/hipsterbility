package de.hsosnabrueck.hipsterbility.config;

import de.hsosnabrueck.hipsterbility.persistence.DeviceDao;
import de.hsosnabrueck.hipsterbility.persistence.impl.DeviceDaoImpl;
import de.hsosnabrueck.hipsterbility.rest.service.DeviceService;
import de.hsosnabrueck.hipsterbility.rest.service.DeviceServiceImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

/**
 * Created by Albert on 15.09.2014.
 */
public class ApplicationBinder extends AbstractBinder{
    @Override
    protected void configure() {
        // services
        bind(DeviceServiceImpl.class).to(DeviceService.class).in(Singleton.class);

        // dao
        bind(DeviceDaoImpl.class).to(DeviceDao.class).in(Singleton.class);
    }
}
