package de.hsosnabrueck.hipsterbility.rest.service.impl;

import de.hsosnabrueck.hipsterbility.entities.FileEntity;
import de.hsosnabrueck.hipsterbility.entities.TestSessionEntity;
import de.hsosnabrueck.hipsterbility.exceptions.DataAccessException;
import de.hsosnabrueck.hipsterbility.persistence.FileDao;
import de.hsosnabrueck.hipsterbility.persistence.TestSessionDao;
import de.hsosnabrueck.hipsterbility.rest.service.TestSessionService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Albert on 01.10.2014.
 */
@Singleton
public class TestSessionServiceImpl implements TestSessionService {

    @Inject
    private TestSessionDao sessionDao;

    @Inject
    private FileDao fileDao;

    @Override
    public Collection<TestSessionEntity> list() throws DataAccessException {
        return sessionDao.listAll();
    }

    @Override
    public Collection<TestSessionEntity> list(int startIndex, int count) throws DataAccessException {
            return sessionDao.list(startIndex, count);
    }

    @Override
    public TestSessionEntity read(Integer id) throws DataAccessException {
            return sessionDao.read(id);
    }

    @Override
    public void delete(Integer id) throws DataAccessException {
            sessionDao.delete(id);
    }

    @Override
    public TestSessionEntity create(TestSessionEntity session) throws DataAccessException {
//        ArrayList<FileEntity> files = new ArrayList<>();
//        if(null != session.getAudios()) files.addAll(session.getAudios());
//        if(null != session.getVideos()) files.addAll(session.getVideos());
//        if(null != session.getLogs()) files.addAll(session.getLogs());
//        if(null != session.getScreenshots()) files.addAll(session.getScreenshots());
//        for(FileEntity f : files){
//            if(null == f.getSession()){
//                f.setSession(session);
//            }
//        }
        return sessionDao.create(session);
    }

    @Override
    public TestSessionEntity update(Integer id, TestSessionEntity session) throws DataAccessException {
        return sessionDao.update(id, session);
    }

}
