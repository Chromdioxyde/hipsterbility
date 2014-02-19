package de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.sessions;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Albert Hoffmann on 13.02.14.
 * Uses interface "Parceable" to be packaged into intents for communication.
 * Source: http://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
 *
 * Interface Parceable is used to send Objects of this class in an intent
 */
public class Session implements Parcelable {

    // TODO: Implementation

    //================================================================================
    // Properties
    //================================================================================

    private int id;
    private String name;
    private String description;
    private String device;
    private String app;
    private boolean active;

    //================================================================================
    // Constructors
    //================================================================================

    public Session(int id) {
        this.id = id;
    }

    private Session(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        device = in.readString();
        app = in.readString();
        // TODO: improve dirty boolean hack
        active = in.readInt() > 0;
    }

    public Session(int id, String name, String description, String device, String app, boolean active){
        this(id);
        this.name = name;
        this.description = description;
        this.device = device;
        this.app = app;
        this.active = active;
    }

    //================================================================================
    // Public Methods
    //================================================================================


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        out.writeString(description);
        out.writeString(device);
        out.writeString(app);
        // TODO: improve dirty boolean hack
        out.writeInt(active ? 1 : 0);
    }

    public static final Parcelable.Creator<Session> CREATOR = new Parcelable.Creator<Session>() {
        public Session createFromParcel(Parcel in) {
            return new Session(in);
        }

        public Session[] newArray(int size) {
            return new Session[size];
        }
    };


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    //================================================================================
    // Private Methods
    //================================================================================

}
