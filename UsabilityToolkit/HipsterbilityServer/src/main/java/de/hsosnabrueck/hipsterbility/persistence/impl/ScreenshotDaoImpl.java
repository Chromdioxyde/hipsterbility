package de.hsosnabrueck.hipsterbility.persistence.impl;

import de.hsosnabrueck.hipsterbility.entities.files.ScreenshotFileEntity;

import javax.inject.Singleton;

/**
 * Created by Albert on 25.09.2014.
 */
@Singleton
public class ScreenshotDaoImpl extends BasicDaoImpl<ScreenshotFileEntity> {
    protected ScreenshotDaoImpl() {
        super(ScreenshotFileEntity.class, ScreenshotFileEntity.TABLE_NAME);
    }
}
