package de.hsosnabrueck.hipsterbility.rest.api;

import de.hsosnabrueck.hipsterbility.entities.TodoEntity;
import de.hsosnabrueck.hipsterbility.rest.service.TaskService;

import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * Created by Albert on 16.09.2014.
 */
public class TodoResource implements Resource<TodoEntity> {

//    @Inject
//    public TodoResource(TodoService taskService){
//        this.taskService = taskService;
//    }


    @Override
    public Response get(int id) {
        return null;
    }

    @Override
    public Response list() {
        return null;
    }

    @Override
    public Response delete(int id) {
        return null;
    }

    @Override
    public Response create(@Context UriInfo uriInfo, TodoEntity object) {
        return null;
    }

    @Override
    public Response update(int id, TodoEntity object) {
        return null;
    }
}


