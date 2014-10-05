package de.hsosnabrueck.hipsterbility.clientfx.rest;

import de.hsosnabrueck.hipsterbility.entities.UserEntity;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

/**
 * Created by Albert on 02.10.2014.
 */
@Singleton
public class DataAccess {

    @Inject
    RestClientHelper restClientHelper;

    public void createUser(UserEntity user) throws DataAccessException {
        Response r = restClientHelper.getTarget().path("users").request().post(Entity.json(user));
        System.out.println(r);
    }
}
