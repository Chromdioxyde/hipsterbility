package de.hsosnabrueck.iui.informatik.vma.hipsterbility.models;

/**
 * Created by Albert Hoffmann on 24.02.14.
 */
public class User {
    private long id;
    private String name;
    //TODO: improve password handling
    private String password;

    public User() {
    }

    public User(long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
