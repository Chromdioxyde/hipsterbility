package de.hsosnabrueck.hipsterbility.rest.api;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;
import de.hsosnabrueck.hipsterbility.entities.UserEntity;
import de.hsosnabrueck.hipsterbility.exceptions.DataAccessException;
import de.hsosnabrueck.hipsterbility.rest.service.DeviceService;
import de.hsosnabrueck.hipsterbility.rest.service.UserService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * Created by Albert on 08.09.2014.
 */

@Path("/devices")
public class DeviceResource {

    private final DeviceService deviceService;
    private final UserService userService;

    @Context
    SecurityContext securityContext;
    @Context
    UriInfo uriInfo;

    @Inject
    public DeviceResource(DeviceService deviceService, UserService userService){
        this.deviceService = deviceService;
        this.userService = userService;
    }

    @RolesAllowed({"ADMIN","USER"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeviceList() {
        try {
            if (securityContext.isUserInRole("ADMIN")) {
                return Response.ok(deviceService.list()).build();
            } else {
                return Response.ok(getUser().getDevices()).build();
            }
        } catch (DataAccessException e){
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @RolesAllowed({"ADMIN","USER"})
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDevice(@PathParam("id") int id) {
        try {
            DeviceEntity device = deviceService.read(id);
            return Response.ok(device).build();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @RolesAllowed("ADMIN")
    @DELETE
    @Path("{id}")
    public Response deleteDevice(@PathParam("id") int id) {
        try {
            deviceService.delete(id);
            return Response.ok().build();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Response.notModified(e.getMessage()).build();
        }
    }

    @RolesAllowed({"USER"})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    public Response createDevice(DeviceEntity device) {
        try {
            device = deviceService.create(device);
            return Response.status(Response.Status.CREATED).entity(device.getId()).build();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Response.notModified(e.getMessage()).build();
        }
    }


    @RolesAllowed({"ADMIN"})
    @POST
    @Path("/users/{id}")
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDeviceForUser(@PathParam("id") int userId, DeviceEntity device) {
        try {
            UserEntity user = userService.read(userId);
            user.getDevices().add(device);
            device.setUser(user);
            device = deviceService.create(device);
            return Response.status(Response.Status.CREATED).entity(device.getId()).build();
        } catch (DataAccessException e){
            e.printStackTrace();
            return Response.notModified(e.getMessage()).build();
        }
    }

    @RolesAllowed({"ADMIN","USER"})
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDevice(@PathParam("id") int id, DeviceEntity device) {
        try {
            deviceService.update(id, device);
            return Response.ok().build();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Response.notModified(e.getMessage()).build();
        }
    }

    /**
     * Gets the UserEntity for the currently logged in user
     * @return UserEntity
     */
    private UserEntity getUser() throws DataAccessException {
        return userService.findByName(securityContext.getUserPrincipal().getName());
    }
}
