package de.hsosnabrueck.hipsterbility.entities;

import javax.persistence.*;
import java.util.*;

/**
 * Created by Albert on 14.09.2014.
 */
@Entity(name = "TestTemplate")
public class TestTemplateEntity {

    public static final String TABLE_NAME = "TestTemplate";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    private String description;
    @ManyToOne
    private TestAppEntity testApp;
    @ManyToMany
    private Collection<TodoTemplateEntity> todos;
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

    public TestAppEntity getTestApp() {
        return testApp;
    }

    public void setTestApp(TestAppEntity testApp) {
        this.testApp = testApp;
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

    public Collection<TodoTemplateEntity> getTodos() {
        return todos;
    }

    public void setTodos(Collection<TodoTemplateEntity> todos) {
        this.todos = todos;
    }
}
