package de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.sessions;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Albert Hoffmann on 13.02.14.
 * Uses interface "Parceable" to be packaged into intents for communication.
 * Source: http://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
 */
public class Session implements Parcelable {

    // TODO: Implementation

    //================================================================================
    // Properties
    //================================================================================

    private String id;

    //================================================================================
    // Constructors
    //================================================================================

    public Session(String id) {
        this.id = id;
    }

    private Session(Parcel in) {
        id = in.readString();
    }

    //================================================================================
    // Public Methods
    //================================================================================

    public String getId() {
        return id;
    }


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
    }

    public static final Parcelable.Creator<Session> CREATOR = new Parcelable.Creator<Session>() {
        public Session createFromParcel(Parcel in) {
            return new Session(in);
        }

        public Session[] newArray(int size) {
            return new Session[size];
        }
    };
    //================================================================================
    // Private Methods
    //================================================================================

}
