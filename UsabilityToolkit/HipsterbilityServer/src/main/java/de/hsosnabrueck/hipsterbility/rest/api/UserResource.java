package de.hsosnabrueck.hipsterbility.rest.api;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;
import de.hsosnabrueck.hipsterbility.entities.TestSessionEntity;
import de.hsosnabrueck.hipsterbility.entities.UserEntity;
import de.hsosnabrueck.hipsterbility.rest.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;

/**
 * Created by Albert on 08.09.2014.
 */
@Path("/users")
public class UserResource implements Resource<UserEntity> {

    private final UserService userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserEntity get(int id) {
        return userService.read(id);
    }

    @Override
    public Collection<UserEntity> list() {
        return userService.list();
    }

    @Override
    public Response delete(int id) {
        return Response.notModified().build();
    }

    @Override
    public Response create(@Context UriInfo uriInfo, UserEntity object) {
        UserEntity e = userService.create(object);
//        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().path(String.valueOf(e.getId()));
//        Response.ResponseBuilder builder = Response.created(uriBuilder.build()).entity(e);
//        return builder.build();
        return null;
    }

    @Override
    public Response update(int id, UserEntity object) {
        userService.update(id, object);
        return Response.ok().build();
    }

    @GET
    @Path("{id}/sessions")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<TestSessionEntity> getSessions(@PathParam("id") int userId){
        return userService.readSessions(userId);
    }

    @GET
    @Path("{id}/devices")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<DeviceEntity> getDevices(@PathParam("id") int userId){
        return userService.readDevices(userId);
    }
}
