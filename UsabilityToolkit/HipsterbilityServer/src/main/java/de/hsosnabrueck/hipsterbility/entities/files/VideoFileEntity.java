package de.hsosnabrueck.hipsterbility.entities.files;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;

/**
 * Created by Albert on 08.09.2014.
 */
@Entity
@DiscriminatorValue("VIDEO")
public class VideoFileEntity extends FileEntity {

    @JsonFormat(shape= JsonFormat.Shape.OBJECT)
    public static enum Type {
        CAMERA_FRONT, SCREEN, RESULT
    }

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}