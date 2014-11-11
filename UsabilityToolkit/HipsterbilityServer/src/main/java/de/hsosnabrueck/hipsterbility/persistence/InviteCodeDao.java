package de.hsosnabrueck.hipsterbility.persistence;

import de.hsosnabrueck.hipsterbility.entities.InviteCodeEntity;
import de.hsosnabrueck.hipsterbility.exceptions.DataAccessException;

/**
 * Created by Albert on 28.09.2014.
 */
public interface InviteCodeDao extends GenericDao<InviteCodeEntity, Integer> {

    public InviteCodeEntity findCode(String code) throws DataAccessException;
}
