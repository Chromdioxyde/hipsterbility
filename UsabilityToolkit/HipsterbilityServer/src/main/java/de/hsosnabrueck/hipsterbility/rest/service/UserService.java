package de.hsosnabrueck.hipsterbility.rest.service;

import de.hsosnabrueck.hipsterbility.entities.GroupEntity;
import de.hsosnabrueck.hipsterbility.entities.UserEntity;
import de.hsosnabrueck.hipsterbility.exceptions.DataAccessException;

/**
 * Created by Albert on 17.09.2014.
 */
public interface UserService extends Service<UserEntity, Integer> {
    public UserEntity findByName(String username) throws DataAccessException;
    public UserEntity findByEmail(String email) throws DataAccessException;
    public boolean checkValidInvite(String code);
    public UserEntity create(UserEntity user, GroupEntity group) throws DataAccessException;
    public boolean addGroup(UserEntity user, GroupEntity group) throws DataAccessException;
    public boolean addGroup(int userId, GroupEntity group) throws DataAccessException;
}
