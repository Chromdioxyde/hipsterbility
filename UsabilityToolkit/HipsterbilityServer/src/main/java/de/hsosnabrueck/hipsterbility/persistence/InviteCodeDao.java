package de.hsosnabrueck.hipsterbility.persistence;

import de.hsosnabrueck.hipsterbility.entities.InviteCodeEntity;

/**
 * Created by Albert on 28.09.2014.
 */
public interface InviteCodeDao extends Dao<InviteCodeEntity> {

    public InviteCodeEntity findCode(String code);
}
