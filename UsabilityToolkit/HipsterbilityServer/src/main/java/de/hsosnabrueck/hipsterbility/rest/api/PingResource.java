package de.hsosnabrueck.hipsterbility.rest.api;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by Albert on 03.10.2014.
 */
@PermitAll
@Path("/ping")
public class PingResource {
    @GET
    public Response ping(){
        return Response.ok().build();
    }
}
