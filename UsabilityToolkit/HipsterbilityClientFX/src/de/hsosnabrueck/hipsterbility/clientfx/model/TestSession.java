package de.hsosnabrueck.hipsterbility.clientfx.model;

import de.hsosnabrueck.hipsterbility.clientfx.model.files.AudioFile;
import de.hsosnabrueck.hipsterbility.clientfx.model.files.LogFile;
import de.hsosnabrueck.hipsterbility.clientfx.model.files.ScreenshotFile;
import de.hsosnabrueck.hipsterbility.clientfx.model.files.VideoFile;
import javafx.beans.property.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Albert on 29.10.2014.
 */
public class TestSession {

    public static final String API_PATH = "sessions";

    private IntegerProperty id;
    private StringProperty name;
    private StringProperty description;
    private BooleanProperty active;
    private ObjectProperty<TestApp> testApp;
    private ObjectProperty<Device> device;
    private List<VideoFile> videos;
    private List<LogFile> logs;
    private List<AudioFile> audios;
    private List<ScreenshotFile> screenshots;
    private ObjectProperty<Test> test;
    private ObjectProperty<User> tester;
    private ObjectProperty<Date> timeStarted;
    private ObjectProperty<Date> timeFinished;


    public TestSession() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.active = new SimpleBooleanProperty();
        this.testApp = new SimpleObjectProperty<>();
        this.device = new SimpleObjectProperty<>();
        this.test = new SimpleObjectProperty<>();
        this.tester = new SimpleObjectProperty<>();
        this.timeStarted = new SimpleObjectProperty<>();
        this.timeFinished = new SimpleObjectProperty<>();
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public boolean getActive() {
        return active.get();
    }

    public BooleanProperty activeProperty() {
        return active;
    }

    public void setActive(boolean active) {
        this.active.set(active);
    }

    public TestApp getTestApp() {
        return testApp.get();
    }

    public ObjectProperty<TestApp> testAppProperty() {
        return testApp;
    }

    public void setTestApp(TestApp testApp) {
        this.testApp.set(testApp);
    }

    public Device getDevice() {
        return device.get();
    }

    public ObjectProperty<Device> deviceProperty() {
        return device;
    }

    public void setDevice(Device device) {
        this.device.set(device);
    }

    public List<VideoFile> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoFile> videos) {
        this.videos = videos;
    }

    public List<LogFile> getLogs() {
        return logs;
    }

    public void setLogs(List<LogFile> logs) {
        this.logs = logs;
    }

    public List<AudioFile> getAudios() {
        return audios;
    }

    public void setAudios(List<AudioFile> audios) {
        this.audios = audios;
    }

    public List<ScreenshotFile> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(List<ScreenshotFile> screenshots) {
        this.screenshots = screenshots;
    }

    public Test getTest() {
        return test.get();
    }

    public ObjectProperty<Test> testProperty() {
        return test;
    }

    public void setTest(Test test) {
        this.test.set(test);
    }

    public User getTester() {
        return tester.get();
    }

    public ObjectProperty<User> testerProperty() {
        return tester;
    }

    public void setTester(User tester) {
        this.tester.set(tester);
    }

    public Date getTimeStarted() {
        return timeStarted.get();
    }

    public ObjectProperty<Date> timeStartedProperty() {
        return timeStarted;
    }

    public void setTimeStarted(Date timeStarted) {
        this.timeStarted.set(timeStarted);
    }

    public Date getTimeFinished() {
        return timeFinished.get();
    }

    public ObjectProperty<Date> timeFinishedProperty() {
        return timeFinished;
    }

    public void setTimeFinished(Date timeFinished) {
        this.timeFinished.set(timeFinished);
    }
}
