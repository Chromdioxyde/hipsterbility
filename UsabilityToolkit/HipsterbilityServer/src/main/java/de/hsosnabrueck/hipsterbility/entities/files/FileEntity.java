package de.hsosnabrueck.hipsterbility.entities.files;

import de.hsosnabrueck.hipsterbility.entities.TestSessionEntity;

import javax.persistence.*;
import java.io.File;
import java.security.Timestamp;
import java.util.Date;

/**
 * Created by Albert on 27.09.2014.
 */

@Entity
@Inheritance
@Table(name = "SessionFile")
@DiscriminatorColumn(name = "FileType")
public abstract class FileEntity {

    public static final String TABLE_NAME = "SessionFile";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, updatable = false, unique = true)
    private String filePath;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date timestamp;
    @ManyToOne
    @JoinColumn
    private TestSessionEntity session;
    @Transient // files are stored on disk rather than in database
    private File file;

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

    public TestSessionEntity getSession() {
        return session;
    }

    public void setSession(TestSessionEntity session) {
        this.session = session;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
