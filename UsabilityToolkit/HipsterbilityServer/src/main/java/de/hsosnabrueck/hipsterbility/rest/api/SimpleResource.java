package de.hsosnabrueck.hipsterbility.rest.api;

import de.hsosnabrueck.hipsterbility.model.Device;
import de.hsosnabrueck.hipsterbility.rest.service.DeviceService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Albert on 15.09.2014.
 */
public interface SimpleResource<T> {

        @GET
        @Path("{id}")
        @Produces(MediaType.APPLICATION_JSON)
        public T get(@PathParam("id") int id);

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public List<T> list();

        @DELETE
        @Path("{id}")
        @Consumes(MediaType.APPLICATION_JSON)
        public Response delete(@PathParam("id") int id);

        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        public Response create(T object);

        @PUT
        @Path("{id}")
        @Consumes(MediaType.APPLICATION_JSON)
        public Response update(@PathParam("id") int id, T object);

}
