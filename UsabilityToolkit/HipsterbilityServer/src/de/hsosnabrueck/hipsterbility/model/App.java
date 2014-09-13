package de.hsosnabrueck.hipsterbility.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Albert on 08.09.2014.
 */
public class App {

    @Id
    @GeneratedValue
    private int id;
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
