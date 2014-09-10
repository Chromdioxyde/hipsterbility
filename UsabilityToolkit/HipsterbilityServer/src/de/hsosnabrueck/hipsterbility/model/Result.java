package de.hsosnabrueck.hipsterbility.model;

import java.util.Date;

/**
 * Created by Albert on 08.09.2014.
 */
public class Result {

    private int id;
    private String filePath;
    private Date timestamp;

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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
