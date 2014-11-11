package de.hsosnabrueck.hipsterbility.rest.api;

import de.hsosnabrueck.hipsterbility.config.ApplicationBinder;
import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;
import de.hsosnabrueck.hipsterbility.exceptions.DataAccessException;
import de.hsosnabrueck.hipsterbility.model.enums.DeviceClass;
import de.hsosnabrueck.hipsterbility.rest.service.DeviceService;
import de.hsosnabrueck.hipsterbility.rest.service.Service;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.inject.Singleton;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class DeviceResourceTest extends JerseyTest {

    @Mock
    private DeviceService serviceMock;

    /**
     * This is executed only once, not before each test.
     *
     * This will enable Mockito Annotations to be used.
     * This will enable log traffic and message dumping.
     * This will register the Injectable Provider to the ResourceConfiguration which will
     * allow for the mock objects and jersey test to be linked.
     */
    @Override
    protected Application configure() {

        MockitoAnnotations.initMocks(this);
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        ResourceConfig config = new ResourceConfig(DeviceResource.class);
//        ResourceConfig config = new ApplicationConfig();
        config.register(new InjectableProvider());
        config.register(new ApplicationBinder());
        return config;
    }

    /**
     * Invoke the read customer and check the http response is 200.
     */
    @Test
    public void testDeviceRecieveResponse() {

        try {
            when(serviceMock.read(Mockito.anyInt())).thenReturn(getMockDevice());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        Response response = target("devices/1").request().get();

        DeviceEntity deviceEntity = response.readEntity(DeviceEntity.class);

        assertEquals(200, response.getStatus());
        assertEquals(1, deviceEntity.getId());
        assertEquals(deviceEntity.getCustomName(),"My Nexus");
        assertEquals(deviceEntity.getDeviceClass(), DeviceClass.PHONE);
        assertEquals(deviceEntity.getName(),"Nexus 5");
        assertEquals(deviceEntity.getOsVersion(),"4.4.4");
    }

    /**
     * Invoke the delete customer and check the http response is 200.
     */
    @Test
    public void testDeviceDeleteResponse() {

        Entity<Integer> deivceId = Entity.entity(getMockDevice().getId(), MediaType.APPLICATION_JSON_TYPE);

        try {
            doNothing().when(serviceMock).create(Mockito.any(DeviceEntity.class));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        Response response = target("devices/"+deivceId.getEntity()).request().delete();

        assertEquals(200, response.getStatus());
        assertEquals("device has been successfully deleted", response.readEntity(String.class));
    }

    /**
     * Invoke the create customer and check the http response is 200.
     */
    @Test
    public void testDeviceCreateResponse() {

        Entity<DeviceEntity> deviceEntity = Entity.entity(getMockDevice(), MediaType.APPLICATION_JSON_TYPE);

        try {
            doNothing().when(serviceMock).create(Mockito.any(DeviceEntity.class));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        Response response = target("devices").request().post(deviceEntity);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals("device has been successfully created", response.readEntity(String.class));
    }

    @Test
    public void testDeviceUpdateResponse() {

        Entity<DeviceEntity> deviceEntity = Entity.entity(getMockDevice(), MediaType.APPLICATION_JSON_TYPE);

        try {
            doNothing().when(serviceMock).create(Mockito.any(DeviceEntity.class));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        Response response = target("devices/1").request().put(deviceEntity);

        assertEquals(200, response.getStatus());
        assertEquals("device has been successfully updated", response.readEntity(String.class));
    }


    // ======= Mocking ==========

    /**
     * Mock object that will be returned
     *
     * @return the device object
     */
    private DeviceEntity getMockDevice() {
        DeviceEntity device = new DeviceEntity();
        device.setId(1);
        device.setCustomName("My Nexus");
        device.setDeviceClass(DeviceClass.PHONE);
        device.setName("Nexus 5");
        device.setOsVersion("4.4.4");
        return device;
    }

    /**
     * Create an Injectable Provider that with bind this factory to the device service.
     * When the provide is invoked a mock service object will be returned.
     * When dispose is invoked the mock service object will be assigned null.
     *
     * @author Robert Leggett
     *
     */
    class InjectableProvider extends AbstractBinder implements Factory<Service> {

        @Override
        protected void configure() {
            bindFactory(this).to(Service.class).in(Singleton.class);
        }

        public Service provide() {
            return serviceMock;
        }

        public void dispose(Service service) {
            serviceMock = null;
        }
    }

}