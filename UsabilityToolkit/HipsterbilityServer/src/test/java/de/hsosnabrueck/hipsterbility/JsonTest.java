package de.hsosnabrueck.hipsterbility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hsosnabrueck.hipsterbility.entities.DeviceEntity;
import de.hsosnabrueck.hipsterbility.entities.TestSessionEntity;
import de.hsosnabrueck.hipsterbility.entities.UserEntity;
import de.hsosnabrueck.hipsterbility.persistence.TestObjectFactory;
import de.hsosnabrueck.hipsterbility.persistence.TestSessionDao;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Albert on 29.09.2014.
 */
public class JsonTest {

    @Test
    public void testJson() throws JsonProcessingException {
//        System.out.println(new ObjectMapper().writeValueAsString(TestObjectFactory.getDeviceEntity()));
//        System.out.println(new ObjectMapper().writeValueAsString(TestObjectFactory.getUserEntity()));

        UserEntity u = TestObjectFactory.getUserEntity();
        TestSessionEntity s = TestObjectFactory.getTestSessionEntity();
        DeviceEntity d = TestObjectFactory.getDeviceEntity();
        List<TestSessionEntity> l = new ArrayList<TestSessionEntity>();
        l.add(s);
        u.setSessions(l);
        s.setTester(u);
        ObjectMapper m = new ObjectMapper();
        System.out.println(m.writeValueAsString(u));
        System.out.println(m.writeValueAsString(s));
        System.out.println(m.writeValueAsString(d));
    }
}
