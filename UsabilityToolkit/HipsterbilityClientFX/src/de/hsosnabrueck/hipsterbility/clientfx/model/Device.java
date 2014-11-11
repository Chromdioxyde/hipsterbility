package de.hsosnabrueck.hipsterbility.clientfx.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import de.hsosnabrueck.hipsterbility.model.enums.DeviceClass;
import de.hsosnabrueck.hipsterbility.model.enums.DevicePlatform;
import javafx.beans.property.*;
import javafx.collections.ObservableList;

/**
 * Created by Albert on 29.10.2014.
 */
public class Device {

    private IntegerProperty id;
    private StringProperty name;
    private StringProperty osVersion;
    private StringProperty customName;
    private ObjectProperty<DeviceClass> deviceClass;
    private StringProperty uuid;
    private ObjectProperty<DevicePlatform> platform;
    @JsonBackReference("device-user")
    private ObjectProperty<User> user;
    private SimpleListProperty<TestSession> sessions;

    public Device() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.osVersion = new SimpleStringProperty();
        this.customName = new SimpleStringProperty();
        this.deviceClass = new SimpleObjectProperty<>();
        this.uuid = new SimpleStringProperty();
        this.platform = new SimpleObjectProperty<>();
        this.user = new SimpleObjectProperty<User>();
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

    public String getOsVersion() {
        return osVersion.get();
    }

    public StringProperty osVersionProperty() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion.set(osVersion);
    }

    public String getCustomName() {
        return customName.get();
    }

    public StringProperty customNameProperty() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName.set(customName);
    }

    public DeviceClass getDeviceClass() {
        return deviceClass.get();
    }

    public ObjectProperty<DeviceClass> deviceClassProperty() {
        return deviceClass;
    }

    public void setDeviceClass(DeviceClass deviceClass) {
        this.deviceClass.set(deviceClass);
    }

    public String getUuid() {
        return uuid.get();
    }

    public StringProperty uuidProperty() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid.set(uuid);
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

    public User getUser() {
        return user.get();
    }

    public ObjectProperty<User> userProperty() {
        return user;
    }

    public void setUser(User user) {
        this.user.set(user);
    }

    public ObservableList<TestSession> getSessions() {
        return sessions.get();
    }

    public SimpleListProperty<TestSession> sessionsProperty() {
        return sessions;
    }

    public void setSessions(SimpleListProperty<TestSession> sessions) {
        this.sessions.set(sessions);
    }
}
