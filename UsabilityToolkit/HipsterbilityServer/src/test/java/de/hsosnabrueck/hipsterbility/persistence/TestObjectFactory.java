package de.hsosnabrueck.hipsterbility.persistence;

import de.hsosnabrueck.hipsterbility.PasswordTest;
import de.hsosnabrueck.hipsterbility.entities.*;
import de.hsosnabrueck.hipsterbility.entities.files.AudioFileEntity;
import de.hsosnabrueck.hipsterbility.entities.files.LogFileEntity;
import de.hsosnabrueck.hipsterbility.entities.files.ScreenshotFileEntity;
import de.hsosnabrueck.hipsterbility.entities.files.VideoFileEntity;
import de.hsosnabrueck.hipsterbility.model.enums.DeviceClass;
import de.hsosnabrueck.hipsterbility.model.enums.DevicePlatform;
import de.hsosnabrueck.hipsterbility.util.Utils;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Albert on 27.09.2014.
 */
public class TestObjectFactory {

    public static UserEntity getUserEntity(){
        UserEntity u = new UserEntity();
        u.setActive(true);
        u.setEmail(Utils.randomString(4) + "@tester.de");
        u.setFirstname("Bob");
        u.setLastname("Tester");
        u.setPassword(new PasswordTest().generateHash("test"));
        u.setLocale(Locale.GERMAN);
        u.setUsername(Utils.randomString(6));
        return u;
    }

    public static DeviceEntity getDeviceEntity(){
        DeviceEntity d = new DeviceEntity();
        d.setUuid(UUID.randomUUID().toString());
        d.setDeviceClass(DeviceClass.PHONE);
        d.setName("Nexus 5");
        d.setCustomName("My Nexus");
        d.setOsVersion("4.4.4");
        d.setPlatform(DevicePlatform.ANDROID);
        return d;
    }

    public static AudioFileEntity getAudioFileEntity(){
        AudioFileEntity a = new AudioFileEntity();
        a.setFilePath("C:\\somefile");
        a.setType(AudioFileEntity.Type.MICROPHONE);
        a.setTimestamp(new Date());
        return a;
    }

    public static LogFileEntity getLogFileEntity(){
        LogFileEntity l = new LogFileEntity();
        l.setFilePath("C:\\somefile");
        l.setType(LogFileEntity.Type.SESSION);
        l.setTimestamp(new Date());
        return l;
    }

    public static ScreenshotFileEntity getScreenshotFileEntity(){
        ScreenshotFileEntity s = new ScreenshotFileEntity();
        s.setFilePath("C:\\somefile");
        s.setTimestamp(new Date());
        return s;
    }

    public static VideoFileEntity getVideoFileEntity(){
        VideoFileEntity v = new VideoFileEntity();
        v.setFilePath("C:\\somefile");
        v.setType(VideoFileEntity.Type.CAMERA_FRONT);
        v.setTimestamp(new Date());
        return v;
    }

    public static TaskEntity getTaskEntity(){
        TaskEntity t = new TaskEntity();
        t.setName("Test Task");
        t.setDescription("Some test task");
        t.setLocale(Locale.GERMAN);
        return t;
    }

    public static TestSessionEntity getTestSessionEntity(){
        TestSessionEntity t = new TestSessionEntity();
        t.setName("Test Session" + Utils.randomString(4));
        t.setDescription("Some Description");
        t.setActive(true);
        return t;
    }

    public static TestAppEntity getTestAppEntity() {
        TestAppEntity t = new TestAppEntity();
        t.setName("testapp");
        t.setPlatform(DevicePlatform.ANDROID);
        t.setVersion("1.0");
        return t;
    }
}
