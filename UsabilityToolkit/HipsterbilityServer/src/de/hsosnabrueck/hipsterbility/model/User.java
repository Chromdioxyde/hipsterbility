package de.hsosnabrueck.hipsterbility.model;

/**
 * Created by Albert on 08.09.2014.
 */
public class User {

    private int id;
    private String name;
    private String password; // TODO encrypt or hash
    private boolean active;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
