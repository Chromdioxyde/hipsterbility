package de.hsosnabrueck.hipsterbility.rest.api;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;
import de.hsosnabrueck.hipsterbility.entities.TestSessionEntity;
import de.hsosnabrueck.hipsterbility.entities.UserEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;

/**
 * Created by Albert on 23.09.2014.
 */
public interface UserResource {

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list();

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(UriInfo uriInfo, UserEntity userEntity);

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, UserEntity userEntity);

    @GET
    @Path("{id}/sessions")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSessions(@PathParam("id") int userId);

    @GET
    @Path("{id}/devices")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDevices(@PathParam("id") int userId);

}
