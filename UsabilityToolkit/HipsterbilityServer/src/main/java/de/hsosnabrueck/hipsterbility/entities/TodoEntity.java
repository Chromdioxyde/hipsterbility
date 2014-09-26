package de.hsosnabrueck.hipsterbility.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Albert on 08.09.2014.
 */
@Entity(name = "Todo")
public class TodoEntity {

    public static final String TABLE_NAME = "Todo";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    private String description;
    private boolean active;
    @OneToMany
    private Collection<TaskEntity> tasks;
    @ManyToOne
    private UserEntity creator;
    @ManyToOne
    private UserEntity modifier;
    @Column(insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestampCreated;
    @Column(insertable = false, updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestampModified;
    private Locale locale;

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

    public Collection<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(Collection<TaskEntity> tasks) {
        this.tasks = tasks;
    }

    public UserEntity getCreator() {
        return creator;
    }

    public void setCreator(UserEntity creator) {
        this.creator = creator;
    }

    public UserEntity getModifier() {
        return modifier;
    }

    public void setModifier(UserEntity modifier) {
        this.modifier = modifier;
    }

    public Date getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(Date timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public Date getTimestampModified() {
        return timestampModified;
    }

    public void setTimestampModified(Date timestampModified) {
        this.timestampModified = timestampModified;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
