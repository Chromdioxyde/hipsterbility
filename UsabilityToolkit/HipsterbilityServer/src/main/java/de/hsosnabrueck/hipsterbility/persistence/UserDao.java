package de.hsosnabrueck.hipsterbility.persistence;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;
import de.hsosnabrueck.hipsterbility.entities.UserEntity;

/**
 * Created by Albert on 17.09.2014.
 */
public interface UserDao extends Dao<UserEntity> {

    public UserEntity findByUsername(String username);
    public DeviceEntity retrieveDevice(int userId, int deviceId);
}
