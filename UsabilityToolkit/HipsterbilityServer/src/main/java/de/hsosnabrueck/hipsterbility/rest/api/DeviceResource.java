package de.hsosnabrueck.hipsterbility.rest.api;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Albert on 25.09.2014.
 */
public interface DeviceResource {

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
    public Response create(UriInfo uriInfo, DeviceEntity object);

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, DeviceEntity object);
}
