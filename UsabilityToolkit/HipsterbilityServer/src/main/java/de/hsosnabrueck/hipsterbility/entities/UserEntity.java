package de.hsosnabrueck.hipsterbility.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * Created by Albert on 08.09.2014.
 */
@Entity(name = "User")
public class UserEntity {

    public static final String TABLE_NAME = "User";

//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    private int id;
    @Id
//    @Column(unique = true, nullable = false, updatable = false)
    private String userName;
    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false) @XmlTransient @JsonIgnore
    private String password; // TODO encrypt or hash
    private boolean active;
//    @ManyToOne
//    private GroupEntity groups;
    @OneToMany
    private List<TestSessionEntity> sessions;
    @OneToMany
    private List<DeviceEntity> devices;

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<DeviceEntity> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceEntity> devices) {
        this.devices = devices;
    }

//    public GroupEntity getGroups() {
//        return groups;
//    }
//
//    public void setGroups(GroupEntity groups) {
//        this.groups = groups;
//    }
}
