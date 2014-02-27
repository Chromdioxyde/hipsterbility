package de.hsosnabrueck.iui.informatik.vma.hipsterbility.models;

/**
 * Created by Albert Hoffmann on 24.02.14.
 */
public class Device {
    private long id;
    private String name;
    private String osVersion;
    private String type;

    public Device() {
    }

    public Device(long id, String name, String osVersion, String type) {
        this.id = id;
        this.name = name;
        this.osVersion = osVersion;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
