package de.hsosnabrueck.hipsterbility.persistence;

import de.hsosnabrueck.hipsterbility.entities.UserEntity;
import de.hsosnabrueck.hipsterbility.exceptions.DataAccessException;

/**
 * Created by Albert on 17.09.2014.
 */
public interface UserDao extends GenericDao<UserEntity, Integer> {

    public UserEntity findByUsername(String username) throws DataAccessException;

    public UserEntity findByEmail(String email) throws DataAccessException;
}
