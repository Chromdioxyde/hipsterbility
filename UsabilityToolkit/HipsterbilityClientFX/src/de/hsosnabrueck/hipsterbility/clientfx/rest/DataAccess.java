package de.hsosnabrueck.hipsterbility.clientfx.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hsosnabrueck.hipsterbility.clientfx.model.Group;
import de.hsosnabrueck.hipsterbility.clientfx.model.InviteCode;
import de.hsosnabrueck.hipsterbility.clientfx.model.Test;
import de.hsosnabrueck.hipsterbility.clientfx.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Albert on 02.10.2014.
 */
@Singleton
public class DataAccess {

    @Inject
    RestClientHelper restClientHelper;

    public boolean createUser(User user, Group group) throws DataAccessException {
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

    public ObservableList<User> getUsers() throws DataAccessException {
        ObjectMapper mapper = new ObjectMapper();
        List<User> userlist = restClientHelper.getTarget().path("users").request().get().readEntity(new GenericType<List<User>>() {});
        ObservableList<User> users = FXCollections.observableArrayList(userlist);
        return users;
    }

    public boolean createInviteCode(InviteCode invite) throws DataAccessException {
        Response r = restClientHelper.getTarget().path("invites").request().post(Entity.json(invite));
        return r.getStatus() == 200;
    }

    public boolean createTest(Test test) throws DataAccessException {
        Response r = restClientHelper.getTarget().path("tests").request().post(Entity.json(test));
        return 201 == r.getStatus();
    }
}
