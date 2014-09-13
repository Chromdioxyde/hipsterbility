package de.hsosnabrueck.hipsterbility.model;

import de.hsosnabrueck.hipsterbility.model.enums.UserGroup;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Albert on 08.09.2014.
 */
@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String password; // TODO encrypt or hash
    private boolean active;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<UserGroup> groups;
    @ElementCollection
    private List<Session> sessions;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
