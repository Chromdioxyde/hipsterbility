package de.hsosnabrueck.hipsterbility.rest.api;

import de.hsosnabrueck.hipsterbility.entities.TaskEntity;
import de.hsosnabrueck.hipsterbility.rest.api.Resource;
import de.hsosnabrueck.hipsterbility.rest.data.CreatedId;
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
    public Response get(int id) {
        return Response.ok(taskService.read(id)).build();
    }

    @Override
    public Response list() {
        return Response.ok(taskService.list()).build();
    }

    @Override
    public Response delete(int id) {
        return taskService.delete(id) ?  Response.status(Response.Status.OK).entity("task has been successfully deleted").type(MediaType.APPLICATION_JSON).build()
                : Response.notModified("could not deleta task").build();
    }

    @Override
    public Response create(@Context UriInfo uriInfo, TaskEntity task) {
        task = taskService.create(task);
        return null != task ? Response.ok(new CreatedId(task.getId())).build() : Response.notModified("could not create task").build();
    }

    @Override
    public Response update(int id, TaskEntity object) {
        return taskService.update(id, object) ? Response.ok("task has been successfully updated").build() : Response.notModified("could not update task").build();
    }
}
