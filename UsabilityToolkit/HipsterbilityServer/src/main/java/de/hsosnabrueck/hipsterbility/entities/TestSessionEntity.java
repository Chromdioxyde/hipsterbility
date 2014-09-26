package de.hsosnabrueck.hipsterbility.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * Created by Albert on 08.09.2014.
 */

@Entity(name = "Session")
public class TestSessionEntity {

    public static final String TABLE_NAME = "Session";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    private boolean active;
    @ManyToOne
    private TestAppEntity testApp;
    @ManyToOne
    private DeviceEntity device;
    @OneToMany
    private Collection<VideoEntity> videos;
    @OneToMany(mappedBy = LogEntity.TABLE_NAME)
    private Collection<LogEntity> logs;
    @OneToMany
    private Collection<AudioEntity> audios;
    @OneToMany
    private Collection<TodoEntity> todos;
    @ManyToOne @JsonBackReference
    private UserEntity tester;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public TestAppEntity getTestAppEntity() {
        return testApp;
    }

    public void setTestAppEntity(TestAppEntity testAppEntity) {
        this.testApp = testAppEntity;
    }

    public DeviceEntity getDeviceEntity() {
        return device;
    }

    public void setDeviceEntity(DeviceEntity deviceEntity) {
        this.device = deviceEntity;
    }

    public TestAppEntity getTestApp() {
        return testApp;
    }

    public void setTestApp(TestAppEntity testApp) {
        this.testApp = testApp;
    }

    public DeviceEntity getDevice() {
        return device;
    }

    public void setDevice(DeviceEntity device) {
        this.device = device;
    }

    public Collection<VideoEntity> getVideos() {
        return videos;
    }

    public void setVideos(Collection<VideoEntity> videos) {
        this.videos = videos;
    }

    public Collection<LogEntity> getLogs() {
        return logs;
    }

    public void setLogs(Collection<LogEntity> logs) {
        this.logs = logs;
    }

    public Collection<AudioEntity> getAudios() {
        return audios;
    }

    public void setAudios(Collection<AudioEntity> audios) {
        this.audios = audios;
    }

    public Collection<TodoEntity> getTodos() {
        return todos;
    }

    public void setTodos(Collection<TodoEntity> todos) {
        this.todos = todos;
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

    public UserEntity getTester() {
        return tester;
    }

    public void setTester(UserEntity testUser) {
        this.tester = testUser;
    }
}
