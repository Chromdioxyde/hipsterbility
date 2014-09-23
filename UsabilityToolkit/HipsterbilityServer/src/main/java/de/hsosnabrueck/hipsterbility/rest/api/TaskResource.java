package de.hsosnabrueck.hipsterbility.rest.api;

import de.hsosnabrueck.hipsterbility.entities.TaskEntity;
import de.hsosnabrueck.hipsterbility.rest.service.TaskService;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;

/**
 * Created by Albert on 16.09.2014.
 */
@Path("/tasks")
public class TaskResource implements Resource<TaskEntity> {

    private final TaskService taskService;

    @Inject
    public TaskResource(TaskService taskService){
        this.taskService = taskService;
    }


    @Override
    public TaskEntity get(int id) {
        return taskService.read(id);
    }

    @Override
    public Collection<TaskEntity> list() {
        return taskService.list();
    }

    @Override
    public Response delete(int id) {
        taskService.delete(id);
        return Response.status(Response.Status.OK).entity("task has been successfully deleted").type(MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response create(@Context UriInfo uriInfo, TaskEntity object) {
//        return taskService.create(object);
        taskService.create(object);
        //TODO: change to created
        return Response.ok().build();
    }

    @Override
    public Response update(int id, TaskEntity object) {
        taskService.update(id, object);
        return Response.status(Response.Status.OK).entity("task has been successfully updated").type(MediaType.APPLICATION_JSON).build();
    }
}
