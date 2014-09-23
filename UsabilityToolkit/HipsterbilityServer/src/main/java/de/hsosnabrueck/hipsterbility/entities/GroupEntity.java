package de.hsosnabrueck.hipsterbility.entities;

import javax.persistence.*;

/**
 * Created by Albert on 13.09.2014.
 */

@Entity(name = "UserGroup")
public class GroupEntity {

    public static final String TABLE_NAME = "UserGroup";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    private UserEntity user;

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
