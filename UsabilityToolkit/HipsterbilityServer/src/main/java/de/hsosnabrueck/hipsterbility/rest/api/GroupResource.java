package de.hsosnabrueck.hipsterbility.rest.api;

import de.hsosnabrueck.hipsterbility.entities.GroupEntity;
import de.hsosnabrueck.hipsterbility.exceptions.DataAccessException;
import de.hsosnabrueck.hipsterbility.rest.service.GroupService;
import de.hsosnabrueck.hipsterbility.rest.service.UserService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Albert on 09.11.2014.
 */
@Path("/groups")
@RolesAllowed({"ADMIN"})
public class GroupResource implements Resource<GroupEntity, String> {

    private UserService userService;
    private GroupService groupService;

    @Inject
    public GroupResource(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @Override
    public Response get(String id) {
        try {
            return Response.ok(groupService.read(id)).build();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response list() {
        try {
            return Response.ok(groupService.list()).build();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @Override
    public Response delete(String id) {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }

    @Override
    public Response create(@Context UriInfo uriInfo, GroupEntity group) {
        try {
            group = groupService.create(group);
            return Response.status(Response.Status.CREATED).entity(group.getName()).build();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Response.notModified(e.getMessage()).build();
        }
    }

    @Override
    public Response update(String id, GroupEntity object) {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }
}
