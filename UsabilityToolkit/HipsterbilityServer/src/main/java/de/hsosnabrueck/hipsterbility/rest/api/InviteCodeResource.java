package de.hsosnabrueck.hipsterbility.rest.api;

import de.hsosnabrueck.hipsterbility.entities.InviteCodeEntity;
import de.hsosnabrueck.hipsterbility.rest.data.CreatedId;
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
        InviteCodeEntity invite = inviteCodeService.read(id);
        return invite != null ? Response.ok(invite).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(){
        return Response.ok(inviteCodeService.list()).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id){
       return inviteCodeService.delete(id) ? Response.ok().build() : Response.notModified("could not delete invite").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Context UriInfo uriInfo, InviteCodeEntity object){
        object = inviteCodeService.create(object);
        return null == object ? Response.notModified("invite or email address already exist").build()
                : Response.ok(new CreatedId(object.getId())).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, InviteCodeEntity object){
        return inviteCodeService.update(id, object) ?  Response.ok().build() : Response.notModified("could not update invite").build();
    }

}
