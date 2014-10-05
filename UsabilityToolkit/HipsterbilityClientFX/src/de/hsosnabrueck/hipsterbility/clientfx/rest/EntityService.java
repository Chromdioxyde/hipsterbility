package de.hsosnabrueck.hipsterbility.clientfx.rest;

import javafx.beans.property.ObjectProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

/**
 * Created by Albert on 03.10.2014.
 */
public class EntityService<T> extends Service<T> {

    private ObjectProperty<T> object;

    @Inject
    private RestClientHelper client;

    @Override
    protected Task<T> createTask() {
        return null;
    }
}
