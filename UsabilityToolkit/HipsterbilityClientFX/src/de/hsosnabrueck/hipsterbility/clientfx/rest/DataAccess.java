package de.hsosnabrueck.hipsterbility.clientfx.rest;

import de.hsosnabrueck.hipsterbility.entities.GroupEntity;
import de.hsosnabrueck.hipsterbility.entities.InviteCodeEntity;
import de.hsosnabrueck.hipsterbility.entities.TestEntity;
import de.hsosnabrueck.hipsterbility.entities.UserEntity;
import de.hsosnabrueck.hipsterbility.rest.data.CreatedId;
import de.hsosnabrueck.hipsterbility.util.Utils;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;

/**
 * Created by Albert on 02.10.2014.
 */
@Singleton
public class DataAccess {

    @Inject
    RestClientHelper restClientHelper;

    public boolean createUser(UserEntity user, GroupEntity group) throws DataAccessException {
        System.out.println(group.getName());
        System.out.println(Entity.json(user));
        Response r = restClientHelper.getTarget().path("users").request().put(Entity.json(user));
        System.out.println(r.getStatus() + " - " + r.getStatusInfo());
        if(r.getStatus() == 201){
            String id = r.readEntity(String.class);
            System.out.println(id);
            r.close();
            r = restClientHelper.getTarget().path("users").path(id).path("group").request().put(Entity.text(group.getName().toUpperCase()));
        }
        System.out.println(r.getStatus() + " - " + r.getStatusInfo());
        return r.getStatus() == 200;
    }

    public List<UserEntity> getUsers(){
        List<UserEntity> users = restClientHelper.getTarget().path("users").request().get().readEntity(new GenericType<List<UserEntity>>() {});
        return users;
    }

    public boolean createInviteCode(InviteCodeEntity invite){
        Response r = restClientHelper.getTarget().path("invites").request().post(Entity.json(invite));
        return r.getStatus() == 200;
    }

    public boolean createTest(TestEntity test){
        Response r = restClientHelper.getTarget().path("tests").request().post(Entity.json(test));
        return 201 == r.getStatus();
    }
}
