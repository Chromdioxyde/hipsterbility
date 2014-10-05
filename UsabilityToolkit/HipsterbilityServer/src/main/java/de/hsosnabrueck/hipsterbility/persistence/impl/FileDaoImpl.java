package de.hsosnabrueck.hipsterbility.persistence.impl;

import de.hsosnabrueck.hipsterbility.entities.files.AudioFileEntity;
import de.hsosnabrueck.hipsterbility.entities.files.LogFileEntity;
import de.hsosnabrueck.hipsterbility.entities.files.ScreenshotFileEntity;
import de.hsosnabrueck.hipsterbility.entities.files.VideoFileEntity;
import de.hsosnabrueck.hipsterbility.persistence.FileDao;
import de.hsosnabrueck.hipsterbility.rest.service.files.ScreenshotFileService;

import javax.inject.Singleton;
import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by Albert on 01.10.2014.
 */
@Singleton
public class FileDaoImpl implements FileDao {

    private class AudioFileDao extends BasicDaoImpl<AudioFileEntity>{
        protected AudioFileDao() {super(AudioFileEntity.class, AudioFileEntity.TABLE_NAME);}
    }
    private class VideoFileDao extends BasicDaoImpl<VideoFileEntity>{
        protected VideoFileDao() {super(VideoFileEntity.class, VideoFileEntity.TABLE_NAME);}
    }
    private class LogFileDao extends BasicDaoImpl<LogFileEntity>{
        protected LogFileDao() {super(LogFileEntity.class, LogFileEntity.TABLE_NAME);}
    }
    private class ScreenshotFileDao extends BasicDaoImpl<ScreenshotFileEntity>{
        protected ScreenshotFileDao() {super(ScreenshotFileEntity.class, ScreenshotFileEntity.TABLE_NAME);}
    }

    protected EntityManagerFactory emf = Persistence.createEntityManagerFactory("HipsterbilityService");

    private AudioFileDao audioFileDao = new AudioFileDao();
    private VideoFileDao videoFileDao = new VideoFileDao();
    private LogFileDao logFileDao = new LogFileDao();
    private ScreenshotFileDao screenshotFileDao = new ScreenshotFileDao();

    @Override
    public AudioFileEntity retrieveAudioFile(int id) {
        return audioFileDao.retrieve(id);
    }

    @Override
    public VideoFileEntity retrieveVideoFile(int id) {
        return videoFileDao.retrieve(id);
    }

    @Override
    public LogFileEntity retrieveLogFile(int id) {
        return logFileDao.retrieve(id);
    }

    @Override
    public ScreenshotFileEntity retrieveScreenshotFile(int id) {
        return screenshotFileDao.retrieve(id);
    }

    @Override
    public void deleteAudioFile(int id) {
        audioFileDao.delete(id);
    }

    @Override
    public void deleteVideoFile(int id) {
        videoFileDao.delete(id);
    }

    @Override
    public void deleteLogFile(int id) {
        logFileDao.delete(id);
    }

    @Override
    public void deleteScrenshotFile(int id) {
        screenshotFileDao.delete(id);
    }

    @Override
    public AudioFileEntity saveAudioFile(AudioFileEntity object) {
        return audioFileDao.save(object);
    }

    @Override
    public VideoFileEntity saveVideoFile(VideoFileEntity object) {
        return videoFileDao.save(object);
    }

    @Override
    public LogFileEntity saveLogFile(LogFileEntity object) {
        return logFileDao.save(object);
    }

    @Override
    public ScreenshotFileEntity saveScreenshotFile(ScreenshotFileEntity object) {
        return screenshotFileDao.save(object);
    }

    @Override
    public Collection<AudioFileEntity> listAllAudioFiles() {
        return audioFileDao.listAll();
    }

    @Override
    public Collection<VideoFileEntity> listAllVideoFiles() {
        return videoFileDao.listAll();
    }

    @Override
    public Collection<LogFileEntity> listAllLogFiles() {
        return logFileDao.listAll();
    }

    @Override
    public Collection<ScreenshotFileEntity> listAllScreenshotFiles() {
        return screenshotFileDao.listAll();
    }

}
