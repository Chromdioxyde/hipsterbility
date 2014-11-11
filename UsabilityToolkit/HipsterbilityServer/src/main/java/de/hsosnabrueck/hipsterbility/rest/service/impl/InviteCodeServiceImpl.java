package de.hsosnabrueck.hipsterbility.rest.service.impl;

import de.hsosnabrueck.hipsterbility.entities.InviteCodeEntity;
import de.hsosnabrueck.hipsterbility.exceptions.DataAccessException;
import de.hsosnabrueck.hipsterbility.persistence.InviteCodeDao;
import de.hsosnabrueck.hipsterbility.rest.service.InviteCodeService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

/**
 * Created by Albert on 28.09.2014.
 */
@Singleton
public class InviteCodeServiceImpl implements InviteCodeService {

    @Inject
    InviteCodeDao inviteCodeDao;

    @Override
    public Collection<InviteCodeEntity> list() throws DataAccessException {
        return inviteCodeDao.listAll();
    }

    @Override
    public Collection<InviteCodeEntity> list(int startIndex, int count) throws DataAccessException {
        return inviteCodeDao.list(startIndex, count);
    }

    @Override
    public InviteCodeEntity read(Integer id) throws DataAccessException {
        return inviteCodeDao.read(id);
    }

    @Override
    public void delete(Integer id) throws DataAccessException {
         inviteCodeDao.delete(id);
    }

    @Override
    public InviteCodeEntity create(InviteCodeEntity object) throws DataAccessException {
        return inviteCodeDao.create(object);
    }

    @Override
    public InviteCodeEntity update(Integer id, InviteCodeEntity invite) throws DataAccessException {
        return inviteCodeDao.update(id, invite);
    }

    @Override
    public InviteCodeEntity getInvite(String code) throws DataAccessException {
        return inviteCodeDao.findCode(code);
    }
}
