package de.hsosnabrueck.hipsterbility.clientfx.model;


import javafx.beans.property.*;

/**
 * Created by Albert on 03.11.2014.
 */
public class InviteCode {

    public static final String API_PATH = "invites";

    private IntegerProperty id;
    private StringProperty code;
    private StringProperty email;
    private ObjectProperty<User> creator;
    private BooleanProperty valid;

    public InviteCode() {
        this.id = new SimpleIntegerProperty();
        this.code = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.creator = new SimpleObjectProperty<>();
        this.valid = new SimpleBooleanProperty();
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getCode() {
        return code.get();
    }

    public StringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
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

    public boolean getValid() {
        return valid.get();
    }

    public BooleanProperty validProperty() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid.set(valid);
    }
}
