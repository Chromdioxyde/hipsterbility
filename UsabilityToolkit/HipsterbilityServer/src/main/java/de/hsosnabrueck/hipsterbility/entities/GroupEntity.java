package de.hsosnabrueck.hipsterbility.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Created by Albert on 13.09.2014.
 */

@Entity(name = "RealmGroup")
public class GroupEntity {

    public static final String TABLE_NAME = "RealmGroup";
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    @Id
    private String name;
    @ManyToMany(mappedBy = "groups") @JsonBackReference("user-group")
    private List<UserEntity> users;

    public GroupEntity() {
    }

    public GroupEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }
}
