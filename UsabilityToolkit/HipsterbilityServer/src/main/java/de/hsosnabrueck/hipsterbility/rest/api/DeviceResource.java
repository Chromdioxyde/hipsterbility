package de.hsosnabrueck.hipsterbility.rest.api;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;
import de.hsosnabrueck.hipsterbility.rest.service.DeviceService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

/**
 * Created by Albert on 08.09.2014.
 */

@Path("/devices")
public class DeviceResource {

    private final DeviceService deviceService;

    @Inject
    public DeviceResource(DeviceService deviceService){
        this.deviceService = deviceService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<DeviceEntity> getDeviceList() {
        return deviceService.list();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DeviceEntity getDevice(@PathParam("id") int id) {
        return deviceService.read(id);
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteDevice(@PathParam("id") int id) {
        deviceService.delete(id);
        return Response.status(Response.Status.OK).entity("device has been successfully deleted").type(MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDevice(DeviceEntity device) {
        deviceService.create(device);
        return Response.status(Response.Status.CREATED).entity("device has been successfully created").type(MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDevice(@PathParam("id") int id, DeviceEntity device) {
        deviceService.update(id, device);
        return Response.status(Response.Status.OK).entity("device has been successfully updated").type(MediaType.APPLICATION_JSON).build();
    }
}
