package de.hsosnabrueck.hipsterbility.entities;

import de.hsosnabrueck.hipsterbility.model.enums.DeviceClass;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Albert on 14.09.2014.
 */
@Entity(name = "Test")
public class TestEntity {

    public static final String TABLE_NAME = "Test";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    private TestAppEntity testApp;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    private List<TaskEntity> tasks;

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

    private boolean template;

    @ElementCollection
    private Collection<DeviceClass> deviceClasses;

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

    public List<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
    }

    public boolean isTemplate() {
        return template;
    }

    public void setTemplate(boolean template) {
        this.template = template;
    }

    public Collection<DeviceClass> getDeviceClasses() {
        return deviceClasses;
    }

    public void setDeviceClasses(Collection<DeviceClass> deviceClasses) {
        this.deviceClasses = deviceClasses;
    }
}
