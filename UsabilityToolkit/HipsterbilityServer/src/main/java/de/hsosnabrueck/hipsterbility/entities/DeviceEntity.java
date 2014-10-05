package de.hsosnabrueck.hipsterbility.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import de.hsosnabrueck.hipsterbility.model.enums.DeviceClass;
import de.hsosnabrueck.hipsterbility.model.enums.DevicePlatform;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Albert on 08.09.2014.
 */
@Entity(name = "Device")
public class DeviceEntity {

    public static final String TABLE_NAME = "Device";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    private String osVersion;

    private String customName;

    @Enumerated(EnumType.STRING)
    private DeviceClass deviceClass;

    @Column(unique = true)
    private String uuid;

    @Enumerated(EnumType.STRING)
    private DevicePlatform platform;

    @ManyToOne @JsonBackReference
    private UserEntity user;

    @OneToMany(mappedBy = "device")
    private Collection<TestSessionEntity> sessions;

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

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public DeviceClass getDeviceClass() {
        return deviceClass;
    }

    public void setDeviceClass(DeviceClass deviceClass) {
        this.deviceClass = deviceClass;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public DevicePlatform getPlatform() {
        return platform;
    }

    public void setPlatform(DevicePlatform platform) {
        this.platform = platform;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Collection<TestSessionEntity> getSessions() {
        return sessions;
    }

    public void setSessions(Collection<TestSessionEntity> sessions) {
        this.sessions = sessions;
    }
}
