package de.hsosnabrueck.hipsterbility.persistence.impl;

import de.hsosnabrueck.hipsterbility.entities.InviteCodeEntity;
import de.hsosnabrueck.hipsterbility.entities.UserEntity;
import de.hsosnabrueck.hipsterbility.persistence.InviteCodeDao;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Collection;

/**
 * Created by Albert on 28.09.2014.
 */
@Singleton
public class InviteCodeDaoImpl extends BasicDaoImpl<InviteCodeEntity> implements InviteCodeDao {

    public InviteCodeDaoImpl() {
        super(InviteCodeEntity.class, InviteCodeEntity.TABLE_NAME);
    }


    @Override
    public InviteCodeEntity findCode(String code) {
//        TODO: clean up and surround with try catch if needed
        EntityManager em = emf.createEntityManager();
        InviteCodeEntity invite = null;
        try {
            invite = em.createNamedQuery("InviteCode.findByCode", InviteCodeEntity.class).setParameter("code", code).getSingleResult();
        } finally {
            em.close();
        }
        return invite;
    }
}
