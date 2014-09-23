package de.hsosnabrueck.hipsterbility.rest.service;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;
import de.hsosnabrueck.hipsterbility.entities.TestSessionEntity;
import de.hsosnabrueck.hipsterbility.entities.UserEntity;
import de.hsosnabrueck.hipsterbility.persistence.DeviceDao;
import de.hsosnabrueck.hipsterbility.persistence.TestSessionDao;
import de.hsosnabrueck.hipsterbility.persistence.UserDao;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

/**
 * Created by Albert on 17.09.2014.
 */
@Singleton
public class UserServiceImpl implements UserService {

    @Inject
    UserDao userDao;
    @Inject
    TestSessionDao sessionDao;
    @Inject
    DeviceDao deviceDao;


    @Override
    public Collection<UserEntity> list() {
        return userDao.listAll();
    }

    @Override
    public Collection<UserEntity> list(int startIndex, int count) {
        return userDao.list(startIndex, count);
    }

    @Override
    public UserEntity read(int id) {
        return userDao.retrieve(id);
    }

    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    @Override
    public UserEntity create(UserEntity object) {
        return userDao.save(object);
    }

    @Override
    public void update(int id, UserEntity object) {
        userDao.update(id, object);
    }


    @Override
    public Collection<DeviceEntity> readDevices(int userId) {
        return userDao.retrieve(userId).getDevices();
    }

    @Override
    public Collection<TestSessionEntity> readSessions(int userId) {
        return userDao.retrieve(userId).getSessions();
    }

    @Override
    public DeviceEntity readDevice(int userId, int deviceId) {
        return userDao.retrieveDevice(userId,deviceId);
    }
}
