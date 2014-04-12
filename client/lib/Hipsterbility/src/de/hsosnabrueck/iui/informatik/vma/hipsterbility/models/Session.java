package de.hsosnabrueck.iui.informatik.vma.hipsterbility.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created on 13.02.14.
 */
public class Session {

    // TODO: Implementation

    //================================================================================
    // Properties
    //================================================================================

    @SerializedName("idsessions")
    private long id;
    private String name;
    private String description;
    private Device device;
    private App app;
    private User user;
    private boolean active;
    /**
     * Make this array list transient to circumvent serialization issues
     */
    private transient ArrayList<Todo> todos;

    /**
     * Default constructor is used for (de-)serialization
     */
    public Session() {
    }

    /**
     * Mini-constructor, mainly for testing.
     * TODO: remove if not needed anymore
     *
     * @param id
     */
    public Session(long id) {
        this.id = id;
        this.todos = new ArrayList<Todo>();
    }


    public Session(long id, String name, String description, Device device, App app, boolean active) {
        this(id);
        this.name = name;
        this.description = description;
        this.device = device;
        this.app = app;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ArrayList<Todo> getTodos() {
        return todos;
    }

    public void setTodos(ArrayList<Todo> todos) {
        this.todos = todos;
    }

    public void addTodo(Todo todo) {
        this.todos.add(todo);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    private class SessionDeserializer implements JsonDeserializer<Session> {
//        public Session deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
//                throws JsonParseException {
//
//            // The variable 'json' is passed as a parameter to the deserialize() method
//            final JsonObject jsonObject = json.getAsJsonObject();
//            final long id = jsonObject.get("idsessions").getAsLong();
//            final String name = jsonObject.get("name").getAsString();
//            final boolean active = jsonObject.get("active").getAsBoolean();
////            final long appId = jsonObject.get("apps_idapps").getAsLong();
//            Session s = new Session();
//            s.setId(id);
//            s.setName(name);
//            s.setActive(active);
//            return s;
//        }
//    }

}
