package de.hsosnabrueck.hipsterbility.rest.api;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;
import de.hsosnabrueck.hipsterbility.entities.TestSessionEntity;
import de.hsosnabrueck.hipsterbility.entities.UserEntity;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.annotation.MultipartConfig;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;

/**
 * Created by Albert on 23.09.2014.
 */
@PermitAll
public interface UserResource {

    @RolesAllowed("ADMIN")
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id);

    @RolesAllowed({"ADMIN","USER"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list();

    @RolesAllowed({"ADMIN"})
    @DELETE
    public Response delete(@PathParam("id") int id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(UserEntity userEntity);

    @RolesAllowed({"ADMIN"})
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, UserEntity userEntity);

    @RolesAllowed({"USER"})
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(UserEntity userEntity);

}
