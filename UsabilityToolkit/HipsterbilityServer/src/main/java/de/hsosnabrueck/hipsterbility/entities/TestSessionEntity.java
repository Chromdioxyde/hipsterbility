package de.hsosnabrueck.hipsterbility.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import de.hsosnabrueck.hipsterbility.entities.files.AudioFileEntity;
import de.hsosnabrueck.hipsterbility.entities.files.LogFileEntity;
import de.hsosnabrueck.hipsterbility.entities.files.ScreenshotFileEntity;
import de.hsosnabrueck.hipsterbility.entities.files.VideoFileEntity;

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

    private String description;

    private boolean active;

    @ManyToOne @JoinColumn
    private TestAppEntity testApp;

    @ManyToOne @JoinColumn @JsonBackReference("device-session")
    private DeviceEntity device;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private Collection<VideoFileEntity> videos;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private Collection<LogFileEntity> logs;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private Collection<AudioFileEntity> audios;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private Collection<ScreenshotFileEntity> screenshots;

    @ManyToOne
    private TestEntity test;

    @ManyToOne @JsonBackReference("user-session")
    private UserEntity tester;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStarted;

    @Column(updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeFinished;

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

    public Collection<VideoFileEntity> getVideos() {
        return videos;
    }

    public void setVideos(Collection<VideoFileEntity> videos) {
        this.videos = videos;
    }

    public Collection<LogFileEntity> getLogs() {
        return logs;
    }

    public void setLogs(Collection<LogFileEntity> logs) {
        this.logs = logs;
    }

    public Collection<AudioFileEntity> getAudios() {
        return audios;
    }

    public void setAudios(Collection<AudioFileEntity> audios) {
        this.audios = audios;
    }

    public Date getTimeStarted() {
        return timeStarted;
    }

    public void setTimeStarted(Date timestampCreated) {
        this.timeStarted = timestampCreated;
    }

    public Date getTimeFinished() {
        return timeFinished;
    }

    public void setTimeFinished(Date timestampModified) {
        this.timeFinished = timestampModified;
    }

    public UserEntity getTester() {
        return tester;
    }

    public void setTester(UserEntity testUser) {
        this.tester = testUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<ScreenshotFileEntity> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(Collection<ScreenshotFileEntity> screenshots) {
        this.screenshots = screenshots;
    }

    public TestEntity getTest() {
        return test;
    }

    public void setTest(TestEntity test) {
        this.test = test;
    }
}
