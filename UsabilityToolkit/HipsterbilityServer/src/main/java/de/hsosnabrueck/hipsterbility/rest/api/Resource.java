package de.hsosnabrueck.hipsterbility.rest.api;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;

/**
 * Created by Albert on 15.09.2014.
 */
public interface Resource<T> {

        @GET
        @Path("{id}")
        @RolesAllowed(value = "User")
        @Produces(MediaType.APPLICATION_JSON)
        public T get(@PathParam("id") int id);

        @GET
        @RolesAllowed(value = "User")
        @Produces(MediaType.APPLICATION_JSON)
        public Collection<T> list();

        @DELETE
        @Path("{id}")
        @RolesAllowed(value = "User")
        @Consumes(MediaType.APPLICATION_JSON)
        public Response delete(@PathParam("id") int id);

        @POST
        @RolesAllowed(value = "User")
        @Consumes(MediaType.APPLICATION_JSON)
        public Response create(UriInfo uriInfo, T object);

        @PUT
        @Path("{id}")
        @RolesAllowed(value = "User")
        @Consumes(MediaType.APPLICATION_JSON)
        public Response update(@PathParam("id") int id, T object);

}
