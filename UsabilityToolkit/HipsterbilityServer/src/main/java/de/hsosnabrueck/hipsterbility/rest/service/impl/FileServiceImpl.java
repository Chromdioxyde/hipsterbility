package de.hsosnabrueck.hipsterbility.rest.service.impl;

import de.hsosnabrueck.hipsterbility.entities.FileEntity;
import de.hsosnabrueck.hipsterbility.exceptions.DataAccessException;
import de.hsosnabrueck.hipsterbility.persistence.FileDao;
import de.hsosnabrueck.hipsterbility.rest.service.FileService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

/**
 * Created by Albert on 01.10.2014.
 */
@Singleton
public class FileServiceImpl implements FileService {

    @Inject
    FileDao fileDao;

    @Override
    public Collection<FileEntity> list() throws DataAccessException {
        return fileDao.listAll();
    }

    @Override
    public Collection<FileEntity> list(int startIndex, int count) throws DataAccessException {
        return fileDao.list(startIndex, count);
    }

    @Override
    public FileEntity read(Integer id) throws DataAccessException {
        return fileDao.read(id);
    }

    @Override
    public void delete(Integer id) throws DataAccessException {
        fileDao.delete(id);
    }

    @Override
    public FileEntity create(FileEntity object) throws DataAccessException {
        return fileDao.create(object);
    }

    @Override
    public FileEntity update(Integer id, FileEntity object) throws DataAccessException {
        return fileDao.update(id, object);
    }
}
