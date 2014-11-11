package de.hsosnabrueck.iui.informatik.vma.hipsterbility.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.model.FileEntity;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.model.TaskEntity;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.model.TestEntity;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.model.TestSessionEntity;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private final static String TAG = DatabaseHelper.class.getSimpleName();

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "hipsterbility.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    private Dao<TestEntity, Integer> testDao;
    private Dao<TaskEntity, Integer> taskDao;
    private Dao<TestSessionEntity, Integer> sessionDao;
    private Dao<FileEntity, Integer> audioFileDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

//    public DatabaseHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
//    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, TestEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, TaskEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, TestSessionEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, FileEntity.class);
        } catch (SQLException e) {
            Log.e(TAG, "Could not create Database Tables: " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        // TODO: implement when needed
    }

    public Dao<TestEntity, Integer> getTestDao() throws SQLException {
        if(null == testDao){
            testDao = getDao(TestEntity.class);
        }
        return testDao;
    }

    public Dao<TaskEntity, Integer> getTaskDao() throws SQLException {
        if(null == taskDao){
            taskDao = getDao(TaskEntity.class);
        }
        return taskDao;
    }

    public Dao<TestSessionEntity, Integer> getSessionDao() throws SQLException {
        if(null == sessionDao){
            sessionDao = getDao(TestSessionEntity.class);
        }
        return sessionDao;
    }
//
//    public Dao<AudioFileEntity, Integer> getAudioFileDao() throws SQLException {
//        if(null == audioFileDao){
//            audioFileDao = getDao(AudioFileEntity.class);
//        }
//        return audioFileDao;
//    }
//
//    public Dao<VideoFileEntity, Integer> getVideoFileDao() throws SQLException {
//        if(null == videoFileDao){
//            videoFileDao = getDao(VideoFileEntity.class);
//        }
//        return videoFileDao;
//    }
//
//    public Dao<ScreenshotFileEntity, Integer> getScreenshotFileDao() throws SQLException {
//        if(null == screenshotFileDao){
//            screenshotFileDao = getDao(ScreenshotFileEntity.class);
//        }
//        return screenshotFileDao;
//    }
//
//    public Dao<LogFileEntity, Integer> getLogFileDao() throws SQLException {
//        if(null == logFileDao){
//            logFileDao = getDao(LogFileEntity.class);
//        }
//        return logFileDao;
//    }


}
