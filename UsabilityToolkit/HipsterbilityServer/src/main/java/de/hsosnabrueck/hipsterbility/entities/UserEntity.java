package de.hsosnabrueck.hipsterbility.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;
import java.util.Locale;

/**
 * Created by Albert on 08.09.2014.
 */
@Entity(name = "User")
@JsonIgnoreProperties({"password"})
@NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username=:username")
public class UserEntity {

    public static final String TABLE_NAME = "User";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false, updatable = false)
    private String username;
    private String firstname;
    private String lastname;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false) @XmlTransient @JsonIgnore
    private String password;
    private boolean active;
    @OneToMany
    private List<TestSessionEntity> sessions;
    @OneToMany
    private List<DeviceEntity> devices;
    private Locale locale;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<TestSessionEntity> getSessions() {
        return sessions;
    }

    public void setSessions(List<TestSessionEntity> sessions) {
        this.sessions = sessions;
    }

    public List<DeviceEntity> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceEntity> devices) {
        this.devices = devices;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
