package de.hsosnabrueck.hipsterbility.rest.service.impl;

import de.hsosnabrueck.hipsterbility.entities.TestSessionEntity;
import de.hsosnabrueck.hipsterbility.entities.files.*;
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
    public Collection<TestSessionEntity> list() {
        return sessionDao.listAll();
    }

    @Override
    public Collection<TestSessionEntity> list(int startIndex, int count) {
        return sessionDao.list(startIndex, count);
    }

    @Override
    public TestSessionEntity read(int id) {
        return sessionDao.retrieve(id);
    }

    @Override
    public boolean delete(int id) {
        return sessionDao.delete(id);
    }

    @Override
    public TestSessionEntity create(TestSessionEntity session) {
        ArrayList<FileEntity> files = new ArrayList<>();
        if(null != session.getAudios()) files.addAll(session.getAudios());
        if(null != session.getVideos()) files.addAll(session.getVideos());
        if(null != session.getLogs()) files.addAll(session.getLogs());
        if(null != session.getScreenshots()) files.addAll(session.getScreenshots());
        for(FileEntity f : files){
            if(null == f.getSession()){
                f.setSession(session);
            }
        }
        return sessionDao.save(session);
    }

    @Override
    public boolean update(int id, TestSessionEntity object) {
        return sessionDao.update(id, object);
    }

}
