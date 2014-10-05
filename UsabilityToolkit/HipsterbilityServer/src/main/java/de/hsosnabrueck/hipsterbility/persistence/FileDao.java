package de.hsosnabrueck.hipsterbility.persistence;

import de.hsosnabrueck.hipsterbility.entities.files.AudioFileEntity;
import de.hsosnabrueck.hipsterbility.entities.files.LogFileEntity;
import de.hsosnabrueck.hipsterbility.entities.files.ScreenshotFileEntity;
import de.hsosnabrueck.hipsterbility.entities.files.VideoFileEntity;
import sun.rmi.log.ReliableLog;

import java.util.Collection;

/**
 * Created by Albert on 01.10.2014.
 */
public interface FileDao {
    public AudioFileEntity retrieveAudioFile(int id);
    public VideoFileEntity retrieveVideoFile(int id);
    public LogFileEntity retrieveLogFile(int id);
    public ScreenshotFileEntity retrieveScreenshotFile(int id);

    public void deleteAudioFile(int id);
    public void deleteVideoFile(int id);
    public void deleteLogFile(int id);
    public void deleteScrenshotFile(int id);

    public AudioFileEntity saveAudioFile(AudioFileEntity object);
    public VideoFileEntity saveVideoFile(VideoFileEntity object);
    public LogFileEntity saveLogFile(LogFileEntity object);
    public ScreenshotFileEntity saveScreenshotFile(ScreenshotFileEntity object);

    public Collection<AudioFileEntity> listAllAudioFiles();
    public Collection<VideoFileEntity> listAllVideoFiles();
    public Collection<LogFileEntity> listAllLogFiles();
    public Collection<ScreenshotFileEntity> listAllScreenshotFiles();
}
