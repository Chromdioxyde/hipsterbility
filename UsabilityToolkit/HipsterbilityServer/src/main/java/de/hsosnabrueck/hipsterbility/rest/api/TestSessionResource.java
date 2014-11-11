package de.hsosnabrueck.hipsterbility.rest.api;

import de.hsosnabrueck.hipsterbility.entities.FileEntity;
import de.hsosnabrueck.hipsterbility.entities.TestSessionEntity;
import de.hsosnabrueck.hipsterbility.entities.UserEntity;
import de.hsosnabrueck.hipsterbility.exceptions.DataAccessException;
import de.hsosnabrueck.hipsterbility.rest.service.FileService;
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
    private final FileService fileService;

    @Context
    SecurityContext securityContext;

    @Inject
    public TestSessionResource(TestSessionService sessionService, UserService userService, FileService fileService){
        this.sessionService = sessionService;
        this.userService = userService;
        this.fileService = fileService;
    }

    @RolesAllowed({"ADMIN"})
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id){
        TestSessionEntity session = null;
        try {
            session = sessionService.read(id);
            return Response.ok(session).build();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return  Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        Collection<TestSessionEntity> sessions;
        try {
            if (securityContext.isUserInRole("ADMIN")) {
                sessions = sessionService.list();
            } else {
                sessions = getUser().getSessions();
            }
            return Response.ok(sessions).build();
        } catch (DataAccessException e){
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @RolesAllowed({"ADMIN"})
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id){
        try {
            sessionService.delete(id);
            return Response.ok().build();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Response.notModified(e.getMessage()).build();
        }
    }

    @RolesAllowed({"ADMIN","USER"})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    public Response create(@Context UriInfo uriInfo, TestSessionEntity session){
        try {
            session = sessionService.create(session);
            return Response.ok(session.getId()).build();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Response.notModified(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, TestSessionEntity session){
        try {
            sessionService.update(id, session);
            return Response.ok().build();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Response.notModified(e.getMessage()).build();
        }
    }

    @RolesAllowed({"ADMIN","USER"})
    @POST
    @Path("{id}/files")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    public Response createFile(@Context UriInfo uriInfo, @PathParam("id") int id, FileEntity fileEntity){
        try {
            TestSessionEntity session = sessionService.read(id);
            fileEntity.setSession(session);
            fileEntity = fileService.create(fileEntity);
            session.getFiles().put(fileEntity.getType(), fileEntity);
            return Response.ok(session.getId()).build();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Response.notModified(e.getMessage()).build();
        }
    }


    /**
     * Gets the UserEntity for the currently logged in user
     * @return UserEntity
     */
    private UserEntity getUser() throws DataAccessException {
        return userService.findByName(securityContext.getUserPrincipal().getName());
    }

}
