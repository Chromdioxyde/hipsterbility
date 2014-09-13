package de.hsosnabrueck.hipsterbility.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Albert on 08.09.2014.
 */
public class Todo {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String description;
    private boolean active;


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
}
