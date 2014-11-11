package de.hsosnabrueck.hipsterbility.rest.api;

import de.hsosnabrueck.hipsterbility.entities.InviteCodeEntity;
import de.hsosnabrueck.hipsterbility.exceptions.DataAccessException;
import de.hsosnabrueck.hipsterbility.rest.service.InviteCodeService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Albert on 04.10.2014.
 */
@Path("/invites")
@RolesAllowed({"ADMIN"})
public class InviteCodeResource {

    private InviteCodeService inviteCodeService;

    @Inject
    public InviteCodeResource(InviteCodeService inviteCodeService){
        this.inviteCodeService = inviteCodeService;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id){
        try {
            InviteCodeEntity invite = inviteCodeService.read(id);
            return Response.ok(invite).build();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(){
        try {
            return Response.ok(inviteCodeService.list()).build();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id){
        try {
            inviteCodeService.delete(id);
            return Response.ok().build();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Response.notModified(e.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Context UriInfo uriInfo, InviteCodeEntity object){
        try {
            object = inviteCodeService.create(object);
            return Response.ok(object.getId()).build();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Response.notModified(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, InviteCodeEntity object){
        try {
            inviteCodeService.update(id, object);
            return Response.ok().build();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Response.notModified(e.getMessage()).build();
        }
    }

}
