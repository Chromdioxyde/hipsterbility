package de.hsosnabrueck.hipsterbility.persistence.impl;

import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;
import de.hsosnabrueck.hipsterbility.entities.TestAppEntity;
import de.hsosnabrueck.hipsterbility.entities.TestSessionEntity;
import de.hsosnabrueck.hipsterbility.entities.UserEntity;
import de.hsosnabrueck.hipsterbility.exceptions.DataAccessException;
import de.hsosnabrueck.hipsterbility.persistence.*;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertNotNull;
/**
 * Created by Albert on 27.09.2014.
 */
public class SessionDaoTest {

    private static TestSessionDao sessionDao;
    private static UserDao userDao;
    private static TestAppDao testAppDao;
    private static DeviceDao deviceDao;

    @BeforeClass
    public static void setupDatabaseConnection() {
        sessionDao = new TestSessionDaoImpl();
        userDao = new UserDaoImpl();
        testAppDao = new TestAppDaoImpl();
        deviceDao = new DeviceDaoImpl();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HipsterbilityServiceTest");
        sessionDao.setEntityManagerFactory(emf);
        userDao.setEntityManagerFactory(emf);
        testAppDao.setEntityManagerFactory(emf);
        deviceDao.setEntityManagerFactory(emf);

    }

    @Test
    public void testSessionDao(){

        UserEntity u1 = TestObjectFactory.getUserEntity();
        UserEntity u2 = null;
        try {
            u2 = userDao.create(u1);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        System.out.println(u2.getId());
        assertNotNull(u2);

        TestSessionEntity s1 = TestObjectFactory.getTestSessionEntity();
        DeviceEntity deviceEntity = TestObjectFactory.getDeviceEntity();
        deviceEntity.setUser(u2);
        TestAppEntity testAppEntity = TestObjectFactory.getTestAppEntity();


        try {
            assertNotNull(deviceDao.create(deviceEntity));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        System.out.println(deviceEntity.getId());
        try {
            assertNotNull(deviceDao.read(1));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        try {
            s1 = sessionDao.create(s1);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        TestSessionEntity s2 = null;
        try {
            s2 = sessionDao.read(s1.getId());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        assertNotNull(s2);

//        u1.getSessions().add(s1);
//        AudioFileEntity a1 = TestObjectFactory.getAudioFileEntity();
//        a1.setSession(s1);
//        s1.getAudios().add(a1);
//        sessionDao.update(s1.getId(), s1);

    }

}