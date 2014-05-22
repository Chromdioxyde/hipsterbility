package de.hsosnabrueck.iui.informatik.vma.hipsterbility.models;

import java.util.Locale;

/**
 * Created on 24.02.14.
 */
public class Device {

    private int id;                     // ID from Database
    private String androidId;           // Device HEX String which should be unique
    private String name;                // Build.DEVICE
    private String customName;          // Custom name which can be set by the user
    private String brand;               // Build.BRAND
    private String model;               // Build.MODEL
    private String manufacturer;        // Build.MANUFACTURER
    private String romVersion;          // Build.PRODUCT (Name of the ROM)
    private String buildNumber;         // Build.ID
    private String androidVersion;      // Build.VERSION.RELEASE
    private DeviceType type;            // Class of the Device
    private Locale locale;              // conf.locale
    private Double screenSizeInInch;
    private int screenHeightInPixels;
    private int screenWidthInPixels;
    private ScreenLayoutSize screenLayoutSize;

    public Device() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRomVersion() {
        return romVersion;
    }

    public void setRomVersion(String romVersion) {
        this.romVersion = romVersion;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Double getScreenSizeInInch() {
        return screenSizeInInch;
    }

    public void setScreenSizeInInch(Double screenSizeInInch) {
        this.screenSizeInInch = screenSizeInInch;
    }

    public int getScreenHeightInPixels() {
        return screenHeightInPixels;
    }

    public void setScreenHeightInPixels(int screenHeightInPixels) {
        this.screenHeightInPixels = screenHeightInPixels;
    }

    public int getScreenWidthInPixels() {
        return screenWidthInPixels;
    }

    public void setScreenWidthInPixels(int screenWidthInPixels) {
        this.screenWidthInPixels = screenWidthInPixels;
    }

    public ScreenLayoutSize getScreenLayoutSize() {
        return screenLayoutSize;
    }

    public void setScreenLayoutSize(ScreenLayoutSize screenLayoutSize) {
        this.screenLayoutSize = screenLayoutSize;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", androidId='" + androidId + '\'' +
                ", name='" + name + '\'' +
                ", customName='" + customName + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", romVersion='" + romVersion + '\'' +
                ", buildNumber='" + buildNumber + '\'' +
                ", androidVersion='" + androidVersion + '\'' +
                ", type=" + type +
                ", locale=" + locale +
                ", screenSizeInInch=" + screenSizeInInch +
                ", screenHeightInPixels=" + screenHeightInPixels +
                ", screenWidthInPixels=" + screenWidthInPixels +
                ", screenLayoutSize=" + screenLayoutSize +
                '}';
    }

    public enum DeviceType {
        PHONE, TABLET, PHABLET, OTHER
    }

    public enum ScreenLayoutSize {
        XLARGE, LARGE, NORMAL, SMALL, UNDEFINED
    }
}
