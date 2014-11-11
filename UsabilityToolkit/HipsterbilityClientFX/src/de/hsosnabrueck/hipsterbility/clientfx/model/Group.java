package de.hsosnabrueck.hipsterbility.clientfx.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Albert on 03.11.2014.
 */
public class Group {

    public static final String API_PATH = "groups";

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    private StringProperty name;
    @JsonBackReference("user-group")
    private List<User> users;

    public Group() {
        this.name = new SimpleStringProperty();
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public List<User> getUsers() {
        if(null == this.users) this.users = new ArrayList<>();
        return users;
    }

    public void setUsers(List<User> user) {
        this.users = user;
    }
}
