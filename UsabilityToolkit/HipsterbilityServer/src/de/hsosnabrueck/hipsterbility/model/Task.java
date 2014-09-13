package de.hsosnabrueck.hipsterbility.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Albert on 08.09.2014.
 */
public class Task {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private boolean done;

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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
