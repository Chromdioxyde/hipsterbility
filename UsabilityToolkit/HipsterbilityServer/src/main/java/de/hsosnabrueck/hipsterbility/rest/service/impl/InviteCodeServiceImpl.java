package de.hsosnabrueck.hipsterbility.rest.service.impl;

import de.hsosnabrueck.hipsterbility.entities.InviteCodeEntity;
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
    public Collection<InviteCodeEntity> list() {
        return inviteCodeDao.listAll();
    }

    @Override
    public Collection<InviteCodeEntity> list(int startIndex, int count) {
        return inviteCodeDao.list(startIndex, count);
    }

    @Override
    public InviteCodeEntity read(int id) {
        return inviteCodeDao.retrieve(id);
    }

    @Override
    public boolean delete(int id) {
        return inviteCodeDao.delete(id);
    }

    @Override
    public InviteCodeEntity create(InviteCodeEntity object) {
        return inviteCodeDao.save(object);
    }

    @Override
    public boolean update(int id, InviteCodeEntity object) {
        return inviteCodeDao.update(id, object);
    }

    @Override
    public InviteCodeEntity getInvite(String code) {
        return inviteCodeDao.findCode(code);
    }
}
