package de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.sessions;


import java.util.ArrayList;

/**
 * Created by albert on 19.02.14.
 * POJO class for todos
 */
public class Todo {

    private int id;
    private String name;
    private String description;
    private boolean active;
    private Session session;
    private ArrayList<Task> tasks;

    public Todo(int id, String name, String description, boolean active, Session session) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.session = session;
        this.tasks = new ArrayList<Task>();
    }

    public Todo(int id, String name, String description, boolean active, Session session, ArrayList<Task> tasks) {
        this(id, name, description, active, session);
        this.tasks = tasks;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public Task getTask(int pos){
       return this.tasks.get(pos);
    }

    public void addTask(Task task){
        this.tasks.add(task);
    }
}
