package de.hsosnabrueck.hipsterbility.persistence.impl;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;
import de.hsosnabrueck.hipsterbility.entities.TestAppEntity;
import de.hsosnabrueck.hipsterbility.entities.files.AudioFileEntity;
import de.hsosnabrueck.hipsterbility.entities.TestSessionEntity;
import de.hsosnabrueck.hipsterbility.entities.UserEntity;
import de.hsosnabrueck.hipsterbility.persistence.*;
import org.eclipse.persistence.config.TargetServer;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.naming.Context;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.spi.PersistenceUnitTransactionType;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
/**
 * Created by Albert on 27.09.2014.
 */
public class SessionDaoTest {


    @BeforeClass
    public static void setupDatabaseConnection() {
    }

    @Test
    public void testSessionDao(){
        TestSessionDao sessionDao = new TestSessionDaoImpl();
        UserDao userDao = new UserDaoImpl();
        TestAppDao testAppDao = new TestAppDaoImpl();
        DeviceDao deviceDao = new DeviceDaoImpl();
        UserEntity u1 = TestObjectFactory.getUserEntity();
        UserEntity u2 = userDao.save(u1);
        System.out.println(u2.getId());
        assertNotNull(u2);

        TestSessionEntity s1 = TestObjectFactory.getTestSessionEntity();
        DeviceEntity deviceEntity = TestObjectFactory.getDeviceEntity();
        deviceEntity.setUser(u2);
        TestAppEntity testAppEntity = TestObjectFactory.getTestAppEntity();


        assertNotNull(deviceDao.save(deviceEntity));
        System.out.println(deviceEntity.getId());
        assertNotNull(deviceDao.retrieve(1));

        s1 = sessionDao.save(s1);
        TestSessionEntity s2 = sessionDao.retrieve(s1.getId());
        assertNotNull(s2);

        u1.getSessions().add(s1);
        AudioFileEntity a1 = TestObjectFactory.getAudioFileEntity();
        a1.setSession(s1);
        s1.getAudios().add(a1);
//        sessionDao.update(s1.getId(), s1);

    }

}