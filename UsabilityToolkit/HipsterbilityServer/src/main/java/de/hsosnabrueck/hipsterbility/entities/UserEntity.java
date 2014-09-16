package de.hsosnabrueck.hipsterbility.entities;


import javax.persistence.*;
import java.util.List;

/**
 * Created by Albert on 08.09.2014.
 */
@Entity(name = "User")
public class UserEntity {

    @Id
    @GeneratedValue
    private int id;
    @Column(unique = true, nullable = false)
    private String userName;
    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String passwordHash; // TODO encrypt or hash
    private boolean active;
    @ManyToMany
    private List<GroupEntity> groups;
    @OneToMany
    private List<TestSessionEntity> sessions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String password) {
        this.passwordHash = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<GroupEntity> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupEntity> groups) {
        this.groups = groups;
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

}
