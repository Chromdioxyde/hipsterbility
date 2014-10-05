package de.hsosnabrueck.hipsterbility.rest.service;

import de.hsosnabrueck.hipsterbility.entities.InviteCodeEntity;

/**
 * Created by Albert on 28.09.2014.
 */
public interface InviteCodeService extends Service<InviteCodeEntity> {
    public InviteCodeEntity getInvite(String code);
}
