package de.hsosnabrueck.hipsterbility.rest.api;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;
import de.hsosnabrueck.hipsterbility.entities.UserEntity;
import de.hsosnabrueck.hipsterbility.rest.data.CreatedId;
import de.hsosnabrueck.hipsterbility.rest.api.DeviceResource;
import de.hsosnabrueck.hipsterbility.rest.service.DeviceService;
import de.hsosnabrueck.hipsterbility.rest.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * Created by Albert on 08.09.2014.
 */

@Path("/devices")
public class DeviceResourceImpl implements DeviceResource{

    private final DeviceService deviceService;
    private final UserService userService;

    @Context
    SecurityContext securityContext;
    @Context
    UriInfo uriInfo;

    @Inject
    public DeviceResourceImpl(DeviceService deviceService, UserService userService){
        this.deviceService = deviceService;
        this.userService = userService;
    }

    @Override
    public Response getDeviceList() {
        if(securityContext.isUserInRole("ADMIN")){
            return Response.ok(deviceService.list()).build();
        } else {
            return Response.ok(getUser().getDevices()).build();
        }
    }

    @Override
    public Response getDevice(@PathParam("id") int id) {
        DeviceEntity device = deviceService.read(id);
        return device != null ? Response.ok(device).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @Override
    public Response deleteDevice(@PathParam("id") int id) {
        return deviceService.delete(id) ? Response.ok().build() : Response.notModified("could not delete device").build();
    }

    @Override
    public Response createDevice(DeviceEntity device) {
        if(securityContext.isUserInRole("Admin")){
            device = deviceService.create(device);
        }
        return null != device ? Response.status(Response.Status.CREATED).entity(new CreatedId(device.getId())).build()
                : Response.notModified().build();
    }

    @Override
    public Response createDeviceForUser(@PathParam("id") int id, DeviceEntity device) {
        UserEntity user = userService.read(id);
        if(null == user) return Response.status(Response.Status.BAD_REQUEST).build();
        device.setUser(user);
        user.getDevices().add(device);
        userService.update(user.getId(), user);
        System.out.println(uriInfo.getAbsolutePath());
        return Response.status(Response.Status.CREATED).entity(device.getId()).build();
    }

    @Override
    public Response updateDevice(@PathParam("id") int id, DeviceEntity device) {
        return deviceService.update(id, device) ? Response.ok().build() : Response.notModified().build();
    }

    /**
     * Gets the UserEntity for the currently logged in user
     * @return UserEntity
     */
    private UserEntity getUser(){
        return userService.findByName(securityContext.getUserPrincipal().getName());
    }
}
