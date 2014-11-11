package de.hsosnabrueck.iui.informatik.vma.hipsterbility.model;

import de.hsosnabrueck.hipsterbility.model.enums.FileType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Albert on 27.09.2014.
 */

@Entity
@Inheritance
@Table(name = "SessionFile")
public class FileEntity {

    public static final String TABLE_NAME = "SessionFile";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date timestamp;

    @ManyToOne
    @JoinColumn
    private TestSessionEntity session;

//    @Transient @JsonIgnore// files are stored on disk rather than in database
//    private File file;

    @Column(nullable = false, updatable = false, unique = true) //@JsonIgnore
    private String filePath;

    @Transient // data for HTTP Transfer
    private byte[] data;

    @Enumerated(EnumType.STRING)
    FileType type;

    @Transient
    private String fileExtension;

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

//    public File getFile() {
//        return file;
//    }

//    public void setFile(File file) {
//        this.file = file;
//    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
}
