package de.hsosnabrueck.hipsterbility.rest.api;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Albert on 25.09.2014.
 */
@RolesAllowed({"ADMIN","USER"})
public interface DeviceResource {

    @RolesAllowed({"ADMIN","USER"})
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDevice(@PathParam("id") int id);

    @RolesAllowed({"ADMIN","USER"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeviceList();

    @RolesAllowed("ADMIN")
    @DELETE
    @Path("{id}")
    public Response deleteDevice(@PathParam("id") int id);

    @RolesAllowed({"USER"})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDevice(DeviceEntity object);

    @RolesAllowed({"Admin"})
    @POST
    @Path("users/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDeviceForUser(@PathParam("id") int id, DeviceEntity object);

    @RolesAllowed({"ADMIN","USER"})
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDevice(@PathParam("id") int id, DeviceEntity object);

}
