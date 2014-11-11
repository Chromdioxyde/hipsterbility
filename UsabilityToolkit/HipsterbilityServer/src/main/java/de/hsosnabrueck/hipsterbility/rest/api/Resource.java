package de.hsosnabrueck.hipsterbility.rest.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.Serializable;

/**
 * Created by Albert on 15.09.2014.
 */
public interface Resource<T, PK extends Serializable> {

        @GET
        @Path("{id}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response get(@PathParam("id") PK id);

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Response list();

        @DELETE
        @Path("{id}")
        public Response delete(@PathParam("id") PK id);

        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        public Response create(UriInfo uriInfo, T object);

        @PUT
        @Path("{id}")
        @Consumes(MediaType.APPLICATION_JSON)
        public Response update(@PathParam("id") PK id, T object);

}
