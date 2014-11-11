package de.hsosnabrueck.hipsterbility.clientfx.model.files;

import com.fasterxml.jackson.annotation.JsonFormat;
import javafx.beans.property.ObjectProperty;

/**
 * Created by Albert on 03.11.2014.
 */
public class VideoFile extends BasicFile {


    @JsonFormat(shape= JsonFormat.Shape.OBJECT)
    public static enum Type {
        CAMERA_FRONT, SCREEN, RESULT
    }

    private ObjectProperty<Type> type;

    public Type getType() {
        return type.get();
    }

    public ObjectProperty<Type> typeProperty() {
        return type;
    }

    public void setType(Type type) {
        this.type.set(type);
    }
}
