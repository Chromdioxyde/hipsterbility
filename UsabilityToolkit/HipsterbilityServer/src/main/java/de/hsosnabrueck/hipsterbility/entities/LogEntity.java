package de.hsosnabrueck.hipsterbility.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Albert on 08.09.2014.
 */
@Entity(name = "TestLog")
public class LogEntity {

    public static final String TABLE_NAME = "TestLog";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
