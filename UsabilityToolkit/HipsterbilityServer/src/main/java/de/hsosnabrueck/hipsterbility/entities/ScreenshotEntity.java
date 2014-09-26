package de.hsosnabrueck.hipsterbility.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by Albert on 25.09.2014.
 */

@Entity(name = "Screenshot")
public class ScreenshotEntity {

    public static final String TABLE_NAME = "Screenshot";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String filePath;
    @ManyToOne @JsonBackReference
    private TestSessionEntity session;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String path) {
        this.filePath = path;
    }

    public TestSessionEntity getSession() {
        return session;
    }

    public void setSession(TestSessionEntity session) {
        this.session = session;
    }
}
