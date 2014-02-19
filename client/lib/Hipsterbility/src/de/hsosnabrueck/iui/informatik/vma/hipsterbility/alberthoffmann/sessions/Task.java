package de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.sessions;

/**
 * Created by albert on 19.02.14.
 * Simple POJO class for tasks of a todo
 */
public class Task {

    private int id;
    private String name;
    private Todo todo;
    private boolean done;

    public Task(int id, String name, Todo todo) {
        this.id = id;
        this.name = name;
        this.todo = todo;
        this.done = false;
    }

    public Task(int id, String name, Todo todo, boolean done) {
        this(id, name, todo);
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Todo getTodo() {
        return todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
