package de.hsosnabrueck.hipsterbility.clientfx.model;

import de.hsosnabrueck.hipsterbility.model.enums.DeviceClass;
import javafx.beans.property.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Albert on 30.10.2014.
 */
public class Test {

    public static final String API_PATH = "tests";

    private IntegerProperty id;
    private StringProperty name;
    private StringProperty description;
    private ObjectProperty<TestApp> testApp;
    private List<Task> tasks;
    private ObjectProperty<User> creator;
    private ObjectProperty<User> modifier;
    private ObjectProperty<Date> timestampCreated;
    private ObjectProperty<Date> timestampModified;
    private BooleanProperty template;
    private List<DeviceClass> deviceClasses;

    public Test() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.testApp = new SimpleObjectProperty<>();
        this.creator = new SimpleObjectProperty<>();
        this.modifier = new SimpleObjectProperty<>();
        this.timestampCreated = new SimpleObjectProperty<>();
        this.timestampModified = new SimpleObjectProperty<>();
        this.template = new SimpleBooleanProperty();
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

    public TestApp getTestApp() {
        return testApp.get();
    }

    public ObjectProperty<TestApp> testAppProperty() {
        return testApp;
    }

    public void setTestApp(TestApp testApp) {
        this.testApp.set(testApp);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public User getCreator() {
        return creator.get();
    }

    public ObjectProperty<User> creatorProperty() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator.set(creator);
    }

    public User getModifier() {
        return modifier.get();
    }

    public ObjectProperty<User> modifierProperty() {
        return modifier;
    }

    public void setModifier(User modifier) {
        this.modifier.set(modifier);
    }

    public Date getTimestampCreated() {
        return timestampCreated.get();
    }

    public ObjectProperty<Date> timestampCreatedProperty() {
        return timestampCreated;
    }

    public void setTimestampCreated(Date timestampCreated) {
        this.timestampCreated.set(timestampCreated);
    }

    public Date getTimestampModified() {
        return timestampModified.get();
    }

    public ObjectProperty<Date> timestampModifiedProperty() {
        return timestampModified;
    }

    public void setTimestampModified(Date timestampModified) {
        this.timestampModified.set(timestampModified);
    }

    public boolean getTemplate() {
        return template.get();
    }

    public BooleanProperty templateProperty() {
        return template;
    }

    public void setTemplate(boolean template) {
        this.template.set(template);
    }

    public List<DeviceClass> getDeviceClasses() {
        return deviceClasses;
    }

    public void setDeviceClasses(List<DeviceClass> deviceClasses) {
        this.deviceClasses = deviceClasses;
    }
}
