package de.hsosnabrueck.hipsterbility.entities;

import de.hsosnabrueck.hipsterbility.model.enums.VideoType;

import javax.persistence.*;

/**
 * Created by Albert on 08.09.2014.
 */
@Entity(name = "Video")
public class VideoEntity {

    @Id
    @GeneratedValue
    private int id;
    private String filePath;
    @Enumerated(EnumType.STRING)
    private VideoType type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public VideoType getType() {
        return type;
    }

    public void setType(VideoType type) {
        this.type = type;
    }
}
