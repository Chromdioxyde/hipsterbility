package de.hsosnabrueck.hipsterbility.clientfx.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;
import java.util.Locale;

/**
 * Created by Albert on 03.11.2014.
 */
public class Task {

    public static final String API_PATH = "tasks";

    private IntegerProperty id;
    private IntegerProperty orderNr;
    private StringProperty name;
    private StringProperty description;
    private BooleanProperty active;
    private BooleanProperty done;
    private BooleanProperty success;
    private StringProperty comment;
    private ObjectProperty<Test> test;
    private ObjectProperty<User> creator;
    private ObjectProperty<User> modifier;
    private ObjectProperty<Date> timestampCreated;
    private ObjectProperty<Date> timestampModified;
    private ObjectProperty<Locale> locale;

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getOrderNr() {
        return orderNr.get();
    }

    public IntegerProperty orderNrProperty() {
        return orderNr;
    }

    public void setOrderNr(int orderNr) {
        this.orderNr.set(orderNr);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public boolean getActive() {
        return active.get();
    }

    public BooleanProperty activeProperty() {
        return active;
    }

    public void setActive(boolean active) {
        this.active.set(active);
    }

    public boolean getDone() {
        return done.get();
    }

    public BooleanProperty doneProperty() {
        return done;
    }

    public void setDone(boolean done) {
        this.done.set(done);
    }

    public boolean getSuccess() {
        return success.get();
    }

    public BooleanProperty successProperty() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success.set(success);
    }

    public String getComment() {
        return comment.get();
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public Test getTest() {
        return test.get();
    }

    public ObjectProperty<Test> testProperty() {
        return test;
    }

    public void setTest(Test test) {
        this.test.set(test);
    }

    public User getCreator() {
        return creator.get();
    }

    public ObjectProperty<User> creatorProperty() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator.set(creator);
    }

    public User getModifier() {
        return modifier.get();
    }

    public ObjectProperty<User> modifierProperty() {
        return modifier;
    }

    public void setModifier(User modifier) {
        this.modifier.set(modifier);
    }

    public Date getTimestampCreated() {
        return timestampCreated.get();
    }

    public ObjectProperty<Date> timestampCreatedProperty() {
        return timestampCreated;
    }

    public void setTimestampCreated(Date timestampCreated) {
        this.timestampCreated.set(timestampCreated);
    }

    public Date getTimestampModified() {
        return timestampModified.get();
    }

    public ObjectProperty<Date> timestampModifiedProperty() {
        return timestampModified;
    }

    public void setTimestampModified(Date timestampModified) {
        this.timestampModified.set(timestampModified);
    }

    public Locale getLocale() {
        return locale.get();
    }

    public ObjectProperty<Locale> localeProperty() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale.set(locale);
    }
}
