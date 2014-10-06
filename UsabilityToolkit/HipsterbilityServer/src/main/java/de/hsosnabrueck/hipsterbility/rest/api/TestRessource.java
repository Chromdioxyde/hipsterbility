package de.hsosnabrueck.hipsterbility.rest.api;

import de.hsosnabrueck.hipsterbility.entities.TestEntity;
import de.hsosnabrueck.hipsterbility.persistence.TestDao;
import de.hsosnabrueck.hipsterbility.rest.service.TestService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Albert on 04.10.2014.
 */
@Path("/tests")
public class TestRessource {

    private TestService testService;

    @Inject
    public TestRessource(TestService testService){
        this.testService = testService;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id){
        TestEntity test = testService.read(id);
        return test != null ? Response.ok(test).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(){
        return Response.ok(testService.list()).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id){
        return testService.delete(id) ? Response.ok().build() : Response.notModified("could not delete test").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Context UriInfo uriInfo, TestEntity test){
        test = testService.create(test);
        return null != test ? Response.status(Response.Status.CREATED).build() : Response.notModified("could not create test").build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, TestEntity test){
        return testService.update(id, test) ? Response.ok().build() : Response.notModified("could not update test").build();
    }
}
