package de.hsosnabrueck.iui.informatik.vma.hipsterbility.model;


import javax.persistence.*;
import java.util.Date;

/**
 * Created by Albert on 09.11.2014.
 */
@Entity(name = "TaskResult")
public class TaskResultEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @ManyToOne @JoinColumn // @JsonBackReference
    private TaskEntity task;

    @ManyToOne @JoinColumn // @JsonBackReference
    private TestSessionEntity session;

    private boolean success;
    private String comment;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestampCompleted;

    public TaskResultEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaskEntity getTask() {
        return task;
    }

    public void setTask(TaskEntity task) {
        this.task = task;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getTimestampCompleted() {
        return timestampCompleted;
    }

    public void setTimestampCompleted(Date timestampCompleted) {
        this.timestampCompleted = timestampCompleted;
    }

    public TestSessionEntity getSession() {
        return session;
    }

    public void setSession(TestSessionEntity session) {
        this.session = session;
    }
}
