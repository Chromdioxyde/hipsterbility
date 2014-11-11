package de.hsosnabrueck.hipsterbility.rest.service;

import de.hsosnabrueck.hipsterbility.entities.InviteCodeEntity;
import de.hsosnabrueck.hipsterbility.exceptions.DataAccessException;

/**
 * Created by Albert on 28.09.2014.
 */
public interface InviteCodeService extends Service<InviteCodeEntity, Integer> {
    public InviteCodeEntity getInvite(String code) throws DataAccessException;
}
