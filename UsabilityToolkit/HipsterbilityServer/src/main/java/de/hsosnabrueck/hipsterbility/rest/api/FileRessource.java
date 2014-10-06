package de.hsosnabrueck.hipsterbility.rest.api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.File;

/**
 * Created by Albert on 06.10.2014.
 */
@Path("/files")
public class FileRessource {

    @POST
    @Produces("text/plain")
    public Response uploadFile(File file){
        return Response.ok().build();
    }
}
