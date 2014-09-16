package de.hsosnabrueck.hipsterbility.model;

import de.hsosnabrueck.hipsterbility.model.enums.DeviceClass;

/**
 * Created by Albert on 15.09.2014.
 */
public class Device {

    private int id;
    private String name;
    private String osVersion;
    private String customName;
    private DeviceClass deviceClass;

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
}
