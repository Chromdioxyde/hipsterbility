package de.hsosnabrueck.hipsterbility.entities;

import javax.persistence.*;

/**
 * Created by Albert on 13.09.2014.
 */

@Entity(name = "Group")
public class GroupEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
