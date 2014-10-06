package de.hsosnabrueck.hipsterbility.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;
import java.util.Locale;

/**
 * Created by Albert on 08.09.2014.
 */
@Entity(name = "User")

@NamedQueries({
        @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username=:username"),
        @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email=:email")
        })
public class UserEntity {
    @JsonIgnoreProperties({"password"})

    public static final String TABLE_NAME = "User";

    public UserEntity() {
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(min = 4, max = 32)
    @Column(unique = true, nullable = false, updatable = false)
    private String username;

    private String firstname;

    private String lastname;

    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false) @XmlTransient @JsonIgnore
    private String password;

    private boolean active;

    @OneToMany(mappedBy = "tester")
    @JsonProperty("sessions")
    private List<TestSessionEntity> sessions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonProperty("devices")
    private List<DeviceEntity> devices;
    private Locale locale;

    private String inviteCode;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<GroupEntity> groups;


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

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

//    public List<GroupEntity> getGroups() {
//        return groups;
//    }
//
//    public void setGroups(List<GroupEntity> group) {
//        this.groups = group;
//    }
}
