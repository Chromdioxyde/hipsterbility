package de.hsosnabrueck.hipsterbility.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by Albert on 08.09.2014.
 */
@Entity(name = "TestLog")
public class LogEntity {

    public static final String TABLE_NAME = "TestLog";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public TestSessionEntity getSession() {
        return session;
    }

    public void setSession(TestSessionEntity session) {
        this.session = session;
    }
}
