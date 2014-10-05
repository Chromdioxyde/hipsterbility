package de.hsosnabrueck.hipsterbility.entities.files;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;

/**
 * Created by Albert on 25.09.2014.
 */
@Entity
@DiscriminatorValue("AUDIO")
public class AudioFileEntity extends FileEntity{

    @JsonFormat(shape= JsonFormat.Shape.OBJECT)
    public static enum Type{
        MICROPHONE
    }

    @Column(updatable = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
