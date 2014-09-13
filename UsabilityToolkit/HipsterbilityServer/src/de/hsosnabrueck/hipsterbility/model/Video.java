package de.hsosnabrueck.hipsterbility.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Albert on 08.09.2014.
 */
public class Video {

    @Id
    @GeneratedValue
    private int id;
    private String filePath;

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
}
