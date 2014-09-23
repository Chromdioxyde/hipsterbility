package de.hsosnabrueck.hipsterbility.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Albert on 08.09.2014.
 */
@Entity(name = "Task")
public class TaskEntity {

    public static final String TABLE_NAME = "Task";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    private boolean done;
    private boolean success;
    @Temporal(TemporalType.TIMESTAMP)
    private Date started;
    @Temporal(TemporalType.TIMESTAMP)
    private Date finished;

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

    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    public Date getFinished() {
        return finished;
    }

    public void setFinished(Date finished) {
        this.finished = finished;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
