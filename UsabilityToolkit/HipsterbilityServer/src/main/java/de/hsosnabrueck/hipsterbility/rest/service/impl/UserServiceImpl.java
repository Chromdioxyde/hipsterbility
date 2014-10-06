package de.hsosnabrueck.hipsterbility.rest.service.impl;

import de.hsosnabrueck.hipsterbility.entities.*;
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
    public boolean delete(int id) {
        return userDao.delete(id);
    }

    @Override
    public UserEntity create(UserEntity object) {
        return userDao.save(object);
    }

    @Override
    public boolean update(int id, UserEntity object) {
        return userDao.update(id, object);
    }

    @Override
    public UserEntity findByName(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public boolean checkValidInvite(String code) {
        if(null == code) return false;
        InviteCodeEntity i = inviteCodeService.getInvite(code);
        return null != i && i.isValid();
    }

    @Override
    public UserEntity create(UserEntity user, GroupEntity group) {
        userDao.save(user);
        group.setUser(user);
        groupDao.save(group);
        return user;
    }

    @Override
    public boolean addGroup(UserEntity user, GroupEntity group) {
        userDao.save(user);
        group.setUser(user);
        groupDao.save(group);
        return group.getId()>0;
    }

    @Override
    public boolean addGroup(int userId, GroupEntity group) {
        UserEntity user = userDao.retrieve(userId);
        if(null == user) return false;
        group.setUser(user);
        groupDao.save(group);
        return group.getId() > 0;
    }
}
