package de.hsosnabrueck.iui.informatik.vma.hipsterbility.model;

import de.hsosnabrueck.hipsterbility.model.enums.DevicePlatform;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Albert on 08.09.2014.
 */
@Entity(name = "App")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"NAME","VERSION"}))
public class TestAppEntity {

    public static final String TABLE_NAME = "App";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String version;

    @Enumerated(EnumType.STRING)
    private DevicePlatform platform;

    @OneToMany(mappedBy = "testApp")
    private Collection<TestSessionEntity> sessions;


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

    public DevicePlatform getPlatform() {
        return platform;
    }

    public void setPlatform(DevicePlatform platform) {
        this.platform = platform;
    }

    public Collection<TestSessionEntity> getSessions() {
        return sessions;
    }

    public void setSessions(Collection<TestSessionEntity> sessions) {
        this.sessions = sessions;
    }
}
