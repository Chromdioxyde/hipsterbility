package de.hsosnabrueck.hipsterbility.rest.service;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;
import de.hsosnabrueck.hipsterbility.entities.TestSessionEntity;
import de.hsosnabrueck.hipsterbility.entities.UserEntity;

import java.util.Collection;

/**
 * Created by Albert on 17.09.2014.
 */
public interface UserService extends Service<UserEntity> {
    public UserEntity findByName(String username);
    public UserEntity findByEmail(String email);
    public boolean checkValidInvite(String code);
}
