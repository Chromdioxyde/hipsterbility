package de.hsosnabrueck.hipsterbility.clientfx.model;

import javafx.beans.property.*;

import java.util.List;
import java.util.Locale;

/**
 * Created by Albert on 29.10.2014.
 */
public class User {

    public static String API_PATH = "users";

    private IntegerProperty id;
    private StringProperty username;
    private StringProperty firstname;
    private StringProperty lastname;
    private StringProperty email;
    private StringProperty password;
    private BooleanProperty active;
    private List<TestSession> sessions;
    private List<Device> devices;
    private ObjectProperty<Locale> locale;
    private StringProperty inviteCode;

    public User() {
        this.id = new SimpleIntegerProperty();
        this.username = new SimpleStringProperty();
        this.firstname = new SimpleStringProperty();
        this.lastname = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.active = new SimpleBooleanProperty();
        this.locale = new SimpleObjectProperty<>();
        this.inviteCode = new SimpleStringProperty();
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

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getFirstname() {
        return firstname.get();
    }

    public StringProperty firstnameProperty() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname.set(firstname);
    }

    public String getLastname() {
        return lastname.get();
    }

    public StringProperty lastnameProperty() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname.set(lastname);
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

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
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

    public List<TestSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<TestSession> sessions) {
        this.sessions = sessions;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
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

    public String getInviteCode() {
        return inviteCode.get();
    }

    public StringProperty inviteCodeProperty() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode.set(inviteCode);
    }
}
