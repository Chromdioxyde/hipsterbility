package de.hsosnabrueck.hipsterbility.clientfx.model;

import de.hsosnabrueck.hipsterbility.model.enums.DevicePlatform;
import javafx.beans.property.*;

import java.util.List;

/**
 * Created by Albert on 30.10.2014.
 */
public class TestApp {

    public static final String API_PATH = "apps";

    private IntegerProperty id;
    private StringProperty name;
    private StringProperty version;
    private ObjectProperty<DevicePlatform> platform;
    private List<TestSession> sessions;

    public TestApp() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.version = new SimpleStringProperty();
        this.platform = new SimpleObjectProperty<>();
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

    public String getVersion() {
        return version.get();
    }

    public StringProperty versionProperty() {
        return version;
    }

    public void setVersion(String version) {
        this.version.set(version);
    }

    public DevicePlatform getPlatform() {
        return platform.get();
    }

    public ObjectProperty<DevicePlatform> platformProperty() {
        return platform;
    }

    public void setPlatform(DevicePlatform platform) {
        this.platform.set(platform);
    }

    public List<TestSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<TestSession> sessions) {
        this.sessions = sessions;
    }
}
