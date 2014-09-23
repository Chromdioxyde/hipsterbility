package de.hsosnabrueck.hipsterbility.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Albert on 08.09.2014.
 */
@Entity(name = "Result")
public class TestResultEntity {

    public static final String TABLE_NAME = "Result";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String filePath;
    @Column(insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
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
