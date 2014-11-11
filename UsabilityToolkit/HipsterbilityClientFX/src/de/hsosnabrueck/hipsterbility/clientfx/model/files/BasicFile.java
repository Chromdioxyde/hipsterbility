package de.hsosnabrueck.hipsterbility.clientfx.model.files;

import de.hsosnabrueck.hipsterbility.clientfx.model.TestSession;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.util.Date;

/**
 * Created by Albert on 03.11.2014.
 */
public abstract class BasicFile {

    private IntegerProperty id;
    private StringProperty filePath;
    private ObjectProperty<Date> timestamp;
    private ObjectProperty<TestSession> session;
    private ObjectProperty<File> file;

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getFilePath() {
        return filePath.get();
    }

    public StringProperty filePathProperty() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath.set(filePath);
    }

    public Date getTimestamp() {
        return timestamp.get();
    }

    public ObjectProperty<Date> timestampProperty() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp.set(timestamp);
    }

    public TestSession getSession() {
        return session.get();
    }

    public ObjectProperty<TestSession> sessionProperty() {
        return session;
    }

    public void setSession(TestSession session) {
        this.session.set(session);
    }

    public File getFile() {
        return file.get();
    }

    public ObjectProperty<File> fileProperty() {
        return file;
    }

    public void setFile(File file) {
        this.file.set(file);
    }
}
