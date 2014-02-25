package de.hsosnabrueck.iui.informatik.vma.hipsterbility.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Albert Hoffmann on 24.02.14.
 */
public class App {
    @SerializedName("idapps")
    private long id;
    private String name;

    public App() {
    }

    public App(long id, String name) {
        this.id = id;
        this.name = name;
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
}
