package de.hsosnabrueck.hipsterbility.rest.data;

/**
 * Created by Albert on 01.10.2014.
 * Wrapper for integer id values of created resources, since we don't construct URIs (yet)
 */
public class CreatedId {
    private int id;

    public CreatedId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
