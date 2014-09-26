package de.hsosnabrueck.hipsterbility.persistence.impl;

import de.hsosnabrueck.hipsterbility.entities.ScreenshotEntity;

import javax.inject.Singleton;

/**
 * Created by Albert on 25.09.2014.
 */
@Singleton
public class ScreenshotDaoImpl extends BasicDaoImpl<ScreenshotEntity> {
    protected ScreenshotDaoImpl() {
        super(ScreenshotEntity.class, ScreenshotEntity.TABLE_NAME);
    }
}
