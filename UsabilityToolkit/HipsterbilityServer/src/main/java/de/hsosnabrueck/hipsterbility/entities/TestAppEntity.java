package de.hsosnabrueck.hipsterbility.entities;

import javax.persistence.*;

/**
 * Created by Albert on 08.09.2014.
 */
@Entity(name = "App")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"NAME","VERSION"}))
public class TestAppEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    private String version;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
