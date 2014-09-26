package de.hsosnabrueck.hipsterbility.rest.service;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;
import de.hsosnabrueck.hipsterbility.entities.TestSessionEntity;
import de.hsosnabrueck.hipsterbility.entities.UserEntity;

import java.util.Collection;

/**
 * Created by Albert on 17.09.2014.
 */
public interface UserService extends Service<UserEntity> {
    public Collection<DeviceEntity> readDevices(int userId);
    public Collection<TestSessionEntity> readSessions(int userId);
    public DeviceEntity readDevice(int userId, int deviceId);
    public UserEntity findByName(String username);
}
