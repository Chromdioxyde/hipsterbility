package de.hsosnabrueck.hipsterbility.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Albert on 08.09.2014.
 */

@Entity(name = "Session")
public class TestSessionEntity {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private boolean active;
    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private TestAppEntity testApp;
    @ManyToOne
    private DeviceEntity device;

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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
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
}
