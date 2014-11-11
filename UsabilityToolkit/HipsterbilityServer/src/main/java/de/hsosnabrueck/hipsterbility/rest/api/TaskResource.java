package de.hsosnabrueck.hipsterbility.rest.api;

import de.hsosnabrueck.hipsterbility.entities.TaskEntity;
import de.hsosnabrueck.hipsterbility.exceptions.DataAccessException;
import de.hsosnabrueck.hipsterbility.rest.service.TaskService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Albert on 16.09.2014.
 */
@Path("/tasks")

public class TaskResource implements Resource<TaskEntity, Integer> {

    private final TaskService taskService;

    @Inject
    public TaskResource(TaskService taskService){
        this.taskService = taskService;
    }

    @Override
    @RolesAllowed({"ADMIN","USER"})
    public Response get(Integer id) {
        try {
            return Response.ok(taskService.read(id)).build();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @Override
    @RolesAllowed({"ADMIN","USER"})
    public Response list() {
        try {
            return Response.ok(taskService.list()).build();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @Override
    @RolesAllowed({"ADMIN"})
    public Response delete(Integer id) {
        try {
            taskService.delete(id);
            return Response.ok().build();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Response.notModified(e.getMessage()).build();
        }
    }

    @Override
    @RolesAllowed({"ADMIN"})
    public Response create(@Context UriInfo uriInfo, TaskEntity task) {
        try {
            task = taskService.create(task);
            return Response.ok(task.getId()).build();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return  Response.notModified(e.getMessage()).build();
        }
    }

    @Override
    @RolesAllowed({"ADMIN"})
    public Response update(Integer id, TaskEntity object) {
        try {
            taskService.update(id, object);
            return Response.ok().build();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Response.notModified(e.getMessage()).build();
        }
    }
}
