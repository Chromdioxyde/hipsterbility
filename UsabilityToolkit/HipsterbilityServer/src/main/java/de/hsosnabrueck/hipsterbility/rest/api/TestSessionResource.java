package de.hsosnabrueck.hipsterbility.rest.api;

import de.hsosnabrueck.hipsterbility.entities.TestSessionEntity;
import de.hsosnabrueck.hipsterbility.entities.UserEntity;
import de.hsosnabrueck.hipsterbility.entities.files.AudioFileEntity;
import de.hsosnabrueck.hipsterbility.rest.data.CreatedId;
import de.hsosnabrueck.hipsterbility.rest.service.TestSessionService;
import de.hsosnabrueck.hipsterbility.rest.service.UserService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.File;
import java.util.Collection;

/**
 * Created by Albert on 08.09.2014.
 */
@Path("/sessions")
@RolesAllowed({"ADMIN","USER"})
public class TestSessionResource {

    private final TestSessionService sessionService;
    private final UserService userService;

    @Context
    SecurityContext securityContext;

    @Inject
    public TestSessionResource(TestSessionService sessionService, UserService userService){
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @RolesAllowed({"ADMIN"})
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id){
        TestSessionEntity session = sessionService.read(id);
        return session != null ? Response.ok(session).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        Collection<TestSessionEntity> sessions;
        if (securityContext.isUserInRole("ADMIN")) {
            sessions = sessionService.list();
        } else {
            sessions = getUser().getSessions();
        }
        return Response.ok(sessions).build();
    }

    @RolesAllowed({"ADMIN"})
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id){
        return sessionService.delete(id) ? Response.ok("session deleted").build() : Response.notModified("could not delete session").build();
    }

    @RolesAllowed({"ADMIN","USER"})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Context UriInfo uriInfo, TestSessionEntity session){
        session = sessionService.create(session);
        return null != session ? Response.ok(new CreatedId(session.getId())).build() : Response.notModified("could not create session").build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, TestSessionEntity session){
        return sessionService.update(id, session) ? Response.ok("updated session").build() : Response.notModified("could not update session").build();
    }

    @RolesAllowed({"USER", "ADMIN"})
    @POST
    @Path("{id}/audios")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAudioFile(@PathParam("id") int id, AudioFileEntity audioFileEntity){
        File file = audioFileEntity.getFile();

        return Response.ok().build();
    }

    /**
     * Gets the UserEntity for the currently logged in user
     * @return UserEntity
     */
    private UserEntity getUser(){
        return userService.findByName(securityContext.getUserPrincipal().getName());
    }

}
