package de.hsosnabrueck.hipsterbility.persistence;

import de.hsosnabrueck.hipsterbility.entities.GroupEntity;

import javax.inject.Singleton;

/**
 * Created by Albert on 16.09.2014.
 */
@Singleton
public class GroupDaoImpl extends BasicDaoImpl<GroupEntity> implements GroupDao {

    protected GroupDaoImpl() {
        super(GroupEntity.class, GroupEntity.TABLE_NAME);
    }
}
