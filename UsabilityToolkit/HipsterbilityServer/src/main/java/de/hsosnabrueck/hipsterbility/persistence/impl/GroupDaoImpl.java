package de.hsosnabrueck.hipsterbility.persistence.impl;

import de.hsosnabrueck.hipsterbility.entities.GroupEntity;
import de.hsosnabrueck.hipsterbility.persistence.GroupDao;

import javax.inject.Singleton;

/**
 * Created by Albert on 16.09.2014.
 */
@Singleton
public class GroupDaoImpl extends GenericDaoImpl<GroupEntity, String> implements GroupDao {

    protected GroupDaoImpl() {
        super(GroupEntity.class, GroupEntity.TABLE_NAME);
    }
}
