package de.hsosnabrueck.hipsterbility.rest.service.impl;

import de.hsosnabrueck.hipsterbility.entities.GroupEntity;
import de.hsosnabrueck.hipsterbility.entities.InviteCodeEntity;
import de.hsosnabrueck.hipsterbility.entities.UserEntity;
import de.hsosnabrueck.hipsterbility.exceptions.DataAccessException;
import de.hsosnabrueck.hipsterbility.persistence.DeviceDao;
import de.hsosnabrueck.hipsterbility.persistence.GroupDao;
import de.hsosnabrueck.hipsterbility.persistence.TestSessionDao;
import de.hsosnabrueck.hipsterbility.persistence.UserDao;
import de.hsosnabrueck.hipsterbility.rest.service.InviteCodeService;
import de.hsosnabrueck.hipsterbility.rest.service.UserService;

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
    @Inject
    InviteCodeService inviteCodeService;
    @Inject
    GroupDao groupDao;


    @Override
    public Collection<UserEntity> list() throws DataAccessException {
        return userDao.listAll();
    }

    @Override
    public Collection<UserEntity> list(int startIndex, int count) throws DataAccessException {
        return userDao.list(startIndex, count);
    }

    @Override
    public UserEntity read(Integer id) throws DataAccessException {
        return userDao.read(id);
    }

    @Override
    public void delete(Integer id) throws DataAccessException {
        userDao.delete(id);
    }

    @Override
    public UserEntity create(UserEntity object) throws DataAccessException {
        return userDao.create(object);
    }

    @Override
    public UserEntity update(Integer id, UserEntity device) throws DataAccessException {
        return userDao.update(id, device);
    }

    @Override
    public UserEntity findByName(String username) throws DataAccessException {
        return userDao.findByUsername(username);
    }

    @Override
    public UserEntity findByEmail(String email) throws DataAccessException {
        return userDao.findByEmail(email);
    }

    @Override
    public boolean checkValidInvite(String code){
        if(null == code) return false;
        InviteCodeEntity i = null;
        try {
            i = inviteCodeService.getInvite(code);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null != i && i.isValid();
    }

    @Override
    public UserEntity create(UserEntity user, GroupEntity group) throws DataAccessException {
        user = userDao.create(user);
        if(null == user) return null; // user already exists or violates unique constraints
        GroupEntity existingGroup = groupDao.read(group.getName());
        if(null != existingGroup){
            group = existingGroup;
        } else {
            groupDao.create(group);
        }
        group.getUsers().add(user);
        user.getGroups().add(group);
        return user;
    }

    @Override
    public boolean addGroup(UserEntity user, GroupEntity group) throws DataAccessException {
        try {
            user = userDao.create(user);
            if(null == user) return false;
            group.getUsers().add(user);
            group = groupDao.createIfNotExists(group, group.getName());
            user.getGroups().add(group);
            group.getUsers().add(user);
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException(e);
        }
    }

    @Override
    public boolean addGroup(int userId, GroupEntity group) throws DataAccessException {
        return addGroup(userDao.read(userId), group);
    }
}
