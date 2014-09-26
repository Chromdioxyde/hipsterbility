package de.hsosnabrueck.hipsterbility.rest.api;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;
import de.hsosnabrueck.hipsterbility.entities.TestSessionEntity;
import de.hsosnabrueck.hipsterbility.entities.UserEntity;
import de.hsosnabrueck.hipsterbility.rest.service.UserService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;

/**
 * Created by Albert on 08.09.2014.
 */
@Path("/users")
@PermitAll
public class UserResourceImpl implements UserResource {

    private final UserService userService;

    @Inject
    public UserResourceImpl(UserService userService) {
        this.userService = userService;
    }

    @Context
    SecurityContext securityContext;

    @RolesAllowed({"USER","ADMIN"})
    public Response get(int id) {
        if(securityContext.isUserInRole("ADMIN") ||
                checkValidUser(securityContext.getUserPrincipal().getName(), id)){
            return Response.ok(userService.read(id)).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

    }


    @RolesAllowed(value = "ADMIN")
    public Response list() {
        return Response.ok(userService.list()).build();
    }

    public Response delete(int id) {
        return Response.notModified().build();
    }

    @RolesAllowed(value = "ADMIN")
    public Response create(@Context UriInfo uriInfo, UserEntity object) {
        UserEntity e = userService.create(object);
//        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().path(String.valueOf(e.getId()));
//        Response.ResponseBuilder builder = Response.created(uriBuilder.build()).entity(e);
//        return builder.build();
        return null;
    }

    public Response update(int id, UserEntity object) {
        userService.update(id, object);
        return Response.ok().build();
    }

    public Response getSessions(@PathParam("id") int userId){
        return Response.ok(userService.readSessions(userId)).build();
    }

    public Response getDevices(@PathParam("id") int userId){
        return Response.ok(userService.readDevices(userId)).build();
    }


    /**
     * @param username login name from security context
     * @param id requested user id from path parameter
     * @return true if username and id match, false if not
     */
    private boolean checkValidUser(String username, int id){
        UserEntity userEntity = userService.findByName(username);
        return null != userEntity && userEntity.getId() == id;
    }
}
