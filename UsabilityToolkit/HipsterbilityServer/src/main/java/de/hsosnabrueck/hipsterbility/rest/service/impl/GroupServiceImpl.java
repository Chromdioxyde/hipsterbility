package de.hsosnabrueck.hipsterbility.rest.service.impl;

import de.hsosnabrueck.hipsterbility.entities.GroupEntity;
import de.hsosnabrueck.hipsterbility.exceptions.DataAccessException;
import de.hsosnabrueck.hipsterbility.persistence.GroupDao;
import de.hsosnabrueck.hipsterbility.rest.service.GroupService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

/**
 * Created by Albert on 09.11.2014.
 */
@Singleton
public class GroupServiceImpl implements GroupService {

    @Inject
    GroupDao groupDao;

    @Override
    public Collection<GroupEntity> list() throws DataAccessException {
        return groupDao.listAll();
    }

    @Override
    public Collection<GroupEntity> list(int startIndex, int count) throws DataAccessException {
        return groupDao.list(startIndex, count);
    }

    @Override
    public GroupEntity read(String id) throws DataAccessException {
        return groupDao.read(id);
    }

    @Override
    public void delete(String id) throws DataAccessException {
        groupDao.delete(id);
    }

    @Override
    public GroupEntity create(GroupEntity object) throws DataAccessException {
        return groupDao.create(object);
    }

    @Override
    public GroupEntity update(String id, GroupEntity object) throws DataAccessException {
        return groupDao.update(id, object);
    }


}
